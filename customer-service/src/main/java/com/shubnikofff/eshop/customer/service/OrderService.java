package com.shubnikofff.eshop.customer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

//	final private KafkaTemplate<Object, Object> kafkaTemplate;

	public <T> Class<T>  createOrder (T order) {
		return (Class<T>) order.getClass();
//		kafkaTemplate.send("orders.events.v1", order);
	}
}
