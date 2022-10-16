package com.shubnikofff.eshop.frontend.service;

import com.shubnikofff.eshop.commons.event.BaseEvent;
import com.shubnikofff.eshop.commons.kafka.topic.KafkaTopics;
import com.shubnikofff.eshop.commons.request.CreateCustomerRequest;
import com.shubnikofff.eshop.commons.request.ToggleCustomerRequest;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.ServerSentEvent;
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
	private final Sinks.Many<ServerSentEvent<BaseEvent>> eventPublisher = Sinks.many().multicast().directBestEffort();

	private final KafkaReceiver<Object, ? extends BaseEvent> customerEventsReceiver;

	private final KafkaSender<Object, CreateCustomerRequest> createCustomerRequestSender;

	private final KafkaSender<Object, ToggleCustomerRequest> toggleCustomerRequestSender;

	@PostConstruct
	private void consumeEvents() {
		customerEventsReceiver.receive()
				.doOnNext(record -> {
					final var value = record.value();
					eventPublisher.tryEmitNext(ServerSentEvent.<BaseEvent>builder()
							.event(value.getEventName())
							.data(value)
							.build()
					);
					record.receiverOffset().acknowledge();
				})
				.subscribeOn(Schedulers.boundedElastic())
				.subscribe();
	}

	public Flux<ServerSentEvent<BaseEvent>> getCustomerEvents() {
		return eventPublisher.asFlux();
	}

	public Flux<Object> createCustomer(CreateCustomerRequest createCustomerRequest) {
		final var producerRecord = new ProducerRecord<>(KafkaTopics.CUSTOMER_COMMAND_TOPIC, createCustomerRequest);
		final var senderRecord = SenderRecord.create(producerRecord, createCustomerRequest);

		return createCustomerRequestSender.send(Mono.just(senderRecord)).map(SenderResult::correlationMetadata);
	}

	public Flux<Object> toggleCustomer(ToggleCustomerRequest toggleCustomerRequest) {
		final var producerRecord = new ProducerRecord<>(KafkaTopics.CUSTOMER_COMMAND_TOPIC, toggleCustomerRequest);
		final var senderRecord = Mono.just(SenderRecord.create(producerRecord, toggleCustomerRequest));

		return toggleCustomerRequestSender.send(senderRecord).map(SenderResult::correlationMetadata);
	}
}
