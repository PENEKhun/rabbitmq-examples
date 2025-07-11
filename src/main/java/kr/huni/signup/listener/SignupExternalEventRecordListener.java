package kr.huni.signup.listener;

import kr.huni.signup.annotation.EventHandler;
import kr.huni.signup.event.SignupExternalEvent;
import kr.huni.signup.service.SignupExternalEventRecorder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Listener for recording signup external events in the outbox table before the transaction commits.
 * This is part of the transaction outbox pattern.
 */
@EventHandler
@RequiredArgsConstructor
@Slf4j
public class SignupExternalEventRecordListener {

    private final SignupExternalEventRecorder eventRecorder;

    /**
     * Records the event in the outbox table before the transaction commits.
     */
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void recordMessageHandler(SignupExternalEvent event) {
        log.info("Recording event in outbox table: {}", event.getEventType());
        eventRecorder.save(event);
    }
}
