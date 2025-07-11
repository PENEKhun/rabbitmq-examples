package kr.huni.signup.service;

import kr.huni.signup.domain.MessageQueueEvent;
import kr.huni.signup.event.SignupExternalEvent;
import kr.huni.signup.repository.MessageQueueEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static kr.huni.signup.enums.MessageQueueStatus.INIT;

/**
 * Service for recording signup external events in the outbox table.
 * This is part of the transaction outbox pattern.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SignupExternalEventRecorder {

    private final MessageQueueEventRepository messageQueueEventRepository;

    /**
     * Saves the event to the outbox table.
     * Uses MANDATORY propagation to ensure it's called within an existing transaction.
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public void save(SignupExternalEvent event) {
        try {
            // Get the payload from the event
            String payload = event.toEventRecordCommand();

            // Create and save the message queue event
            MessageQueueEvent messageQueueEvent = MessageQueueEvent.builder()
                    .eventType(event.getEventType())
                    .payload(payload)
                    .status(INIT)
                    .createdAt(LocalDateTime.now())
                    .retryCount(0)
                    .maxRetries(3)
                    .build();

            messageQueueEventRepository.save(messageQueueEvent);

            log.info("Event recorded in outbox table: {}", event.getEventType());
        } catch (Exception e) {
            log.error("Failed to record event in outbox table: {}", event.getEventType(), e);
            throw new RuntimeException("Failed to record event in outbox table", e);
        }
    }
}
