package com.shubnikofff.eshop.commons.kafka.message;

import java.io.Serializable;

public record UpdateCustomerCommandMessage(String customerName) implements Serializable {
}
