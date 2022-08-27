package com.shubnikofff.eshop.commons.kafka.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shubnikofff.eshop.commons.kafka.message.MessageHeaders;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.kafka.support.serializer.DeserializationException;

import java.io.IOException;
import java.util.logging.Logger;


public class KafkaMessageDeserializer<T> implements Deserializer<T> {

	private final static Logger logger = Logger.getLogger(KafkaMessageDeserializer.class.getName());

	private final static ObjectMapper objectMapper = new ObjectMapper();

	private Class<T> valueType;

	@Override
	public T deserialize(String topic, byte[] data) {
		if (data == null) {
			logger.warning("Null received at deserializing");
			return null;
		}

		try {
			return objectMapper.readValue(data, valueType);
		} catch (IOException e) {
			throw new DeserializationException("Error when deserializing kafka message", data, false, e.getCause());
		}
	}

	@Override
	public T deserialize(String topic, Headers headers, byte[] data) {
		try {
			final var className = objectMapper.readValue(headers.lastHeader(MessageHeaders.TYPE).value(), String.class);
			valueType = (Class<T>) Class.forName(className);
		} catch (IOException e) {
			throw new DeserializationException("Error when deserializing header type of kafka message", headers.lastHeader("type").value(), false, e.getCause());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Cannot find message class at deserializing", e.getCause());
		}

		return deserialize(topic, data);
	}
}
