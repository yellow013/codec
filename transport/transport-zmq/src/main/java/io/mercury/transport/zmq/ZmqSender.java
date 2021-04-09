package io.mercury.transport.zmq;

import java.io.Closeable;
import java.io.IOException;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

import org.slf4j.Logger;
import org.zeromq.SocketType;

import io.mercury.common.log.CommonLoggerFactory;
import io.mercury.common.serialization.spec.BytesSerializer;
import io.mercury.transport.api.Sender;
import io.mercury.transport.configurator.TcpKeepAliveOption;
import io.mercury.transport.zmq.cfg.ZmqAddress;
import io.mercury.transport.zmq.exception.ZmqConnectionException;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@NotThreadSafe
public class ZmqSender<T> extends ZmqTransport implements Sender<T>, Closeable {

	@Getter
	private final String name;

	@Getter
	private final ZmqSenderConfigurator cfg;

	private final BytesSerializer<T> ser;

	private static final Logger log = CommonLoggerFactory.getLogger(ZmqSender.class);

	private ZmqSender(@Nonnull ZmqSenderConfigurator cfg, @Nonnull BytesSerializer<T> ser) {
		super(cfg.getAddr(), cfg.getIoThreads());
		this.cfg = cfg;
		this.ser = ser;
		if (socket.connect(addr.getAddr())) {
			log.info("connected addr -> {}", addr);
		} else {
			log.error("unable to connect addr -> {}", addr);
			throw new ZmqConnectionException(addr);
		}
		this.name = "Zmq::Req$" + cfg.getConnectionInfo();
	}

	@Override
	protected SocketType getSocketType() {
		return SocketType.REQ;
	}

	@Override
	public void sent(T msg) {
		byte[] bytes = ser.serialization(msg);
		if (bytes != null && bytes.length > 0) {
			socket.send(bytes);
			socket.recv();
		}
	}

	/**
	 * 
	 * @author yellow013
	 *
	 */
	public static final class ZmqSenderConfigurator extends ZmqConfigurator {

		private ZmqSenderConfigurator(Builder builder) {
			super(builder.addr, builder.ioThreads, builder.tcpKeepAliveOption);
		}

		/**
		 * 创建TCP协议连接
		 * 
		 * @param port
		 * @return
		 */
		public final static Builder tcp(int port) {
			return new Builder(ZmqAddress.tcp(port));
		}

		/**
		 * 创建TCP协议连接
		 * 
		 * @param addr
		 * @param port
		 * @return
		 */
		public final static Builder tcp(String addr, int port) {
			return new Builder(ZmqAddress.tcp(addr, port));
		}

		/**
		 * 创建IPC协议连接
		 * 
		 * @param addr
		 * @return
		 */
		public final static Builder ipc(String addr) {
			return new Builder(ZmqAddress.ipc(addr));
		}

		@Accessors(chain = true)
		public static class Builder {

			private final ZmqAddress addr;

			@Setter
			private int ioThreads = 1;

			@Setter
			private TcpKeepAliveOption tcpKeepAliveOption = null;

			private Builder(ZmqAddress addr) {
				this.addr = addr;
			}

			public ZmqSenderConfigurator build() {
				return new ZmqSenderConfigurator(this);
			}
		}
	}

	public static void main(String[] args) {

		ZmqSenderConfigurator configurator = ZmqSenderConfigurator.tcp("localhost", 5551).setIoThreads(1).build();

		try (ZmqSender<String> sender = new ZmqSender<String>(configurator, msg -> msg.getBytes())) {

			sender.sent("TEST MSG");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
