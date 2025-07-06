package kr.huni.signup.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupEvent implements Serializable {
    private Long userId;
    private String username;
    private String email;
    private String fullName;
    private String phoneNumber;
}
