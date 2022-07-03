package com.nurs.core.service;

import com.nurs.core.config.MQConfig;
import com.nurs.core.dao.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final RabbitTemplate template;

    private final OrderRepository orderRepository;

    public void sendOrderEmail(String email) {
        template.convertAndSend(MQConfig.EXCHANGE,
                MQConfig.ROUTING_KEY_5, email);
    }

    public void sendPaymentEmail(Long orderId) {
        String email = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new).getEmail();
        template.convertAndSend(MQConfig.EXCHANGE,
                MQConfig.ROUTING_KEY_6, email);
    }
}
