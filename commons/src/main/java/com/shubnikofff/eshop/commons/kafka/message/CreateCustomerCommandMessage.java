package com.shubnikofff.eshop.commons.kafka.message;

import java.math.BigDecimal;

public record CreateCustomerCommandMessage(
		String customerName,
		BigDecimal initialBalance
) {
}
