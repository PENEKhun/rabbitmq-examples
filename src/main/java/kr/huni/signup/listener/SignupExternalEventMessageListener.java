package kr.huni.signup.listener;

import kr.huni.signup.annotation.EventHandler;
import kr.huni.signup.event.SignupExternalEvent;
import kr.huni.signup.service.SignupExternalEventSendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Listener for sending signup external events to Kafka after the transaction commits.
 * This is part of the transaction outbox pattern.
 */
@EventHandler
@RequiredArgsConstructor
@Slf4j
public class SignupExternalEventMessageListener {

    private final SignupExternalEventSendService sendService;

    /**
     * Sends the event to Kafka after the transaction commits.
     * This is done asynchronously to avoid blocking the main thread.
     */
    @Async("EVENT_ASYNC_TASK_EXECUTOR")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendMessageHandler(SignupExternalEvent event) {
        log.info("Sending event to Kafka: {}", event.getEventType());
        sendService.send(event);
    }
}
