package com.shubnikofff.eshop.customer.query;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerViewRepository extends JpaRepository<CustomerView, UUID> {
}
