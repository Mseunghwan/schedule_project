package org.example.schedule_project.dto.schedule;

import lombok.Getter;
import org.example.schedule_project.entity.user.User;

@Getter
public class UpdateScheduleRequestDto {

    private final String todo;

    private final Long userId;

    private final String password;


    public UpdateScheduleRequestDto(String todo, Long userId, String password) {
        this.todo = todo;
        this.userId = userId;
        this.password = password;
    }
}
