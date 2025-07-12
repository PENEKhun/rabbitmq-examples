package kr.huni.signup.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.huni.signup.domain.MessageQueueEvent;
import kr.huni.signup.domain.User;
import kr.huni.signup.enums.MessageQueueStatus;
import kr.huni.signup.event.SignupEvent;
import kr.huni.signup.repository.MessageQueueEventRepository;
import kr.huni.signup.repository.UserRepository;
import kr.huni.signup.service.CouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Consumer for welcome coupon events.
 * This class listens to the welcome-coupon topic and issues welcome coupons to users.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class WelcomeCouponConsumer {

    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final CouponService couponService;
    private final MessageQueueEventRepository messageQueueEventRepository;

    /**
     * Listens to the welcome-coupon topic and issues welcome coupons to users.
     */
    @KafkaListener(topics = "welcome-coupon", groupId = "signup-group")
    @Transactional
    public void consume(String message) {
        log.info("Received welcome coupon event: {}", message);
        
        try {
            // Parse the message
            SignupEvent event = objectMapper.readValue(message, SignupEvent.class);
            
            // Find the user
            Optional<User> userOptional = userRepository.findById(event.getUserId());
            
            if (userOptional.isEmpty()) {
                log.error("User not found for ID: {}", event.getUserId());
                updateMessageQueueEventStatus(message, MessageQueueStatus.CONSUMED_FAIL);
                return;
            }
            
            User user = userOptional.get();
            
            // Issue the welcome coupon
            couponService.issueWelcomeCoupon(user);
            
            // Update the message queue event status
            updateMessageQueueEventStatus(message, MessageQueueStatus.CONSUMED_SUCCESS);
            
            log.info("Welcome coupon issued to user: {}", user.getUsername());
        } catch (Exception e) {
            log.error("Failed to process welcome coupon event", e);
            updateMessageQueueEventStatus(message, MessageQueueStatus.CONSUMED_FAIL);
        }
    }
    
    /**
     * Updates the status of the message queue event.
     */
    private void updateMessageQueueEventStatus(String payload, MessageQueueStatus status) {
        try {
            MessageQueueEvent event = messageQueueEventRepository.findByPayloadAndEventType(payload, "WELCOME_COUPON");
            
            if (event != null) {
                event.setStatus(status);
                event.setProcessedAt(LocalDateTime.now());
                messageQueueEventRepository.save(event);
                log.info("Updated message queue event status to {}: {}", status, event.getId());
            } else {
                log.warn("Message queue event not found for payload: {}", payload);
            }
        } catch (Exception e) {
            log.error("Failed to update message queue event status", e);
        }
    }
}
