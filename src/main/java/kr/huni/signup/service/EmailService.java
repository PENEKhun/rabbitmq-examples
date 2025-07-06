package kr.huni.signup.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import kr.huni.signup.domain.EmailNotification;
import kr.huni.signup.domain.User;
import kr.huni.signup.repository.EmailNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final EmailNotificationRepository emailNotificationRepository;

    public void sendWelcomeEmail(User user) {
        String subject = "%s님 환영합니다!".formatted(user.getFullName());
        String content = """
                안녕하세요, %s님!
                회원가입을 축하드립니다. 저희 서비스에 가입해 주셔서 감사합니다.
                회원가입 기념으로 10%% 할인 쿠폰을 발행해 드렸으니 확인해보세요 ㅎㅎ.
                앞으로도 많은 관심과 사랑 부탁드립니다.
                감사합니다.
                """.formatted(user.getFullName());

        try {
            sendEmail(user.getEmail(), subject, content);
            saveEmailNotification(user, "WELCOME", subject, content);
        } catch (MessagingException e) {
            // Log the error and save the failed notification
            saveEmailNotification(user, "WELCOME", subject, content, "FAILED");
            throw new RuntimeException("Failed to send welcome email", e);
        }
    }

    private void sendEmail(String to, String subject, String content) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, false); // false means it's not HTML

        mailSender.send(message);
    }

    private void saveEmailNotification(User user, String emailType, String subject, String content) {
        saveEmailNotification(user, emailType, subject, content, "SENT");
    }

    private void saveEmailNotification(User user, String emailType, String subject, String content, String status) {
        EmailNotification notification = EmailNotification.builder()
                .user(user)
                .emailType(emailType)
                .subject(subject)
                .content(content)
                .sentAt(LocalDateTime.now())
                .status(status)
                .build();

        emailNotificationRepository.save(notification);
    }
}
