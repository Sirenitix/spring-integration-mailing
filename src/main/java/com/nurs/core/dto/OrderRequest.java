package com.nurs.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    @DecimalMin(value = "0.0",inclusive = false)
    @DecimalMax(value = "10000000.0",inclusive = false)
    @NotNull
    private BigDecimal amount;

    @Email
    @NotNull
    private String email;
}
