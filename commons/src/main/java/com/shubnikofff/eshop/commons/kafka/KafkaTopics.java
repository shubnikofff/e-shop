package com.shubnikofff.eshop.commons.kafka;

public final class KafkaTopics {

	private KafkaTopics() {}

	// commands
	public static final String ACCOUNT_COMMAND_TOPIC = "account.command.v1";
	public static final String CUSTOMER_COMMAND_TOPIC = "customer.command.v1";

	// events
	public static final String CUSTOMER_EVENT_TOPIC = "customer.event.v1";
}
