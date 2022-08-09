package com.shubnikofff.eshop.frontend.service;

import com.shubnikofff.eshop.commons.kafka.topic.KafkaTopics;
import com.shubnikofff.eshop.frontend.dto.CreateCustomerRequest;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class CustomerService {

	private final Sinks.Many<Object> eventPublisher = Sinks.many().multicast().directBestEffort();

	private final KafkaReceiver<Integer, String> customerEventsReceiver;

	private final KafkaSender<Integer, String> kafkaSender;

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

	public Mono<String> sendCreateCustomerCommand(CreateCustomerRequest createCustomerRequest) {
		final var producerRecord = new ProducerRecord<Integer, String>(
				KafkaTopics.CUSTOMER_EVENT_TOPIC, "Customer " + createCustomerRequest.name() + " created");


		final var senderRecord = SenderRecord.create(producerRecord, createCustomerRequest);

		kafkaSender.send(Mono.just(senderRecord)).subscribe();

		return Mono.just(createCustomerRequest.name());
	}
}
