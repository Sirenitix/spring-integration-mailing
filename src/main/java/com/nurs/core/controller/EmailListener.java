package com.nurs.core.controller;

import com.nurs.core.config.MQConfig;
import com.nurs.core.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

   private final EmailService emailService;

    @Scheduled(fixedRate = 3000, initialDelay = 3000)
    public void sendOrderMail() {
       emailService.sendOrderEmail();
    }

    @Scheduled(fixedRate = 5000, initialDelay = 5000)
        public void sendPaymentMail() {
        emailService.sendPaymentEmail();
    }

}
