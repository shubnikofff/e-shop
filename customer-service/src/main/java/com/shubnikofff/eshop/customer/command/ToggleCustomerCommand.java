package com.shubnikofff.eshop.customer.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class ToggleCustomerCommand {

	@TargetAggregateIdentifier
	public UUID customerId;
}
