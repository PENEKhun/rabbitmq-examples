package kr.huni.signup.service;

import kr.huni.signup.config.RabbitMQConfig;
import kr.huni.signup.domain.MessageQueueEvent;
import kr.huni.signup.domain.User;
import kr.huni.signup.event.SignupEvent;
import kr.huni.signup.repository.MessageQueueEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventPublisherService {

    private final RabbitTemplate rabbitTemplate;
    private final MessageQueueEventRepository messageQueueEventRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public void publishWelcomeEmailEvent(User user) {
        try {
            SignupEvent event = createSignupEvent(user);
            String payload = objectMapper.writeValueAsString(event);

            // Save event to database
            MessageQueueEvent messageQueueEvent = MessageQueueEvent.builder()
                    .eventType("WELCOME_EMAIL")
                    .payload(payload)
                    .status("PUBLISHED")
                    .createdAt(LocalDateTime.now())
                    .retryCount(0)
                    .maxRetries(3)
                    .build();

            messageQueueEventRepository.save(messageQueueEvent);

            // Publish event to RabbitMQ
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.SIGNUP_EXCHANGE,
                    RabbitMQConfig.WELCOME_EMAIL_ROUTING_KEY,
                    event
            );

            log.info("Welcome email event published for user: {}", user.getUsername());
        } catch (Exception e) {
            log.error("Failed to publish welcome email event for user: {}", user.getUsername(), e);
            throw new RuntimeException("Failed to publish welcome email event", e);
        }
    }

    @Transactional
    public void publishWelcomeCouponEvent(User user) {
        try {
            SignupEvent event = createSignupEvent(user);
            String payload = objectMapper.writeValueAsString(event);

            // Save event to database
            MessageQueueEvent messageQueueEvent = MessageQueueEvent.builder()
                    .eventType("WELCOME_COUPON")
                    .payload(payload)
                    .status("PUBLISHED")
                    .createdAt(LocalDateTime.now())
                    .retryCount(0)
                    .maxRetries(3)
                    .build();

            messageQueueEventRepository.save(messageQueueEvent);

            // Publish event to RabbitMQ
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.SIGNUP_EXCHANGE,
                    RabbitMQConfig.WELCOME_COUPON_ROUTING_KEY,
                    event
            );

            log.info("Welcome coupon event published for user: {}", user.getUsername());
        } catch (Exception e) {
            log.error("Failed to publish welcome coupon event for user: {}", user.getUsername(), e);
            throw new RuntimeException("Failed to publish welcome coupon event", e);
        }
    }

    private SignupEvent createSignupEvent(User user) {
        return SignupEvent.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
