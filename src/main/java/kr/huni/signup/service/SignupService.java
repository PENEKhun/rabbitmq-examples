package kr.huni.signup.service;

import jakarta.transaction.Transactional;
import kr.huni.signup.domain.User;
import kr.huni.signup.repository.UserRepository;
import kr.huni.signup.request.SignupRequest;
import kr.huni.signup.response.SignupResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignupService {
    private final UserRepository userRepository;

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

        User createdUser = User.create(signupRequest);
        userRepository.save(createdUser);

        return SignupResponse.builder()
                .username(createdUser.getUsername())
                .fullName(createdUser.getFullName())
                .build();
    }
}
