package kr.huni.signup.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SignupResponse {
    private final String username;
    private final String fullName;
}
