package com.shubnikofff.eshop.frontend.configuration;

import com.shubnikofff.eshop.commons.kafka.message.CreateCustomerCommandMessage;
import com.shubnikofff.eshop.commons.kafka.message.CustomerEventMessage;
import com.shubnikofff.eshop.commons.kafka.util.KafkaReceiverFactory;
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

	private final KafkaSenderFactory kafkaSenderFactory;

	private final KafkaReceiverFactory kafkaReceiverFactory;

//	private final Map<String, Object> consumerProperties;

	public KafkaConfiguration(KafkaConfigurationProperties properties) {
		kafkaSenderFactory = new KafkaSenderFactory(properties.getBootstrapServers());
		kafkaReceiverFactory = new KafkaReceiverFactory(properties.getBootstrapServers(), properties.getGroupId());

//		consumerProperties = Map.of(
//				ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers(),
//				ConsumerConfig.GROUP_ID_CONFIG, properties.getGroupId(),
//				ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class,
//				ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class
//		);
	}

	@Bean
	public KafkaSender<Object, CreateCustomerCommandMessage> createCustomerCommandSender() {
		return kafkaSenderFactory.create();
	}

	@Bean
	public KafkaReceiver<Object, CustomerEventMessage> customerEventsReceiver() {
//		final var receiverOptions = ReceiverOptions.<Integer, String>create(consumerProperties)
//				.subscription(Collections.singleton(KafkaTopics.CUSTOMER_EVENT_TOPIC));
//
//		return KafkaReceiver.create(receiverOptions);
		return kafkaReceiverFactory.create(Collections.singleton(KafkaTopics.CUSTOMER_EVENT_TOPIC), CustomerEventMessage.class);
	}
}
