package com.nurs.core.controller;

import com.nurs.core.config.MQConfig;
import com.nurs.core.dto.AllOrdersRequest;
import com.nurs.core.dto.DeleteOrderRequest;
import com.nurs.core.dto.UpdateOrderRequest;
import com.nurs.core.entity.CustomMessage;
import com.nurs.core.entity.Order;
import com.nurs.core.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderListener {

    private final OrderService orderService;

    @RabbitListener(queues = MQConfig.QUEUE)
    public void orderListener(Order order) {
        log.info(order + " - order");
        orderService.createOrder(order);
    }

    @RabbitListener(queues = MQConfig.QUEUE)
    public void deleteOrderListener(DeleteOrderRequest deleteOrderRequest) {
        log.info(deleteOrderRequest.getId() + " - order id to delete");
        orderService.deleteOrder(deleteOrderRequest.getId());
    }

    @RabbitListener(queues = MQConfig.QUEUE)
    public void updateOrderListener(UpdateOrderRequest updateOrderRequest) {
        log.info(updateOrderRequest + " - order to update");
        orderService.updateOrder(updateOrderRequest);
    }

//    @RabbitListener(queues = MQConfig.QUEUE)
//    public void updateOrderListener(AllOrdersRequest allOrdersRequest) {
//        log.info(allOrdersRequest.getOrders()+ " - get all orders");
//        List<Order> orders = orderService.getAllOrders();
//        log.info(orders + "all users");
//    }

//    @Scheduled(fixedDelay = 1000, initialDelay = 1000)
//    public void scheduleFixedDelayTask() {
//        log.info(
//                "Fixed delay task - " + System.currentTimeMillis() / 1000);
//    }

}
