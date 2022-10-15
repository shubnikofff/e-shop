package com.shubnikofff.eshop.customer.query;

import com.shubnikofff.eshop.commons.event.CustomerCreatedEvent;
import com.shubnikofff.eshop.commons.event.CustomerDisabledEvent;
import com.shubnikofff.eshop.commons.event.CustomerEnabledEvent;
import com.shubnikofff.eshop.customer.exception.CustomerNotFoundException;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("customer-service")
public class CustomerProjector {

	private final CustomerViewRepository repository;

	public CustomerProjector(CustomerViewRepository repository) {
		this.repository = repository;
	}

	@EventHandler
	public void on(CustomerCreatedEvent event) {
		repository.save(new CustomerView(
				event.getCustomerId(),
				event.getCustomerName(),
				event.isActive()
		));
	}

	@EventHandler
	public void on(CustomerEnabledEvent event) throws CustomerNotFoundException {
		final var customerView = repository.findById(event.getCustomerId())
				.orElseThrow(() -> new CustomerNotFoundException(event.getCustomerId()));

		customerView.setActive(true);
		repository.save(customerView);
	}

	@EventHandler
	public void on(CustomerDisabledEvent event) throws CustomerNotFoundException {
		final var customerView = repository.findById(event.getCustomerId())
				.orElseThrow(() -> new CustomerNotFoundException(event.getCustomerId()));

		customerView.setActive(false);
		repository.save(customerView);
	}
}
