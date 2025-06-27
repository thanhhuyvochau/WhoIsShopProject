package org.project.usermanagement.config;

import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Qualifier;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
@Slf4j
public class RabbitConfig {
    public static final String EXCHANGE_NAME = "email.exchange";
    public static final String EMAIL_QUEUE = "email.queue";

    @Bean
    public Queue emailQueue() {
        return new Queue(RabbitConfig.EMAIL_QUEUE, true);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(RabbitConfig.EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue emailQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(emailQueue).to(directExchange).with("email.routing");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory factory) {
        RabbitTemplate template = new RabbitTemplate(factory);
        template.setMessageConverter(jsonMessageConverter());
        template.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.info("Message successfully delivered to exchange");
            } else {
                log.error("Message delivery to exchange failed. Cause: {}", cause);
            }
        });
        return template;
    }
}
