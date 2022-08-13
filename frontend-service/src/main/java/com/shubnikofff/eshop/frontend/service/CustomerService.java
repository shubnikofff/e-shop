package com.shubnikofff.eshop.frontend.service;

import com.shubnikofff.eshop.commons.kafka.message.CreateCustomerCommandMessage;
import com.shubnikofff.eshop.commons.kafka.topic.KafkaTopics;
import com.shubnikofff.eshop.frontend.dto.CreateCustomerRequest;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;
import reactor.kafka.sender.SenderResult;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class CustomerService {

	@Value("${spring.application.name}")
	static String applicationName;
	private final Sinks.Many<Object> eventPublisher = Sinks.many().multicast().directBestEffort();

	private final KafkaReceiver<Integer, String> customerEventsReceiver;

	private final KafkaSender<Object, CreateCustomerCommandMessage> createCustomerCommandSender;

	@PostConstruct
	private void consumeEvents() {
		customerEventsReceiver.receive()
				.doOnNext(record -> {
					final var value = record.value();
					eventPublisher.tryEmitNext(value);
					record.receiverOffset().acknowledge();
				})
				.subscribeOn(Schedulers.boundedElastic())
				.subscribe();
	}

	public Flux<Object> getCustomerEvents() {
		return eventPublisher.asFlux();
	}

	public Flux<Object> sendCreateCustomerCommand(CreateCustomerRequest createCustomerRequest) {
		final var producerRecord = new ProducerRecord<>(KafkaTopics.CUSTOMER_EVENT_TOPIC, new CreateCustomerCommandMessage(
				createCustomerRequest.name(),
				createCustomerRequest.initialBalance()
		));
		final var senderRecord = SenderRecord.create(producerRecord, createCustomerRequest);

		return createCustomerCommandSender.send(Mono.just(senderRecord)).map(SenderResult::correlationMetadata);
	}
}
