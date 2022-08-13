package com.shubnikofff.eshop.commons.kafka.util;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.Collection;
import java.util.Map;

public class KafkaReceiverFactory {

	private final Map<String, Object> configProperties;

	public KafkaReceiverFactory(String bootstrapServers, String groupId) {
		configProperties = Map.of(
				ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
				ConsumerConfig.GROUP_ID_CONFIG, groupId,
				ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class
		);
	}

	public <K, V> KafkaReceiver<K, V> create(Collection<String> topics, Class<V> valueType) {
		final var receiverOptions = ReceiverOptions.<K, V>create(configProperties)
				.withValueDeserializer(new KafkaMessageDeserializer<>(valueType))
				.subscription(topics);

		return KafkaReceiver.create(receiverOptions);
	}
}
