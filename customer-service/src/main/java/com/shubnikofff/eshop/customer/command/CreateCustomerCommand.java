package com.shubnikofff.eshop.customer.command;

import lombok.Builder;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Getter
@Builder
public class CreateCustomerCommand {
	@TargetAggregateIdentifier
	public UUID customerId;
	private String customerName;
}
