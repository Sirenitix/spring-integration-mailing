package com.nurs.core.service;


import com.nurs.core.dao.OrderRepository;
import com.nurs.core.dao.PaymentRepository;
import com.nurs.core.dto.UpdateOrderRequest;
import com.nurs.core.entity.Order;
import com.nurs.core.entity.Payment;
import com.nurs.core.exceptions.OrderAlreadyPaid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
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
                                    updateOrderRequest.getPaid());
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }


//    public Order getOrder(Long orderId) {
//        return orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
//    }
//
//    public Payment pay(Long orderId, String creditCardNumber) {
//        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
//
//        if (order.isPaid()) {
//            throw new OrderAlreadyPaid();
//        }
//
//        orderRepository.save(order.markPaid());
//        return paymentRepository.save(new Payment(order, creditCardNumber));
//    }


}