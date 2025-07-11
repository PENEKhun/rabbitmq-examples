package kr.huni.signup.service;

import kr.huni.signup.domain.User;
import kr.huni.signup.event.SignupExternalEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for publishing signup-related events.
 * This service follows the transaction outbox pattern.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EventPublisherService {

    private final ApplicationEventPublisher eventPublisher;

    /**
     * Publishes a welcome email event for a user.
     * The event will be recorded in the outbox table before the transaction commits,
     * and sent to Kafka after the transaction commits.
     */
    @Transactional
    public void publishWelcomeEmailEvent(User user) {
        log.info("Publishing welcome email event for user: {}", user.getUsername());
        eventPublisher.publishEvent(SignupExternalEvent.welcomeEmail(user));
    }

    /**
     * Publishes a welcome coupon event for a user.
     * The event will be recorded in the outbox table before the transaction commits,
     * and sent to Kafka after the transaction commits.
     */
    @Transactional
    public void publishWelcomeCouponEvent(User user) {
        log.info("Publishing welcome coupon event for user: {}", user.getUsername());
        eventPublisher.publishEvent(SignupExternalEvent.welcomeCoupon(user));
    }
}
