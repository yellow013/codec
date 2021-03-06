package io.mercury.transport.rabbitmq;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;

import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.GetResponse;

import io.mercury.common.character.Charsets;
import io.mercury.common.collections.MutableLists;
import io.mercury.common.concurrent.queue.MultiConsumerQueue;
import io.mercury.common.concurrent.queue.QueueStyle;
import io.mercury.common.log.CommonLoggerFactory;
import io.mercury.common.serialization.spec.BytesDeserializer;
import io.mercury.common.serialization.spec.BytesSerializer;
import io.mercury.serialization.json.JsonWrapper;
import io.mercury.transport.rabbitmq.configurator.RabbitConnection;
import io.mercury.transport.rabbitmq.declare.AmqpExchange;
import io.mercury.transport.rabbitmq.declare.QueueDefinition;
import io.mercury.transport.rabbitmq.exception.DeclareException;

public class RabbitMqBuffer<E> implements MultiConsumerQueue<E>, Closeable {

	private static final Logger log = CommonLoggerFactory.getLogger(RabbitMqBuffer.class);

	private RabbitConnection connection;
	private RabbitMqChannel channel;
	private String queueName;
	private List<String> exchangeNames;
	private List<String> routingKeys;

	private BytesSerializer<E> serializer;
	private BytesDeserializer<E> deserializer;

	private final String name;

	/**
	 * 
	 * @param <E>
	 * @param connection
	 * @param queueName
	 * @param serializer
	 * @param deserializer
	 * @return
	 * @throws DeclareException
	 */
	public static final <E> RabbitMqBuffer<E> newQueue(RabbitConnection connection, String queueName,
			BytesSerializer<E> serializer, BytesDeserializer<E> deserializer) throws DeclareException {
		return new RabbitMqBuffer<>(connection, queueName, MutableLists.emptyFastList(), MutableLists.emptyFastList(),
				serializer, deserializer);
	}

	/**
	 * 
	 * @param <E>
	 * @param connection
	 * @param queueName
	 * @param exchangeNames
	 * @param routingKeys
	 * @param serializer
	 * @param deserializer
	 * @return
	 * @throws DeclareException
	 */
	public static final <E> RabbitMqBuffer<E> newQueue(RabbitConnection connection, String queueName,
			List<String> exchangeNames, List<String> routingKeys, BytesSerializer<E> serializer,
			BytesDeserializer<E> deserializer) throws DeclareException {
		return new RabbitMqBuffer<>(connection, queueName, exchangeNames, routingKeys, serializer, deserializer);
	}

	/**
	 * 
	 * @param connection
	 * @param queueName
	 * @param exchangeNames
	 * @param routingKeys
	 * @param serializer
	 * @param deserializer
	 * @throws DeclareException
	 */
	private RabbitMqBuffer(RabbitConnection connection, String queueName, List<String> exchangeNames,
			List<String> routingKeys, BytesSerializer<E> serializer, BytesDeserializer<E> deserializer)
			throws DeclareException {
		this.connection = connection;
		this.queueName = queueName;
		this.exchangeNames = exchangeNames;
		this.routingKeys = routingKeys;
		this.serializer = serializer;
		this.deserializer = deserializer;
		this.channel = RabbitMqChannel.newWith(connection);
		this.name = "rabbitmq-buffer::" + connection.getConnectionInfo() + "/" + queueName + "";
		declareQueue();
	}

	/**
	 * 
	 * @throws DeclareException
	 */
	private void declareQueue() throws DeclareException {
		QueueDefinition queueRelationship = QueueDefinition.named(queueName).binding(
				// 如果routingKeys为空集合, 则创建fanout交换器, 否则创建直接交换器
				exchangeNames.stream().map(exchangeName -> routingKeys.isEmpty() ? AmqpExchange.fanout(exchangeName)
						: AmqpExchange.direct(exchangeName)).collect(Collectors.toList()),
				routingKeys);
		queueRelationship.declare(RabbitMqDeclarator.newWith(channel.internalChannel()));
	}

	/**
	 * 
	 * @return
	 */
	public RabbitConnection getConnection() {
		return connection;
	}

	@Override
	public boolean enqueue(E e) {
		byte[] msg = serializer.serialization(e);
		try {
			channel.internalChannel().basicPublish("", queueName, null, msg);
			return true;
		} catch (IOException ioe) {
			log.error("enqueue basicPublish throw -> {}", ioe.getMessage(), ioe);
			return false;
		}
	}

	@Override
	public E dequeue() {
		GetResponse response = basicGet();
		if (response == null)
			return null;
		byte[] body = response.getBody();
		if (body == null)
			return null;
		basicAck(response.getEnvelope());
		return deserializer.deserialization(body);
	}

	@Override
	public boolean pollAndApply(PollFunction<E> function) {
		GetResponse response = basicGet();
		if (response == null)
			return false;
		byte[] body = response.getBody();
		if (body == null)
			return false;
		if (!function.apply(deserializer.deserialization(body))) {
			log.error("PollFunction failure, no ack");
			return false;
		}
		return basicAck(response.getEnvelope());
	}

	/**
	 * 
	 * @return
	 */
	private GetResponse basicGet() {
		try {
			return channel.internalChannel().basicGet(queueName, false);
		} catch (IOException ioe) {
			log.error("poll basicGet throw -> {}", ioe.getMessage(), ioe);
			return null;
		}
	}

	/**
	 * 
	 * @param envelope
	 * @return
	 */
	private boolean basicAck(Envelope envelope) {
		try {
			channel.internalChannel().basicAck(envelope.getDeliveryTag(), false);
			return true;
		} catch (IOException ioe) {
			log.error("poll basicAck throw -> {}", ioe.getMessage(), ioe);
			return false;
		}
	}

	@Override
	public String getQueueName() {
		return name;
	}

	@Override
	public void close() throws IOException {
		channel.close();
	}

	public static void main(String[] args) {

		RabbitConnection connection = RabbitConnection.configuration("127.0.0.1", 5672, "user", "password").build();

		try {
			RabbitMqBuffer<String> testQueue = newQueue(connection, "rmq_test",
					e -> JsonWrapper.toJson(e).getBytes(Charsets.UTF8),
					(bytes, reuuse) -> new String(bytes, Charsets.UTF8));
			testQueue.pollAndApply(str -> {
				System.out.println(str);
				return true;
			});
		} catch (DeclareException e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public QueueStyle getQueueStyle() {
		return QueueStyle.MPMC;
	}

}
