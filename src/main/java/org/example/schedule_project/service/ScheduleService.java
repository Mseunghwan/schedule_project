package org.example.schedule_project.service;

import jakarta.transaction.Transactional;
import org.example.schedule_project.dto.DeleteScheduleRequestDto;
import org.example.schedule_project.dto.ScheduleResponseDto;
import org.example.schedule_project.entity.Schedule;
import org.example.schedule_project.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public ScheduleResponseDto findById(Long id) {

        Schedule schedule = scheduleRepository.findByIdOrThrow(id);

        return new ScheduleResponseDto(schedule.getId(), schedule.getTodo(), schedule.getWriteUser());
    }

    @Transactional
    public void updateSchedule(Long id, String todo, String writerUser, String password) {

        // 기존 일정 있는지 확인해서 부르고
        Schedule findSchedule = scheduleRepository.findByIdOrThrow(id);

        // 받은 password가 기존 password와 동일하지 않으면 exception 던져주고 아니면
        if (!findSchedule.getPassword().equals(password)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password does not match");
        }

        // 할일, 작성자 명 변경
        findSchedule.update(todo, writerUser);
    }

    public void delete(Long id, DeleteScheduleRequestDto requestDto) {

        Schedule findSchedule = scheduleRepository.findByIdOrThrow(id);

        // 받은 password가 기존 password와 동일하지 않으면 exception 던져주고 아니면
        if (!findSchedule.getPassword().equals(requestDto.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password does not match");
        }

        // 삭제
        scheduleRepository.delete(findSchedule);

    }
}
