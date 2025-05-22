package org.example.schedule_project.repository.schedule;

import org.example.schedule_project.entity.schedule.Schedule;
import org.example.schedule_project.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    default Schedule findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException("해당 일정이 존재하지 않습니다."));
    }

    List<Schedule> findByUserId(Long userId);
}
