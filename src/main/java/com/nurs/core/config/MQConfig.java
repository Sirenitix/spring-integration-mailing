package com.nurs.core.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class MQConfig {

    public static final String CREATE_ORDER_QUEUE = "create_order";

    public static final String UPDATE_ORDER_QUEUE = "update_order";

    public static final String DELETE_ORDER_QUEUE = "delete_order";

    public static final String CREATE_PAYMENT_QUEUE = "create_payment";

    public static final String EMAIL_ORDER_QUEUE = "order_email";

    public static final String EMAIL_PAYMENT_QUEUE = "payment_email";

    public static final String EXCHANGE = "order_exchange";

    public static final String ROUTING_KEY_1 = "routing_key_1";

    public static final String ROUTING_KEY_2 = "routing_key_2";

    public static final String ROUTING_KEY_3 = "routing_key_3";

    public static final String ROUTING_KEY_4 = "routing_key_4";

    public static final String ROUTING_KEY_5 = "routing_key_5";

    public static final String ROUTING_KEY_6 = "routing_key_6";

    @Bean(name = "firstQueue")
    public Queue firstQueue() {
        return  new Queue(CREATE_ORDER_QUEUE);
    }

    @Bean(name = "secondQueue")
    public Queue secondQueue() {
        return new Queue(UPDATE_ORDER_QUEUE);
    }

    @Bean(name = "thirdQueue")
    public Queue thirdQueue() {
        return new Queue(DELETE_ORDER_QUEUE);
    }

    @Bean(name = "fourthQueue")
    public Queue fourthQueue() {return new Queue(CREATE_PAYMENT_QUEUE);}

    @Bean(name = "fifthQueue")
    public Queue fifthQueue() {return new Queue(EMAIL_ORDER_QUEUE);}

    @Bean(name = "sixthQueue")
    public Queue sixthQueue() {return new Queue(EMAIL_PAYMENT_QUEUE);}

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding binding1(@Qualifier("firstQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(ROUTING_KEY_1);
    }

    @Bean
    public Binding binding2(@Qualifier("secondQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(ROUTING_KEY_2);
    }

    @Bean
    public Binding binding3(@Qualifier("thirdQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(ROUTING_KEY_3);
    }


    @Bean
    public Binding binding4(@Qualifier("fourthQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(ROUTING_KEY_4);
    }

    @Bean
    public Binding binding5(@Qualifier("fifthQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(ROUTING_KEY_5);
    }

    @Bean
    public Binding binding6(@Qualifier("sixthQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(ROUTING_KEY_6);
    }


    @Bean
    public MessageConverter messageConverter() {
        return  new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return  template;
    }

}
