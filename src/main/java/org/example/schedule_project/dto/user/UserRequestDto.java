package org.example.schedule_project.dto.user;

import lombok.Getter;

@Getter
public class UserRequestDto {

    private final String email;

    private final String username;

    public UserRequestDto(String email, String username) {
        this.email = email;
        this.username = username;
    }
}
