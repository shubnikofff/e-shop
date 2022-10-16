package com.shubnikofff.eshop.customer.exception;

import java.util.UUID;

public class CustomerNotFoundException extends Exception {
	private final UUID customerId;

	public CustomerNotFoundException(UUID customerId) {
		this.customerId = customerId;
	}
}
