package com.shubnikofff.eshop.customer.command;

import com.shubnikofff.eshop.commons.event.CustomerCreatedEvent;
import com.shubnikofff.eshop.commons.event.CustomerDisabledEvent;
import com.shubnikofff.eshop.commons.event.CustomerEnabledEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

@Aggregate
public class CustomerAggregate {

	@AggregateIdentifier
	private UUID customerId;

	private String name;

	private boolean isActive;

	public CustomerAggregate() {
	}

	@CommandHandler
	public CustomerAggregate(CreateCustomerCommand createCustomerCommand) {
		final var customerCreatedEvent = new CustomerCreatedEvent(createCustomerCommand.getCustomerId(), createCustomerCommand.getCustomerName(), false);
		AggregateLifecycle.apply(customerCreatedEvent);
	}

	@EventSourcingHandler
	public void on(CustomerCreatedEvent customerCreatedEvent) {
		customerId = customerCreatedEvent.getCustomerId();
		name = customerCreatedEvent.getCustomerName();
		isActive = customerCreatedEvent.isActive();
	}

	@CommandHandler
	public void handle(ToggleCustomerCommand toggleCustomerCommand) {
		final var event = isActive ? new CustomerDisabledEvent(toggleCustomerCommand.getCustomerId()) : new CustomerEnabledEvent(toggleCustomerCommand.getCustomerId());
		AggregateLifecycle.apply(event);
	}

	@EventSourcingHandler
	public void on(CustomerEnabledEvent customerEnabledEvent) {
		isActive = true;
	}

	@EventSourcingHandler
	public void on(CustomerDisabledEvent customerEnabledEvent) {
		isActive = false;
	}
}
