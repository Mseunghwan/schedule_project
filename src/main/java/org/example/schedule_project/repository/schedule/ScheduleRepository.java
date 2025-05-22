package org.example.schedule_project.repository.schedule;

import org.example.schedule_project.entity.schedule.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    default Schedule findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }

    List<Schedule> findByUserId(Long userId);
}
