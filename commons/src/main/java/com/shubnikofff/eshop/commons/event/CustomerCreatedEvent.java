package com.shubnikofff.eshop.commons.event;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class CustomerCreatedEvent implements BaseEvent {

	private UUID customerId;

	private String customerName;

	private boolean isActive;

	public CustomerCreatedEvent() {
	}

	@Override
	public String getEventName() {
		return "CUSTOMER_CREATED_EVENT";
	}

	public CustomerCreatedEvent(
			@JsonProperty("customerId") UUID customerId,
			@JsonProperty("customerName") String customerName,
			@JsonProperty("isActive") boolean isActive
	) {
		this.customerId = customerId;
		this.customerName = customerName;
		this.isActive = isActive;
	}

	public UUID getCustomerId() {
		return customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	@JsonProperty("isActive")
	public boolean isActive() {
		return isActive;
	}
}
