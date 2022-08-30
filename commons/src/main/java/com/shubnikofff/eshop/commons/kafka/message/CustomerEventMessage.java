package com.shubnikofff.eshop.commons.kafka.message;

import java.io.Serializable;
import java.math.BigDecimal;

public record CustomerEventMessage(
		String customerName,
		BigDecimal balance,
		String description) implements Serializable {
}
