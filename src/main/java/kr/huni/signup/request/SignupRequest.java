package kr.huni.signup.request;

import org.springframework.util.StringUtils;

public record SignupRequest(
    String fullName,
    String username,
    String password,
    String email,
    String phoneNumber
){
    public SignupRequest {
        if (!StringUtils.hasText(fullName)) {
            throw new IllegalArgumentException("Full name is required.");
        }

        if (!StringUtils.hasText(username)) {
            throw new IllegalArgumentException("Username is required.");
        }

        if (!StringUtils.hasText(password)) {
            throw new IllegalArgumentException("Password is required.");
        }

        if (!StringUtils.hasText(email)) {
            throw new IllegalArgumentException("Email is required.");
        }

        if (!StringUtils.hasText(phoneNumber)) {
            throw new IllegalArgumentException("Phone number is required.");
        }
    }
}
