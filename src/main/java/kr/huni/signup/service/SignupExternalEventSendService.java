package kr.huni.signup.service;

import kr.huni.signup.event.SignupExternalEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Service for sending signup external events to Kafka.
 * This is part of the transaction outbox pattern.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SignupExternalEventSendService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Sends the event to Kafka.
     * This method is called after the transaction commits.
     */
    public void send(SignupExternalEvent event) {
        try {
            // Get the payload from the event
            String payload = event.toEventRecordCommand();

            // Publish to Kafka
            kafkaTemplate.send(event.getTopic(), payload);

            log.info("Event sent to Kafka topic {}: {}", event.getTopic(), event.getEventType());
        } catch (Exception e) {
            log.error("Failed to send event to Kafka: {}", event.getEventType(), e);
            // We don't throw an exception here because this is called after the transaction commits
            // and we don't want to roll back the transaction if the Kafka send fails
        }
    }
}
