package com.nurs.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderRequest {

    @NotNull
    private Long id;
    @NotNull
    private String date;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private Boolean paid;
    @NotNull
    private String email;

}