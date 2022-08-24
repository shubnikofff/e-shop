package com.shubnikofff.eshop.customer.configuration;

import com.shubnikofff.eshop.commons.kafka.message.CreateCustomerCommandMessage;
import com.shubnikofff.eshop.commons.kafka.message.CustomerEventMessage;
import com.shubnikofff.eshop.commons.kafka.util.KafkaFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class KafkaConfiguration {

	private final KafkaFactory kafkaFactory;

	public KafkaConfiguration(KafkaConfigurationProperties properties) {
		kafkaFactory = new KafkaFactory(properties.getBootstrapServers(), properties.getGroupId());
	}

	@Bean
	public KafkaTemplate<String, CustomerEventMessage> customerEventTemplate() {
		return new KafkaTemplate<>(kafkaFactory.createProducerFactory());
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, CreateCustomerCommandMessage> createCustomerCommandListener() {
		return kafkaFactory.createListenerContainerFactory(CreateCustomerCommandMessage.class);
	}
}
