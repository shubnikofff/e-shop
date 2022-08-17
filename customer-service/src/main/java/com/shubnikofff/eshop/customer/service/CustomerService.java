package com.shubnikofff.eshop.customer.service;

import com.shubnikofff.eshop.commons.kafka.message.CreateCustomerCommandMessage;
import com.shubnikofff.eshop.commons.kafka.topic.KafkaTopics;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

	@KafkaListener(topics = KafkaTopics.CUSTOMER_COMMAND_TOPIC, containerFactory = "greetingKafkaListenerContainerFactory")
	void receiveCustomerCommand(CreateCustomerCommandMessage message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
		System.out.println("Customer name: " + message.customerName() + " topic: " + topic);
	}
}
