package com.shubnikofff.eshop.customer.configuration;

import com.shubnikofff.eshop.commons.kafka.serialization.MessageConverter;
import org.axonframework.extensions.kafka.eventhandling.KafkaMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AxonConfiguration {

	@Bean
	public KafkaMessageConverter<String, byte[]> kafkaMessageConverter() {
		return new MessageConverter();
	}
}
