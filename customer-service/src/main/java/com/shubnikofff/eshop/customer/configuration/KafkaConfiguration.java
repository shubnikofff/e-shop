package com.shubnikofff.eshop.customer.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

import java.util.UUID;

@Configuration
@EnableKafka
public class KafkaConfiguration {

	@Bean
	public ConcurrentKafkaListenerContainerFactory<UUID, Object> kafkaListenerContainerFactory(ConsumerFactory<UUID, Object> factory) {
		final var containerFactory = new ConcurrentKafkaListenerContainerFactory<UUID, Object>();
		containerFactory.setConsumerFactory(factory);
		return containerFactory;
	}
}
