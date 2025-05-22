package org.example.schedule_project.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateUserRequestDto {

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.") // 이메일 형식 검사
    private final String email;

    @NotBlank(message = "사용자 이름은 필수입니다.")
    private final String username;

    public CreateUserRequestDto(String email, String username) {
        this.email = email;
        this.username = username;
    }
}
