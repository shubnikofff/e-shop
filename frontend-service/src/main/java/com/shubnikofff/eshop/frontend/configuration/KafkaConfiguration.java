package com.shubnikofff.eshop.frontend.configuration;

import com.shubnikofff.eshop.commons.kafka.message.CreateCustomerCommandMessage;
import com.shubnikofff.eshop.commons.kafka.util.KafkaSenderFactory;
import com.shubnikofff.eshop.commons.kafka.topic.KafkaTopics;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.KafkaSender;

import java.util.Collections;
import java.util.Map;

@Configuration
public class KafkaConfiguration {

	private final String bootstrapServers;

	private final Map<String, Object> consumerProperties;

	public KafkaConfiguration(KafkaConfigurationProperties properties) {
		bootstrapServers = properties.getBootstrapServers();

		consumerProperties = Map.of(
				ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers(),
				ConsumerConfig.GROUP_ID_CONFIG, properties.getGroupId(),
				ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class,
				ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class
		);
	}

	@Bean
	public KafkaSender<Object, CreateCustomerCommandMessage> createCustomerCommandSender() {
		return KafkaSenderFactory.create(bootstrapServers);
	}

	@Bean
	public KafkaReceiver<Integer, String> customerEventsReceiver() {
		final var receiverOptions = ReceiverOptions.<Integer, String>create(consumerProperties)
				.subscription(Collections.singleton(KafkaTopics.CUSTOMER_EVENT_TOPIC));

		return KafkaReceiver.create(receiverOptions);
	}
}
