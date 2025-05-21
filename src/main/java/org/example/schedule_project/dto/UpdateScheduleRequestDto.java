package org.example.schedule_project.dto;

import lombok.Getter;

@Getter
public class UpdateScheduleRequestDto {

    private final String todo;

    private final String writeUser;

    private final String password;


    public UpdateScheduleRequestDto(String todo, String writeUser, String password) {
        this.todo = todo;
        this.writeUser = writeUser;
        this.password = password;
    }
}
