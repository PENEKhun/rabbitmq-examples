package kr.huni.signup.listener;

import kr.huni.signup.config.RabbitMQConfig;
import kr.huni.signup.domain.User;
import kr.huni.signup.event.SignupEvent;
import kr.huni.signup.repository.UserRepository;
import kr.huni.signup.service.CouponService;
import kr.huni.signup.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class SignupEventListener {

    private final EmailService emailService;
    private final CouponService couponService;
    private final UserRepository userRepository;

    @RabbitListener(queues = RabbitMQConfig.WELCOME_EMAIL_QUEUE)
    @Transactional
    public void handleWelcomeEmailEvent(SignupEvent event) {
        log.info("Received welcome email event for user: {}", event.getUsername());
        
        User user = userRepository.findById(event.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + event.getUserId()));
        
        emailService.sendWelcomeEmail(user);
        
        log.info("Welcome email sent for user: {}", user.getUsername());
    }

    @RabbitListener(queues = RabbitMQConfig.WELCOME_COUPON_QUEUE)
    @Transactional
    public void handleWelcomeCouponEvent(SignupEvent event) {
        log.info("Received welcome coupon event for user: {}", event.getUsername());
        
        User user = userRepository.findById(event.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + event.getUserId()));
        
        couponService.issueWelcomeCoupon(user);
        
        log.info("Welcome coupon issued for user: {}", user.getUsername());
    }
}
