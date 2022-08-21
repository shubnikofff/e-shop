package com.shubnikofff.eshop.customer.service;

import com.shubnikofff.eshop.commons.kafka.message.CreateCustomerCommandMessage;
import com.shubnikofff.eshop.commons.kafka.message.CustomerEventMessage;
import com.shubnikofff.eshop.commons.kafka.topic.KafkaTopics;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

	private final KafkaTemplate<String, CustomerEventMessage> customerEventTemplate;

	@KafkaListener(topics = KafkaTopics.CUSTOMER_COMMAND_TOPIC, containerFactory = "createCustomerCommandListener")
	void receiveCustomerCommand(CreateCustomerCommandMessage message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
		System.out.println("Customer name: " + message.customerName() + " topic: " + topic);
		customerEventTemplate.send(KafkaTopics.CUSTOMER_EVENT_TOPIC, new CustomerEventMessage(
				message.customerName(),
				message.initialBalance(),
				"Customer created"
		));
	}
}
