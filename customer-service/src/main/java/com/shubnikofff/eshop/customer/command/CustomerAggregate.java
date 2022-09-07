package com.shubnikofff.eshop.customer.command;

import com.shubnikofff.eshop.commons.event.CustomerCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

@Aggregate
public class CustomerAggregate {

	@AggregateIdentifier
	private UUID id;

	private String name;

	public CustomerAggregate() {
	}

	@CommandHandler
	public CustomerAggregate(CreateCustomerCommand createCustomerCommand) {
		final var customerCreatedEvent = new CustomerCreatedEvent(createCustomerCommand.getCustomerId(), createCustomerCommand.getCustomerName());
		AggregateLifecycle.apply(customerCreatedEvent);
	}

	@EventSourcingHandler
	public void on(CustomerCreatedEvent customerCreatedEvent) {
		id = customerCreatedEvent.getCustomerId();
		name = customerCreatedEvent.getCustomerName();
	}
}
