package com.shubnikofff.eshop.commons.kafka.serialization;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.shubnikofff.eshop.commons.kafka.message.MessageHeaders;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.kafka.support.serializer.DeserializationException;

import java.io.IOException;
import java.util.logging.Logger;


public class MessageDeserializer<T> implements Deserializer<T> {

	private final static Logger logger = Logger.getLogger(MessageDeserializer.class.getName());

	private final static ObjectMapper objectMapper = new ObjectMapper();

	private JavaType dataType;

	@Override
	public T deserialize(String topic, byte[] data) {
		if (data == null) {
			logger.warning("Null received at deserializing");
			return null;
		}

		try {
			return objectMapper.readValue(data, dataType);
		} catch (IOException e) {
			throw new DeserializationException("Error when deserializing kafka message", data, false, e.getCause());
		}
	}

	@Override
	public T deserialize(String topic, Headers headers, byte[] data) {
		dataType = resolveType(topic, data, headers);

		return deserialize(topic, data);
	}

	public static JavaType resolveType(String topic, byte[] data, Headers headers) {
		final var type = new String(headers.lastHeader(MessageHeaders.TYPE).value());
		return TypeFactory.defaultInstance().constructFromCanonical(type);
	}
}
