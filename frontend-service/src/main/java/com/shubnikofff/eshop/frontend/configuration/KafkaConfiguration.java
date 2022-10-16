package com.shubnikofff.eshop.frontend.configuration;

import com.shubnikofff.eshop.commons.event.BaseEvent;
import com.shubnikofff.eshop.commons.kafka.serialization.MessageDeserializer;
import com.shubnikofff.eshop.commons.kafka.serialization.MessageSerializer;
import com.shubnikofff.eshop.commons.kafka.topic.KafkaTopics;
import com.shubnikofff.eshop.commons.request.CreateCustomerRequest;
import com.shubnikofff.eshop.commons.request.ToggleCustomerRequest;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Configuration
public class KafkaConfiguration {

	private final Map<String, Object> producerConfig;

	private final Map<String, Object> consumerConfig;

	public KafkaConfiguration(KafkaConfigurationProperties properties) {
		producerConfig = Map.of(
				ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers(),
				ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
				ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, MessageSerializer.class
		);

		consumerConfig = Map.of(
				ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers(),
				ConsumerConfig.GROUP_ID_CONFIG, properties.getGroupId(),
				ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
				ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, MessageDeserializer.class
		);
	}

	@Bean
	public KafkaSender<Object, CreateCustomerRequest> createCustomerRequestSender() {
		return createSender();
	}

	@Bean
	public KafkaSender<Object, ToggleCustomerRequest> toggleCustomerRequestSender() {
		return createSender();
	}

	@Bean
	public KafkaReceiver<Object, ? extends BaseEvent> customerEventsReceiver() {
		return createReceiver(Collections.singleton(KafkaTopics.CUSTOMER_EVENT_TOPIC));
	}

	private <K, V> KafkaReceiver<K, V> createReceiver(Collection<String> topics) {
		final var receiverOptions = ReceiverOptions.<K, V>create(consumerConfig)
				.subscription(topics);

		return KafkaReceiver.create(receiverOptions);
	}

	private  <K, V> KafkaSender<K, V> createSender() {
		final var senderOptions = SenderOptions.<K, V>create(producerConfig)
				.maxInFlight(1024);

		return KafkaSender.create(senderOptions);
	}
}
