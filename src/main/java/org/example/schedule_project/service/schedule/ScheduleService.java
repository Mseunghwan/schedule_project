package org.example.schedule_project.service.schedule;

import jakarta.transaction.Transactional;
import org.example.schedule_project.dto.schedule.DeleteScheduleRequestDto;
import org.example.schedule_project.dto.schedule.ScheduleResponseDto;
import org.example.schedule_project.entity.schedule.Schedule;
import org.example.schedule_project.entity.user.User;
import org.example.schedule_project.repository.schedule.ScheduleRepository;
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

    public ScheduleResponseDto save(String todo, Long userId, String password) {

        // 받은 todo, writeUser로 스케쥴 객체 하나 만듦
        Schedule schedule = new Schedule(todo, userId, password);

        // scheduleRepository에 save
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDto(savedSchedule.getId(), savedSchedule.getTodo(), savedSchedule.getUserId());

    }

    public List<ScheduleResponseDto> findAll() {

        return scheduleRepository.findAll().stream().map(ScheduleResponseDto::toDto).toList();
    }

    public ScheduleResponseDto findById(Long id) {

        Schedule schedule = scheduleRepository.findByIdOrThrow(id);

        return new ScheduleResponseDto(schedule.getId(), schedule.getTodo(), schedule.getUserId());
    }

    @Transactional
    public void updateSchedule(Long id, String todo, Long userId, String password) {

        // 기존 일정 있는지 확인해서 부르고
        Schedule findSchedule = scheduleRepository.findByIdOrThrow(id);

        // 받은 password가 기존 password와 동일하지 않으면 exception 던져주고 아니면
        if (!findSchedule.getPassword().equals(password)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password does not match");
        }

        // 할일, 작성자 명 변경
        findSchedule.update(todo, userId);
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

    public List<ScheduleResponseDto> findByUserId(Long userId) {
        List<Schedule> schedules = scheduleRepository.findByUserId(userId);

        return schedules.stream().map(ScheduleResponseDto::toDto).toList();
    }

}
