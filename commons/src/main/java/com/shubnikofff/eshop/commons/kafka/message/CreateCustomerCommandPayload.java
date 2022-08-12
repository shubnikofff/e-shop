package com.shubnikofff.eshop.commons.kafka.message;

import java.math.BigDecimal;

public record CreateCustomerCommandPayload(
		String customerName,
		BigDecimal initialBalance
) {
}
