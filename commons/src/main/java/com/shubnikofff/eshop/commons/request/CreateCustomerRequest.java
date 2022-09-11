package com.shubnikofff.eshop.commons.request;


import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CreateCustomerRequest(@NotNull String customerName, @NotNull BigDecimal initialBalance) {
}
