package com.shubnikofff.eshop.commons.kafka.message;

import java.io.Serializable;
import java.math.BigDecimal;

public record CreateCustomerCommandMessage(
		String customerName,
		BigDecimal initialBalance) implements Serializable {
}
