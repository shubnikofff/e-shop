package com.shubnikofff.eshop.commons.kafka.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shubnikofff.eshop.commons.kafka.message.KafkaMessageHeaders;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.extensions.kafka.eventhandling.KafkaMessageConverter;

import java.util.Optional;

public class MessageConverter implements KafkaMessageConverter<String, byte[]> {

	private final static ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public ProducerRecord<String, byte[]> createKafkaMessage(EventMessage<?> eventMessage, String topic) {

		final var headers = new RecordHeaders(new Header[]{new RecordHeader(KafkaMessageHeaders.CONTENT_TYPE, eventMessage.getPayloadType().getName().getBytes())});

		try {
			return new ProducerRecord<>(
					topic,
					null,
					eventMessage.getTimestamp().toEpochMilli(),
					null,
					objectMapper.writeValueAsBytes(eventMessage.getPayload()),
					headers
			);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Optional<EventMessage<?>> readKafkaMessage(ConsumerRecord<String, byte[]> consumerRecord) {
		return Optional.empty();
	}
}
