package com.shubnikofff.eshop.customer.service;

import com.shubnikofff.eshop.commons.kafka.KafkaTopics;
import com.shubnikofff.eshop.commons.request.CreateCustomerRequest;
import com.shubnikofff.eshop.commons.request.ToggleCustomerRequest;
import com.shubnikofff.eshop.customer.command.CreateCustomerCommand;
import com.shubnikofff.eshop.customer.command.ToggleCustomerCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@KafkaListener(topics = KafkaTopics.CUSTOMER_COMMAND_TOPIC, containerFactory = "kafkaListenerContainerFactory")
public class CustomerCommandListener {

	private final CommandGateway commandGateway;

	@KafkaHandler
	void handleCreateCustomerRequest(CreateCustomerRequest request) {
		log.info("Received request {}", request);

		commandGateway.<UUID>send(
				CreateCustomerCommand.builder()
						.customerId(UUID.randomUUID())
						.customerName(request.customerName())
						.build()
		).thenAccept(System.out::println);
	}

	@KafkaHandler
	void handleToggleCustomerRequest(ToggleCustomerRequest request) {
		log.info("Received request {}", request);

		commandGateway.send(new ToggleCustomerCommand(request.customerId()))
				.exceptionally((e) -> {
					e.printStackTrace();
					return e;
				})
				.thenAccept(System.out::println);
	}

	@KafkaHandler(isDefault = true)
	void defaultHandler(Object message) {
		log.warn("No handlers found for message {}", message);
	}
}
