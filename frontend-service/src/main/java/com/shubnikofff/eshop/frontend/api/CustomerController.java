package com.shubnikofff.eshop.frontend.api;

import com.shubnikofff.eshop.frontend.dto.CreateCustomerRequest;
import com.shubnikofff.eshop.frontend.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

	private final CustomerService customerService;

	@PostMapping(value = "/")
	public Flux<Object> createCustomer(@RequestBody @Valid CreateCustomerRequest request) {
		return customerService.sendCreateCustomerCommand(request);
	}
}
