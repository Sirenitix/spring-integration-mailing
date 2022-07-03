package com.nurs.core.controller;

import com.nurs.core.config.MQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Component
@RequiredArgsConstructor
public class EmailListener {

    private final JavaMailSender emailSender;

    private final RabbitTemplate template;

    @Scheduled(fixedRate = 3000, initialDelay = 3000)
    public void sendOrderMail() {
        log.info("Scheduled task order");
        while (true) {
            String emailOrder = (String) template.receiveAndConvert(MQConfig.EMAIL_ORDER_QUEUE);
            log.info(emailOrder + " - email order");
            if(emailOrder == null) break;
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@nurs.com");
            message.setTo(emailOrder);
            message.setSubject("From shop.org");
            message.setText("Your order is created");
            emailSender.send(message);

        }
        log.info("exit order");
    }

    @Scheduled(fixedRate = 5000, initialDelay = 5000)
        public void sendPaymentMail() {
        log.info("Scheduled task payment");
        while (true){
            String emailPayment = (String) template.receiveAndConvert(MQConfig.EMAIL_PAYMENT_QUEUE);
            log.info(emailPayment + " - email payment");
            if(emailPayment == null) break;
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@nurs.com");
            message.setTo(emailPayment);
            message.setSubject("From shop.org");
            message.setText("Your payment is created");
            emailSender.send(message);
            emailPayment = (String) template.receiveAndConvert(MQConfig.EMAIL_PAYMENT_QUEUE);
        }
        log.info("exit payment");
    }

}
