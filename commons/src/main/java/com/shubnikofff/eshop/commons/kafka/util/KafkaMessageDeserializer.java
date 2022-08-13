package com.shubnikofff.eshop.commons.kafka.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;


public class KafkaMessageDeserializer<T> implements Deserializer<T> {

	private final static ObjectMapper objectMapper = new ObjectMapper();

	private final Class<T> valueType;

	public KafkaMessageDeserializer(Class<T> valueType) {
		this.valueType = valueType;
	}

	@Override
	public T deserialize(String topic, byte[] data) {
		if (data == null) {
			return null;
		}

		try {
			return objectMapper.readValue(data, valueType);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
