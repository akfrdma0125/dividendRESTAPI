package com.example.kuit_server.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class PatchUserPasswordReq {
    private String prevPassword;
    @NotBlank(message = "newPassword: {NotBlank}")
    @Length(min = 8, max = 20,
            message = "newPassword: 최소 {min}자리 ~ 최대 {max}자리까지 가능합니다")
    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}",
            message = "newPassword: 대문자, 소문자, 특수문자가 적어도 하나씩은 있어야 합니다")
    private String newPassword;
    private String checkPassword;
    public void resetPassword(String encodedPassword) {
        this.newPassword = encodedPassword;
    }
}
