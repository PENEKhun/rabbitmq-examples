package kr.huni.signup.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.huni.signup.domain.MessageQueueEvent;
import kr.huni.signup.domain.User;
import kr.huni.signup.event.SignupEvent;
import kr.huni.signup.repository.MessageQueueEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventPublisherService {

    private final MessageQueueEventRepository messageQueueEventRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    private static final String WELCOME_EMAIL_TOPIC = "welcome-email";
    private static final String WELCOME_COUPON_TOPIC = "welcome-coupon";

    @Transactional
    public void publishWelcomeEmailEvent(User user) {
        publishEvent(user, WELCOME_EMAIL_TOPIC, "WELCOME_EMAIL");
    }

    @Transactional
    public void publishWelcomeCouponEvent(User user) {
        publishEvent(user, WELCOME_COUPON_TOPIC, "WELCOME_COUPON");
    }

    private void publishEvent(User user, String topic, String eventType) {
        try {
            SignupEvent event = createSignupEvent(user);
            String payload = objectMapper.writeValueAsString(event);

            // Save event to database
            MessageQueueEvent messageQueueEvent = MessageQueueEvent.builder()
                    .eventType(eventType)
                    .payload(payload)
                    .status("PUBLISHED")
                    .createdAt(LocalDateTime.now())
                    .retryCount(0)
                    .maxRetries(3)
                    .build();
            messageQueueEventRepository.save(messageQueueEvent);

            // Publish to Kafka
            kafkaTemplate.send(topic, payload);

            log.info("{} event published to Kafka for user: {}", eventType, user.getUsername());
        } catch (Exception e) {
            log.error("Failed to publish {} event for user: {}", eventType, user.getUsername(), e);
            throw new RuntimeException("Failed to publish Kafka event", e);
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
