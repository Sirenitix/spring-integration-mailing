package com.nurs.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteOrderRequest {

    @NonNull
    private Long id;
}