package com.shubnikofff.eshop.commons.kafka.message;

public class KafkaMessage<P> {

	private final String sender;

	private final P payload;

	public KafkaMessage(String sender, P payload) {
		this.sender = sender;
		this.payload = payload;
	}

	public String getSender() {
		return sender;
	}

	public P getPayload() {
		return payload;
	}
}
