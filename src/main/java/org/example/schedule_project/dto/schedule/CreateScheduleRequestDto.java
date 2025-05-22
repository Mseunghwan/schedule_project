package org.example.schedule_project.dto.schedule;

import lombok.Getter;
import org.example.schedule_project.entity.user.User;

@Getter
public class CreateScheduleRequestDto {

    private final String todo;

    private final Long userId;

    private final String password;


    public CreateScheduleRequestDto(String todo, Long userId, String password) {
        this.todo = todo;
        this.userId = userId;
        this.password = password;
    }
}
