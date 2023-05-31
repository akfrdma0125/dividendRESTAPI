package com.example.kuit_server.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
public class PostLoginReq {

    @Email(message = "email: 이메일 형식이어야 합니다")
    @NotBlank(message = "email: {NotBlank}")
    @Length(max = 50, message = "email: 최대 {max}자리까지 가능합니다")
    private String email;

    @NotBlank(message = "password: {NotBlank}")
    @Length(min = 8, max = 20,
            message = "password: 최소 {min}자리 ~ 최대 {max}자리까지 가능합니다")
    private String password;
}
