package com.shubnikofff.eshop.customer.job;

import com.shubnikofff.eshop.customer.model.OrderMessage;
import com.shubnikofff.eshop.customer.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MakeOrderJob {

	private final OrderService orderService;

	private int counter = 0;

	@Scheduled(cron = "*/2 * * * * *")
	public void makeOrder() {
		orderService.createOrder(new OrderMessage(++counter));
	}
}
