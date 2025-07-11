package kr.huni.signup.service;

import jakarta.transaction.Transactional;
import kr.huni.signup.domain.User;
import kr.huni.signup.repository.UserRepository;
import kr.huni.signup.request.SignupRequest;
import kr.huni.signup.response.SignupResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service for handling user signup.
 * This service follows the transaction outbox pattern for publishing events.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SignupService {
    private final UserRepository userRepository;
    private final EventPublisherService eventPublisherService;

    /**
     * Signs up a new user and publishes events for welcome email and coupon.
     * The events will be recorded in the outbox table before the transaction commits,
     * and sent to Kafka after the transaction commits.
     */
    @Transactional
    public SignupResponse signup(SignupRequest signupRequest) {
        if (signupRequest.username().length() > 20 || signupRequest.username().length() < 3) {
            throw new IllegalArgumentException("Username must be between 3 and 20 characters.");
        }

        if (signupRequest.password().length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long.");
        }

        if (userRepository.existsByUsername(signupRequest.username())) {
            throw new IllegalArgumentException("Username already exists.");
        }

        if (userRepository.existsByEmail(signupRequest.email())) {
            throw new IllegalArgumentException("Email already exists.");
        }

        if (userRepository.existsByPhoneNumber(signupRequest.phoneNumber())) {
            throw new IllegalArgumentException("Phone number already exists.");
        }

        // 1. 도메인 로직 - 사용자 생성 및 저장
        User createdUser = User.create(signupRequest);
        userRepository.save(createdUser);

        log.info("User created and saved: {}", createdUser.getUsername());

        // 2. 이벤트 발행 - 환영 이메일 및 쿠폰 발행 이벤트
        eventPublisherService.publishWelcomeEmailEvent(createdUser);
        eventPublisherService.publishWelcomeCouponEvent(createdUser);

        return SignupResponse.builder()
                .username(createdUser.getUsername())
                .fullName(createdUser.getFullName())
                .build();
    }
}
