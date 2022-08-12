package com.shubnikofff.eshop.frontend.service;

import com.shubnikofff.eshop.commons.kafka.message.CreateCustomerCommandPayload;
import com.shubnikofff.eshop.commons.kafka.message.KafkaMessage;
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

	private final KafkaSender<Object, KafkaMessage> kafkaSender;

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

		final var kafkaMessage = new KafkaMessage<CreateCustomerCommandPayload>(applicationName, new CreateCustomerCommandPayload(
				createCustomerRequest.name(),
				createCustomerRequest.initialBalance()
		));

		final var producerRecord = new ProducerRecord<Object, KafkaMessage>(KafkaTopics.CUSTOMER_EVENT_TOPIC, kafkaMessage);


		final var senderRecord = SenderRecord.create(producerRecord, createCustomerRequest);

		return kafkaSender.send(Mono.just(senderRecord)).map(SenderResult::correlationMetadata);
	}
}
