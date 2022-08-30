package com.shubnikofff.eshop.commons.kafka.message;

final public class MessageHeaders {

	private MessageHeaders() {}

	/**
	 * Type of the message body value
	 */
	public static final String TYPE = "contentType";

	public static final String SENDER = "sender";
}
