package kr.huni.signup.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.huni.signup.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Base class for all signup-related external events.
 * This class is used for the transaction outbox pattern.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class SignupExternalEvent {
    private SignupEvent signupEvent;
    private String topic;
    private String eventType;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static SignupExternalEvent welcomeEmail(User user) {
        return new SignupExternalEvent(
                createSignupEvent(user),
                "welcome-email",
                "WELCOME_EMAIL"
        );
    }

    public static SignupExternalEvent welcomeCoupon(User user) {
        return new SignupExternalEvent(
                createSignupEvent(user),
                "welcome-coupon",
                "WELCOME_COUPON"
        );
    }

    private static SignupEvent createSignupEvent(User user) {
        return SignupEvent.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    /**
     * Converts this event to a JSON string for storing in the outbox table.
     * This is used by the event recorder.
     */
    public String toEventRecordCommand() {
        try {
            return objectMapper.writeValueAsString(signupEvent);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize event to JSON", e);
            throw new RuntimeException("Failed to serialize event to JSON", e);
        }
    }
}
