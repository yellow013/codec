package io.mercury.transport.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import org.apache.commons.io.IOUtils;

import io.mercury.common.collections.Capacity;
import io.mercury.common.concurrent.queue.SingleConsumerQueue;
import io.mercury.common.disruptor.SpscQueue;
import io.mercury.common.thread.Threads;
import io.mercury.transport.socket.configurator.SocketConfigurator;

public final class SocketTransceiver extends BaseTransceiver<String> {

	private SocketConfigurator configurator;
	private Consumer<byte[]> callback;

	private Socket socket;
	private Writer writer;

	private AtomicBoolean isReceiving = new AtomicBoolean(false);
	private AtomicBoolean isRun = new AtomicBoolean(false);

	/**
	 * @param configurator
	 * @param callback
	 * @param serverSocket
	 */
	public SocketTransceiver(SocketConfigurator configurator, Consumer<byte[]> callback) {
		super();
		if (configurator == null || callback == null) {
			throw new IllegalArgumentException("configurator or callback is null for init ");
		}
		this.configurator = configurator;
		this.callback = callback;
		init();
	}

	private void init() {
		try {
			this.socket = new Socket(configurator.getHost(), configurator.getPort());
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	public boolean isConnected() {
		return false;
	}

	public boolean destroy() {
		this.isReceiving.set(false);
		try {
			if (writer != null) {
				writer.close();
			}
			if (socket != null) {
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public void receive() {
		if (!isRun.get()) {
			isRun.set(true);
		}
		if (!isReceiving.get()) {
			startReceiveThread();
		}
	}

	private synchronized void startReceiveThread() {
		if (isReceiving.get())
			return;
		Threads.startNewThread(() -> {
			InputStream inputStream = null;
			while (isRun.get()) {
				try {
					inputStream = socket.getInputStream();
					int available = inputStream.available();
					if (available == 0) {
						Threads.sleep(configurator.receiveInterval());
						continue;
					}
					byte[] bytes = new byte[available];
					IOUtils.read(inputStream, bytes);
					callback.accept(bytes);
				} catch (IOException e) {
					e.printStackTrace();
					destroy();
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		isReceiving.set(true);
	}

	private void processSendQueue(String msg) {
		try {
			if (isRun.get()) {
				if (writer == null) {
					this.writer = new OutputStreamWriter(socket.getOutputStream());
				}
				writer.write(msg);
				writer.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
			destroy();
		}
	}

	@Override
	protected SingleConsumerQueue<String> initSendQueue() {
		return new SpscQueue<>("socket-queue", Capacity.L07_SIZE, true, (msg) -> {
			processSendQueue(msg);
		});
	}

	@Override
	public void reconnect() {
		// TODO Auto-generated method stub
	}

	@Override
	public void close() throws IOException {
		destroy();
	}

}
