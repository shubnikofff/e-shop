package com.shubnikofff.eshop.frontend.dto;


import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CreateCustomerRequest(@NotNull String name, @NotNull BigDecimal initialBalance) {
}
