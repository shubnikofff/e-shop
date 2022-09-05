package com.shubnikofff.eshop.commons.event;

import java.util.UUID;

public record CustomerCreatedEvent(
		UUID customerId,
		String customerName
) {
}
