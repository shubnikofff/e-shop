package com.shubnikofff.eshop.commons.kafka.message;

final public class KafkaMessageHeaders {

	private KafkaMessageHeaders() {}

	/**
	 * Type of the message body value
	 */
	public static final String CONTENT_TYPE = "contentType";

	public static final String SENDER = "sender";
}
