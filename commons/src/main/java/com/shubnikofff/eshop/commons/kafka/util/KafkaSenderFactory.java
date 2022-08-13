package com.shubnikofff.eshop.commons.kafka.util;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

import java.util.Map;

final public class KafkaSenderFactory {

	private final String bootstrapServers;

	public KafkaSenderFactory(String bootstrapServers) {
		this.bootstrapServers = bootstrapServers;
	}

	public <K, V> KafkaSender<K, V> create() {
		final Map<String, Object> configProperties = Map.of(
				ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
				ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
				ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaMessageSerializer.class
		);

		final var senderOptions = SenderOptions.<K, V>create(configProperties)
				.maxInFlight(1024);

		return KafkaSender.create(senderOptions);
	}
}
