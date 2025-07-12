package kr.huni.signup.repository;

import kr.huni.signup.domain.MessageQueueEvent;
import kr.huni.signup.enums.MessageQueueStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageQueueEventRepository extends JpaRepository<MessageQueueEvent, Long> {

    /**
     * Finds a message queue event by payload and event type.
     */
    MessageQueueEvent findByPayloadAndEventType(String payload, String eventType);

    /**
     * Finds message queue events by status.
     */
    List<MessageQueueEvent> findByStatus(MessageQueueStatus status);
}
