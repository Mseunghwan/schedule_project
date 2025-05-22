package org.example.schedule_project.dto.schedule;

import lombok.Getter;
import org.example.schedule_project.entity.schedule.Schedule;
import org.example.schedule_project.entity.user.User;

@Getter
public class ScheduleResponseDto {

    private final Long id;

    private final String todo;

    private final Long userId;


    public ScheduleResponseDto(Long id, String todo, Long userId) {
        this.id = id;
        this.todo = todo;
        this.userId = userId;
    }

    public static ScheduleResponseDto toDto(Schedule schedule) {
        return new ScheduleResponseDto(schedule.getId(), schedule.getTodo(), schedule.getUser().getId());
    }
}
