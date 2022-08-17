package com.shubnikofff.eshop.customer.configuration;

import com.shubnikofff.eshop.commons.kafka.message.CreateCustomerCommandMessage;
import com.shubnikofff.eshop.commons.kafka.util.KafkaMessageDeserializer;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Collections;
import java.util.Map;

@Configuration
public class KafkaConfiguration {

	@Bean
	public NewTopic orderEventsTopic() {
		return new NewTopic("orders.events.v1", 1, (short) 1);
	}

//	@Bean
	private  <T> ConsumerFactory<String, T> greetingConsumerFactory(Class<T> valueType) {
		// ...
		return new DefaultKafkaConsumerFactory<>(
				Map.of(
						ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
						ConsumerConfig.GROUP_ID_CONFIG, "customer-service"
				),
				new StringDeserializer(),
				new JsonDeserializer<>(valueType)
		);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, CreateCustomerCommandMessage>
	greetingKafkaListenerContainerFactory() {

		final var containerFactory = new ConcurrentKafkaListenerContainerFactory<String, CreateCustomerCommandMessage>();
		containerFactory.setConsumerFactory(greetingConsumerFactory(CreateCustomerCommandMessage.class));
		return containerFactory;
	}
}
