package com.shubnikofff.eshop.customer.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfiguration {

	@Bean
	public NewTopic orderEventsTopic() {
		return new NewTopic("orders.events.v1", 1, (short) 1);
	}
}
