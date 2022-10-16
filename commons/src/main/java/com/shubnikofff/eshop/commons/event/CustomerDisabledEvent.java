package com.shubnikofff.eshop.commons.event;

import java.util.UUID;

public class CustomerDisabledEvent implements BaseEvent {

	private UUID customerId;

	public CustomerDisabledEvent() {
	}

	public CustomerDisabledEvent(UUID customerId) {
		this.customerId = customerId;
	}

	public UUID getCustomerId() {
		return customerId;
	}

	@Override
	public String getEventName() {
		return "CUSTOMER_DISABLED";
	}
}
