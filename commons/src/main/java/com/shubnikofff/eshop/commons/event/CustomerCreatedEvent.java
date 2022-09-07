package com.shubnikofff.eshop.commons.event;

import java.util.UUID;

public class CustomerCreatedEvent extends BaseEvent {

	public static final String EVENT_NAME = "CUSTOMER_CREATED_EVENT";

	private UUID customerId;

	private String customerName;

	public CustomerCreatedEvent() {
		super(EVENT_NAME);
	}

	public CustomerCreatedEvent(UUID customerId, String customerName) {
		super(EVENT_NAME);
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
