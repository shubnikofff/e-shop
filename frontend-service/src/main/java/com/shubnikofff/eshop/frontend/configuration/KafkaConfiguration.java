package com.shubnikofff.eshop.frontend.configuration;

import com.shubnikofff.eshop.commons.kafka.message.CreateCustomerCommandMessage;
import com.shubnikofff.eshop.commons.kafka.message.CustomerEventMessage;
import com.shubnikofff.eshop.commons.kafka.message.UpdateCustomerCommand;
import com.shubnikofff.eshop.commons.kafka.topic.KafkaTopics;
import com.shubnikofff.eshop.commons.kafka.util.KafkaReceiverFactory;
import com.shubnikofff.eshop.commons.kafka.util.KafkaSenderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.sender.KafkaSender;

import java.util.Collections;

@Configuration
public class KafkaConfiguration {

	private final KafkaSenderFactory kafkaSenderFactory;

	private final KafkaReceiverFactory kafkaReceiverFactory;

	public KafkaConfiguration(KafkaConfigurationProperties properties) {
		kafkaSenderFactory = new KafkaSenderFactory(properties.getBootstrapServers());
		kafkaReceiverFactory = new KafkaReceiverFactory(properties.getBootstrapServers(), properties.getGroupId());
	}

	@Bean
	public KafkaSender<Object, CreateCustomerCommandMessage> createCustomerCommandSender() {
		return kafkaSenderFactory.create();
	}

	@Bean
	public KafkaSender<Object, UpdateCustomerCommand> updateCustomerCommandSender() {
		return kafkaSenderFactory.create();
	}

	@Bean
	public KafkaReceiver<Object, CustomerEventMessage> customerEventsReceiver() {
		return kafkaReceiverFactory.create(Collections.singleton(KafkaTopics.CUSTOMER_EVENT_TOPIC), CustomerEventMessage.class);
	}
}
