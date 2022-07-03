package com.nurs.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    @JoinColumn(name = "order_id")
    @NotNull
    private Order order;
    @NotNull
    private String creditCardNumber;

    public Payment(Order order, String creditCardNumber) {
        this.order = order;
        this.creditCardNumber = creditCardNumber;
    }
}
