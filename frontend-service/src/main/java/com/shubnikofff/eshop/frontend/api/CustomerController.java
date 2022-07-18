package com.shubnikofff.eshop.frontend.api;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.time.Duration.*;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

	@GetMapping(value = "/orders", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Object> getOrders() {
//		Map<String, Object> consumerProps = new HashMap<>();
//		consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//		consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "frontend");
//		consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
//		consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//
//
//		final var receiverOptions = ReceiverOptions.create(consumerProps).subscription(Collections.singleton("orders.events.v1"));
//		return KafkaReceiver.create(receiverOptions).receive()
//				.checkpoint("Message being consumed")
//				.log()
//				.doOnNext(record -> record.receiverOffset().acknowledge())
//				.map(ReceiverRecord::value)
//				.checkpoint("Message are done consumed");
//	}

		return Flux.create(fluxSink -> {
					while (true) {
						fluxSink.next(System.currentTimeMillis());
					}
				}
		);
	}
}
