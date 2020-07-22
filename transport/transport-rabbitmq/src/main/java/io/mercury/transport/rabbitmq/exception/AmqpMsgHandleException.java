package io.mercury.transport.rabbitmq.exception;

public class AmqpMsgHandleException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8006184062312891950L;

	/**
	 * 
	 * @param message
	 * @param throwable
	 */
	public AmqpMsgHandleException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
