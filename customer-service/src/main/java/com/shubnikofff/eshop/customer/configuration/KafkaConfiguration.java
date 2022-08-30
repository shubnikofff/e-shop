package com.shubnikofff.eshop.customer.configuration;

import com.shubnikofff.eshop.commons.kafka.message.CustomerEventMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.UUID;

@Configuration
public class KafkaConfiguration {

	@Bean
	public KafkaTemplate<UUID, CustomerEventMessage> customerEventTemplate(ProducerFactory<UUID, CustomerEventMessage> producerFactory) {
		return new KafkaTemplate<>(producerFactory);
	}
	@Bean
	public ConcurrentKafkaListenerContainerFactory<UUID, Object> kafkaListenerContainerFactory(ConsumerFactory<UUID, Object> factory) {
		final var containerFactory = new ConcurrentKafkaListenerContainerFactory<UUID, Object>();
		containerFactory.setConsumerFactory(factory);
		return containerFactory;
	}
}
