package com.shubnikofff.eshop.commons.kafka.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shubnikofff.eshop.commons.kafka.KafkaHeaders;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

final public class MessageSerializer<T> implements Serializer<T> {

	private final static Logger logger = Logger.getLogger(MessageSerializer.class.getName());

	private final static ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public byte[] serialize(String topic, T data) {
		if (data == null) {
			logger.warning("Null received at serializing");
			return null;
		}

		try {
			return objectMapper.writeValueAsBytes(data);
		} catch (Exception e) {
			throw new SerializationException("Error when serializing kafka message to byte[]");
		}
	}

	@Override
	public byte[] serialize(String topic, Headers headers, T data) {
		headers.add(KafkaHeaders.CONTENT_TYPE, data.getClass().getName().getBytes(StandardCharsets.UTF_8));
		return serialize(topic, data);
	}
}
