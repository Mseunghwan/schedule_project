package org.example.schedule_project.service;

import org.example.schedule_project.dto.ScheduleResponseDto;
import org.example.schedule_project.entity.Schedule;
import org.example.schedule_project.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public ScheduleResponseDto save(String todo, String writeUser, String password) {

        // 받은 todo, writeUser로 스케쥴 객체 하나 만듦
        Schedule schedule = new Schedule(todo, writeUser, password);

        // scheduleRepository에 save
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDto(savedSchedule.getId(), savedSchedule.getTodo(), savedSchedule.getWriteUser());

    }

    public List<ScheduleResponseDto> findAll() {

        return scheduleRepository.findAll().stream().map(ScheduleResponseDto::toDto).toList();
    }
}
