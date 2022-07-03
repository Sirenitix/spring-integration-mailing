package com.nurs.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    @NotNull
    private Long orderId;
    @NotNull
    private String creditCardNumber;
}
