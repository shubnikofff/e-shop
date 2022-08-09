package com.shubnikofff.eshop.frontend.api;

import com.shubnikofff.eshop.frontend.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/events")
public class EventsController {

	private final CustomerService customerService;

	@GetMapping(value = "/customer", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Object> getCustomerEventStream() {
		return customerService.getCustomerEvents();
	}
}
