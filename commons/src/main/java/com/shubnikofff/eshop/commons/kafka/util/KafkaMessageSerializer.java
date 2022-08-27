package com.shubnikofff.eshop.commons.kafka.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shubnikofff.eshop.commons.kafka.message.MessageHeaders;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.util.logging.Logger;

final public class KafkaMessageSerializer<T> implements Serializer<T> {

	private final static Logger logger = Logger.getLogger(KafkaMessageSerializer.class.getName());

	private final static ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public byte[] serialize(String topic, T data) {
		if(data == null) {
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
		try {
			headers.add(MessageHeaders.TYPE, objectMapper.writeValueAsBytes(data.getClass().getName()));
		} catch (JsonProcessingException e) {
			throw new SerializationException("Error when serializing kafka message header to byte[]");
		}
		return serialize(topic, data);
	}
}
