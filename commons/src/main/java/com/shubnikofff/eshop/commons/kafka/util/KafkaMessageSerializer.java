package com.shubnikofff.eshop.commons.kafka.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.util.logging.Logger;

final public class KafkaMessageSerializer<T> implements Serializer<T> {

	private final static Logger logger = Logger.getLogger(KafkaMessageSerializer.class.getName());

	private final static ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public byte[] serialize(String topic, T message) {
		if(message == null) {
			logger.warning("Null received at serializing");
			return null;
		}

		try {
			return objectMapper.writeValueAsBytes(message);
		} catch (Exception e) {
			throw new SerializationException("Error when serializing kafka message to byte[]");
		}
	}
}
