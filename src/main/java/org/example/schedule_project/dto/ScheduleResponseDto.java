package org.example.schedule_project.dto;

import lombok.Getter;
import org.example.schedule_project.entity.Schedule;

@Getter
public class ScheduleResponseDto {

    private final Long id;

    private final String todo;

    private final String writeUser;


    public ScheduleResponseDto(Long id, String todo, String writeUser) {
        this.id = id;
        this.todo = todo;
        this.writeUser = writeUser;
    }

    public static ScheduleResponseDto toDto(Schedule schedule) {
        return new ScheduleResponseDto(schedule.getId(), schedule.getTodo(), schedule.getWriteUser());
    }
}
