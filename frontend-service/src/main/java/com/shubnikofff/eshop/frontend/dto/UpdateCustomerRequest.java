package com.shubnikofff.eshop.frontend.dto;


import javax.validation.constraints.NotNull;

public record UpdateCustomerRequest(@NotNull String name) {
}
