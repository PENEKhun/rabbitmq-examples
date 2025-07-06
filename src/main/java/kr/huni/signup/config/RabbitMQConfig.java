package kr.huni.signup.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Exchange names
    public static final String SIGNUP_EXCHANGE = "signup.exchange";
    
    // Queue names
    public static final String WELCOME_EMAIL_QUEUE = "welcome.email.queue";
    public static final String WELCOME_COUPON_QUEUE = "welcome.coupon.queue";
    
    // Routing keys
    public static final String WELCOME_EMAIL_ROUTING_KEY = "signup.welcome.email";
    public static final String WELCOME_COUPON_ROUTING_KEY = "signup.welcome.coupon";

    @Bean
    public TopicExchange signupExchange() {
        return new TopicExchange(SIGNUP_EXCHANGE);
    }

    @Bean
    public Queue welcomeEmailQueue() {
        return QueueBuilder.durable(WELCOME_EMAIL_QUEUE).build();
    }

    @Bean
    public Queue welcomeCouponQueue() {
        return QueueBuilder.durable(WELCOME_COUPON_QUEUE).build();
    }

    @Bean
    public Binding welcomeEmailBinding(Queue welcomeEmailQueue, TopicExchange signupExchange) {
        return BindingBuilder.bind(welcomeEmailQueue).to(signupExchange).with(WELCOME_EMAIL_ROUTING_KEY);
    }

    @Bean
    public Binding welcomeCouponBinding(Queue welcomeCouponQueue, TopicExchange signupExchange) {
        return BindingBuilder.bind(welcomeCouponQueue).to(signupExchange).with(WELCOME_COUPON_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}
