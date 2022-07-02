package com.nurs.core.controller;


import com.nurs.core.entity.Order;
import com.nurs.core.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/getOrder")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();

    }
}
