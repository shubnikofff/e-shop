package com.shubnikofff.eshop.frontend.api;

import com.shubnikofff.eshop.commons.event.BaseEvent;
import com.shubnikofff.eshop.frontend.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/events")
public class EventsController {

	private final CustomerService customerService;

	@GetMapping(value = "/customer")
	public Flux<ServerSentEvent<BaseEvent>> getCustomerEventStream() {
		return customerService.getCustomerEvents();
	}
}
