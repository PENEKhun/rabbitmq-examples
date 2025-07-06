package kr.huni.signup.repository;

import kr.huni.signup.domain.MessageQueueEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageQueueEventRepository extends JpaRepository<MessageQueueEvent, Long> {
}
