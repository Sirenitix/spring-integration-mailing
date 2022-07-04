package com.nurs.core.service;

import com.nurs.core.config.MQConfig;
import com.nurs.core.entity.Mail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {


    private final JavaMailSender emailSender;

    private final RabbitTemplate template;


    public void sendOrderEmail() {
        log.info("Scheduled task order");
        while (true) {
            String emailOrder = (String) template.receiveAndConvert(MQConfig.EMAIL_ORDER_QUEUE);
            if(emailOrder == null) break;
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@nurs.com");
            message.setTo(emailOrder);
            message.setSubject("From shop.org");
            message.setText("Your order is created");
            emailSender.send(message);

        }
    }

    public void sendPaymentEmail() {
        log.info("Scheduled task payment");
        while (true){
            String emailPayment = (String) template.receiveAndConvert(MQConfig.EMAIL_PAYMENT_QUEUE);
            if(emailPayment == null) break;
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@nurs.com");
            message.setTo(emailPayment);
            message.setSubject("From shop.org");
            message.setText("Your payment is created");
            emailSender.send(message);
        }
    }

    public void sendSimpleMessage(Mail mail){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(mail.getSubject());
        message.setText(mail.getContent());
        message.setTo(mail.getTo());
        message.setFrom(mail.getFrom());

        emailSender.send(message);
    }

}
