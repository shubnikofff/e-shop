package com.shubnikofff.eshop.commons.kafka.util;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

import java.util.Collection;
import java.util.Map;

public class KafkaFactory {

	private final Map<String, Object> configProperties;

	public KafkaFactory(String bootstrapServers, String groupId) {
		configProperties = Map.of(
				ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
				ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
				ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaMessageSerializer.class,
				ConsumerConfig.GROUP_ID_CONFIG, groupId,
				ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class
		);
	}

	public <K, V> KafkaSender<K, V> createKafkaSender() {
		final var senderOptions = SenderOptions.<K, V>create(configProperties)
				.maxInFlight(1024);

		return KafkaSender.create(senderOptions);
	}

	public <K, V> KafkaReceiver<K, V> createKafkaReceiver(Collection<String> topics, Class<V> valueType) {
		final var receiverOptions = ReceiverOptions.<K, V>create(configProperties)
				.withValueDeserializer(new KafkaMessageDeserializer<>(valueType))
				.subscription(topics);

		return KafkaReceiver.create(receiverOptions);
	}

	public <K, V>ProducerFactory<K, V> createProducerFactory() {
		return new DefaultKafkaProducerFactory<>(configProperties);
	}

	public <K, V> ConcurrentKafkaListenerContainerFactory<K, V> createListenerContainerFactory(Class<V> valueType) {
		final var consumerFactory = new DefaultKafkaConsumerFactory<>(configProperties, null, new KafkaMessageDeserializer<>(valueType));
		final var listenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<K, V>();
		listenerContainerFactory.setConsumerFactory(consumerFactory);
		return listenerContainerFactory;
	}
}
