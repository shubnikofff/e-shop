package com.shubnikofff.eshop.customer.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "kafka")
@Data
public class KafkaConfigurationProperties {
	private String bootstrapServers;
	private String groupId;
}
