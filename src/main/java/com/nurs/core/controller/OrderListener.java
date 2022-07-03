package com.nurs.core.controller;

import com.nurs.core.config.MQConfig;
import com.nurs.core.dto.DeleteOrderRequest;
import com.nurs.core.dto.PaymentRequest;
import com.nurs.core.dto.UpdateOrderRequest;
import com.nurs.core.entity.Order;
import com.nurs.core.service.OrderService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Component
@RequiredArgsConstructor
public class OrderListener {

    private final OrderService orderService;

    @RabbitListener(queues = MQConfig.CREATE_ORDER_QUEUE)
    public void orderListener(@NonNull Order order) {
        log.info(order + " - order");
        orderService.createOrder(order);
    }

    @RabbitListener(queues = MQConfig.DELETE_ORDER_QUEUE)
    public void deleteOrderListener(@NonNull DeleteOrderRequest deleteOrderRequest) {
        log.info(deleteOrderRequest.getId() + " - order id to delete");
        orderService.deleteOrder(deleteOrderRequest.getId());
    }

    @RabbitListener(queues = MQConfig.UPDATE_ORDER_QUEUE)
    public void updateOrderListener(@NonNull UpdateOrderRequest updateOrderRequest) {
        log.info(updateOrderRequest + " - order to update");
        orderService.updateOrder(updateOrderRequest);
    }

    @RabbitListener(queues = MQConfig.CREATE_PAYMENT_QUEUE)
    public void paymentListener(@NonNull PaymentRequest paymentRequest) {
        log.info(paymentRequest + " - payment");
        orderService.createPayment(paymentRequest);
    }

//    @Scheduled(fixedDelay = 1000, initialDelay = 1000)
//    public void scheduleFixedDelayTask() {
//        log.info(
//                "Fixed delay task - " + System.currentTimeMillis() / 1000);
//    }

}
