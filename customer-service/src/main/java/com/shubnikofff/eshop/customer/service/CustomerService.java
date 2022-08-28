package com.shubnikofff.eshop.customer.service;

import com.shubnikofff.eshop.commons.kafka.message.CreateCustomerCommandMessage;
import com.shubnikofff.eshop.commons.kafka.message.CustomerEventMessage;
import com.shubnikofff.eshop.commons.kafka.message.UpdateCustomerCommand;
import com.shubnikofff.eshop.commons.kafka.topic.KafkaTopics;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
//@KafkaListener(id = "customer-service", topics = KafkaTopics.CUSTOMER_COMMAND_TOPIC, containerFactory = )
public class CustomerService {

	private final KafkaTemplate<Object, CustomerEventMessage> customerEventTemplate;

	@KafkaListener(topics = KafkaTopics.CUSTOMER_COMMAND_TOPIC, containerFactory = "createCustomerCommandListener")
//	@KafkaHandler
	void handleCreateCustomerCommand(CreateCustomerCommandMessage message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
		System.out.println("Customer name: " + message.customerName() + " topic: " + topic);
		customerEventTemplate.send(KafkaTopics.CUSTOMER_EVENT_TOPIC, new CustomerEventMessage(
				message.customerName(),
				message.initialBalance(),
				"Customer created"
		));
	}

	@KafkaListener(topics = KafkaTopics.CUSTOMER_COMMAND_TOPIC, containerFactory = "updateCustomerCommandListener")
//	@KafkaHandler
	void handleUpdateCustomerCommand(UpdateCustomerCommand message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
		System.out.println("Customer name: " + message.customerName() + " topic: " + topic);
		customerEventTemplate.send(KafkaTopics.CUSTOMER_EVENT_TOPIC, new CustomerEventMessage(
				message.customerName(),
				BigDecimal.valueOf(10050),
				"Customer updated"
		));
	}
}
