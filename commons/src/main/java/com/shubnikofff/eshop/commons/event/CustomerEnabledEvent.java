package com.shubnikofff.eshop.commons.event;

import java.util.UUID;

public class CustomerEnabledEvent implements BaseEvent {

	private UUID customerId;

	public CustomerEnabledEvent() {
	}

	public CustomerEnabledEvent(UUID customerId) {
		this.customerId = customerId;
	}

	public UUID getCustomerId() {
		return customerId;
	}

	@Override
	public String getEventName() {
		return "CUSTOMER_ENABLED";
	}
}
