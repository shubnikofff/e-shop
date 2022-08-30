package com.shubnikofff.eshop.customer.service;

import com.shubnikofff.eshop.commons.kafka.message.CreateCustomerCommandMessage;
import com.shubnikofff.eshop.commons.kafka.message.CustomerEventMessage;
import com.shubnikofff.eshop.commons.kafka.message.UpdateCustomerCommandMessage;
import com.shubnikofff.eshop.commons.kafka.topic.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@KafkaListener(topics = KafkaTopics.CUSTOMER_COMMAND_TOPIC, containerFactory = "kafkaListenerContainerFactory")
public class CustomerService {

	private final KafkaTemplate<UUID, CustomerEventMessage> customerEventTemplate;

	@KafkaHandler
	void handleCreateCustomerCommand(CreateCustomerCommandMessage message) {
		log.info("Received message {}", message);
		customerEventTemplate.send(KafkaTopics.CUSTOMER_EVENT_TOPIC, new CustomerEventMessage(
				message.customerName(),
				message.initialBalance(),
				"Customer created"
		));
	}

	@KafkaHandler
	void handleUpdateCustomerCommand(UpdateCustomerCommandMessage message) {
		log.info("Received message {}", message);
		customerEventTemplate.send(KafkaTopics.CUSTOMER_EVENT_TOPIC, new CustomerEventMessage(
				message.customerName(),
				BigDecimal.valueOf(10050),
				"Customer updated"
		));
	}

	@KafkaHandler(isDefault = true)
	void defaultHandler(Object message) {
		log.warn("No handlers found for message {}", message);
	}
}
