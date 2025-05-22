package org.example.schedule_project.dto.user;

import lombok.Getter;
import org.example.schedule_project.entity.user.User;

@Getter
public class UserResponseDto {

    private final Long id;

    private final String email;

    private final String username;


    public UserResponseDto(Long id, String email, String username) {
        this.id = id;
        this.email = email;
        this.username = username;
    }

    public static UserResponseDto toDto(User user) {
        return new UserResponseDto(user.getId(), user.getEmail(), user.getUsername());
    }
}
