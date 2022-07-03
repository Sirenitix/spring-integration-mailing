package com.nurs.core.service;


import com.nurs.core.config.MQConfig;
import com.nurs.core.dao.OrderRepository;
import com.nurs.core.dao.PaymentRepository;
import com.nurs.core.dto.PaymentRequest;
import com.nurs.core.dto.UpdateOrderRequest;
import com.nurs.core.entity.Order;
import com.nurs.core.entity.Payment;
import com.nurs.core.exceptions.OrderAlreadyPaid;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    private final PaymentRepository paymentRepository;


    public void createOrder(Order order) {
        orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public void updateOrder(UpdateOrderRequest updateOrderRequest) {
        orderRepository.updateById( updateOrderRequest.getId(),
                                    updateOrderRequest.getAmount(),
                                    updateOrderRequest.getDate(),
                                    updateOrderRequest.getPaid(),
                                    updateOrderRequest.getEmail());
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        if(orderRepository.findById(id).isPresent()){
            return orderRepository.findById(id).get();
        }
        return null;
    }



    public void createPayment(PaymentRequest paymentRequest) {
        Order order = orderRepository.findById(paymentRequest.getOrderId()).orElseThrow(EntityNotFoundException::new);

        if (order.isPaid()) {
            throw new OrderAlreadyPaid();
        }

        orderRepository.save(order.markPaid());
        paymentRepository.save(new Payment(order, paymentRequest.getCreditCardNumber()));
    }
}