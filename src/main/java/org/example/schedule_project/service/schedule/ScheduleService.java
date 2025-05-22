package org.example.schedule_project.service.schedule;

import jakarta.transaction.Transactional;
import org.example.schedule_project.dto.schedule.DeleteScheduleRequestDto;
import org.example.schedule_project.dto.schedule.ScheduleResponseDto;
import org.example.schedule_project.entity.schedule.Schedule;
import org.example.schedule_project.entity.user.User;
import org.example.schedule_project.repository.schedule.ScheduleRepository;
import org.example.schedule_project.repository.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, UserRepository userRepository) {
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
    }

    public ScheduleResponseDto save(String todo, Long userId, String password) {

        // User 객체 찾음
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // 받은 todo, writeUser로 스케쥴 객체 하나 만듦
        Schedule schedule = new Schedule(todo, user, password);

        // scheduleRepository에 save
        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDto(savedSchedule.getId(), savedSchedule.getTodo(), savedSchedule.getUser().getId());

    }

    public List<ScheduleResponseDto> findAll() {

        return scheduleRepository.findAll().stream().map(ScheduleResponseDto::toDto).toList();
    }

    public Page<ScheduleResponseDto> findAllByPage(Pageable pageable) {
        Page<Schedule> schedules = scheduleRepository.findAll(pageable);
        return schedules.map(ScheduleResponseDto::toDto);
    }

    public ScheduleResponseDto findById(Long id) {

        Schedule schedule = scheduleRepository.findByIdOrThrow(id);

        return new ScheduleResponseDto(schedule.getId(), schedule.getTodo(), schedule.getUser().getId());
    }

    @Transactional
    public void updateSchedule(Long id, String todo, Long userId, String password) {

        // 기존 일정 있는지 확인해서 부르고
        Schedule findSchedule = scheduleRepository.findByIdOrThrow(id);

        User user = userRepository.findByIdOrThrow(userId);

        // 받은 password가 기존 password와 동일하지 않으면 exception 던져주고 아니면
        if (!findSchedule.getPassword().equals(password)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password does not match");
        }

        // 할일, 작성자 명 변경
        findSchedule.update(todo, user);
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
