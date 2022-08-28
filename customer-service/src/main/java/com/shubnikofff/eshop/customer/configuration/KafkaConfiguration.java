package com.shubnikofff.eshop.customer.configuration;

import com.shubnikofff.eshop.commons.kafka.message.CreateCustomerCommandMessage;
import com.shubnikofff.eshop.commons.kafka.message.CustomerEventMessage;
import com.shubnikofff.eshop.commons.kafka.message.UpdateCustomerCommand;
import com.shubnikofff.eshop.commons.kafka.util.KafkaMessageDeserializer;
import com.shubnikofff.eshop.commons.kafka.util.KafkaMessageSerializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

@Configuration
public class KafkaConfiguration {

	private final Map<String, Object> producerConfig;

	private final Map<String, Object> consumerConfig;

	public KafkaConfiguration(KafkaConfigurationProperties properties) {
		producerConfig = Map.of(
				ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers(),
				ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
				ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaMessageSerializer.class
		);

		consumerConfig = Map.of(
				ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers(),
				ConsumerConfig.GROUP_ID_CONFIG, properties.getGroupId(),
				ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
				ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaMessageDeserializer.class
		);
	}

	@Bean
	public KafkaTemplate<Object, CustomerEventMessage> customerEventTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}

	@Bean(name = "createCustomerCommandListener")
	public ConcurrentKafkaListenerContainerFactory<String, CreateCustomerCommandMessage> createCustomerCommandListener() {
		return listenerContainerFactory();
	}

	@Bean(name = "updateCustomerCommandListener")
	public ConcurrentKafkaListenerContainerFactory<String, UpdateCustomerCommand> updateCustomerCommandListener() {
		return listenerContainerFactory();
	}

	private  <K, V> ProducerFactory<K, V> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfig);
	}

	public <K, V> ConcurrentKafkaListenerContainerFactory<K, V> listenerContainerFactory() {
		final var consumerFactory = new DefaultKafkaConsumerFactory<>(consumerConfig);
		final var listenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<K, V>();
		listenerContainerFactory.setConsumerFactory(consumerFactory);
		return listenerContainerFactory;
	}

//	@Bean
//	public RecordMessageConverter multiTypeMessageConverter() {
//		final var typeMapper = new DefaultJackson2JavaTypeMapper();
//		typeMapper.setTypePrecedence(Jackson2JavaTypeMapper.TypePrecedence.TYPE_ID);
//		typeMapper.addTrustedPackages("com.shubnikofff.eshop.commons.kafka.message");
//		typeMapper.setIdClassMapping(messageClassMap);
//
//		final var converter = new StringJsonMessageConverter();
//		converter.setTypeMapper(typeMapper);
//		return converter;
//	}
//
//	@Bean
//	public ConsumerFactory<String, Object> multiTypeConsumerFactory() {
//		return new DefaultKafkaConsumerFactory<>(Map.of(
//				ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
//				ConsumerConfig.GROUP_ID_CONFIG, "customer-service",
//				ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
//				ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class
//		));
//	}
//
//	@Bean
//	public ConcurrentKafkaListenerContainerFactory<String, Object> multiTypeKafkaListenerContainerFactory() {
//		final var containerFactory = new ConcurrentKafkaListenerContainerFactory<String, Object>();
//		containerFactory.setConsumerFactory(multiTypeConsumerFactory());
//		containerFactory.setMessageConverter(multiTypeMessageConverter());
//		return containerFactory;
//	}
}
