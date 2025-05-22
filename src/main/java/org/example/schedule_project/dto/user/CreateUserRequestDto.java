package org.example.schedule_project.dto.user;

import lombok.Getter;

@Getter
public class CreateUserRequestDto {

    private final String email;

    private final String username;

    public CreateUserRequestDto(String email, String username) {
        this.email = email;
        this.username = username;
    }
}
