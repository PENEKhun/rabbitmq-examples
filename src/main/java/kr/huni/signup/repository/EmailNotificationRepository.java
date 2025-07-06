package kr.huni.signup.repository;

import kr.huni.signup.domain.EmailNotification;
import kr.huni.signup.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmailNotificationRepository extends JpaRepository<EmailNotification, Long> {
    List<EmailNotification> findByUser(User user);
    List<EmailNotification> findByUserAndEmailType(User user, String emailType);
}
