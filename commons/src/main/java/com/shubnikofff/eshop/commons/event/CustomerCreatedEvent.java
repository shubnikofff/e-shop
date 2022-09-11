package com.shubnikofff.eshop.commons.event;

import java.util.UUID;

public class CustomerCreatedEvent extends BaseEvent {

	private UUID customerId;

	private String customerName;

	public CustomerCreatedEvent() {
	}

	@Override
	public String getEventName() {
		return "CUSTOMER_CREATED_EVENT";
	}

	public CustomerCreatedEvent(UUID customerId, String customerName) {
		this.customerId = customerId;
		this.customerName = customerName;
	}

	public UUID getCustomerId() {
		return customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

}
