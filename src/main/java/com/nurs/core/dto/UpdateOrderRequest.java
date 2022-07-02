package com.nurs.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderRequest {

    @NonNull
    private Long id;
    @NonNull
    private String date;
    @NonNull
    private BigDecimal amount;
    @NonNull
    private Boolean paid;

}