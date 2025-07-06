package kr.huni.signup.controller;

import kr.huni.signup.request.SignupRequest;
import kr.huni.signup.response.SignupResponse;
import kr.huni.signup.service.SignupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignupController {
    private final SignupService signupService;

    @PostMapping("signup")
    public SignupResponse signup(@RequestBody SignupRequest signupRequest) {
        // Validate the request
        if (signupRequest == null) {
            throw new IllegalArgumentException("Signup request cannot be null.");
        }

        return signupService.signup(signupRequest);
    }
}
