package com.nurs.core.controller;

import com.nurs.core.config.MQConfig;
import com.nurs.core.entity.CustomMessage;
import com.nurs.core.entity.Order;
import com.nurs.core.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderListener {

    private final OrderService orderService;

    @RabbitListener(queues = MQConfig.QUEUE)
    public void listener(Order order) {
        log.info(order + " - order");
        orderService.createOrder(order);
    }

//    @Scheduled(fixedDelay = 1000, initialDelay = 1000)
//    public void scheduleFixedDelayTask() {
//        log.info(
//                "Fixed delay task - " + System.currentTimeMillis() / 1000);
//    }

}
