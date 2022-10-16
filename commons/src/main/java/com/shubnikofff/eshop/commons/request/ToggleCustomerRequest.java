package com.shubnikofff.eshop.commons.request;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public record ToggleCustomerRequest(@NotNull UUID customerId) {
}
