package org.example.schedule_project.service.schedule.jdbc;

import org.example.schedule_project.dto.schedule.ScheduleResponseDto;
import org.example.schedule_project.dto.schedule.DeleteScheduleRequestDto;
import org.example.schedule_project.entity.schedule.Schedule;
import org.example.schedule_project.entity.user.User;
import org.example.schedule_project.repository.schedule.jdbc.ScheduleJdbcRepository;
import org.example.schedule_project.repository.user.jdbc.UserJdbcRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceJdbc {

    private final ScheduleJdbcRepository scheduleRepo;
    private final UserJdbcRepository userRepo;

    public ScheduleServiceJdbc(ScheduleJdbcRepository scheduleRepo,
                               UserJdbcRepository userRepo) {
        this.scheduleRepo = scheduleRepo;
        this.userRepo = userRepo;
    }

//     일정 생성 → User 존재 확인 → DAO(save) → 다시 조회
    @Transactional
    public ScheduleResponseDto save(String todo, Long userId, String password) {
        // User 유효성 체크
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // 순수 DAO에게 INSERT & 새 ID 받아옴
        Long newId = scheduleRepo.save(todo, userId, password);

        // DAO에서 방금 생성된 엔티티 조회
        Schedule schedule = scheduleRepo.findById(newId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "일정 생성 후 조회 실패"));

        // DTO로 변환하여 반환
        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getTodo(),
                schedule.getUser().getId()
        );
    }

// 전체 일정 조회 → 엔티티 List
    public List<ScheduleResponseDto> findAll() {
        return scheduleRepo.findAll().stream()
                .map(s -> new ScheduleResponseDto(s.getId(),
                        s.getTodo(),
                        s.getUser().getId()))
                .collect(Collectors.toList());
    }

//    ID로 단건 조회
    public ScheduleResponseDto findById(Long id) {
        Schedule s = scheduleRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다."));
        return new ScheduleResponseDto(s.getId(), s.getTodo(), s.getUser().getId());
    }

//사용자별 일정 조회

    public List<ScheduleResponseDto> findByUserId(Long userId) {
        return scheduleRepo.findByUserId(userId).stream()
                .map(s -> new ScheduleResponseDto(s.getId(),
                        s.getTodo(),
                        s.getUser().getId()))
                .collect(Collectors.toList());
    }

 // 일정 수정 → 단건 조회 → 비밀번호 검증
    @Transactional
    public void updateSchedule(Long id, String todo, Long userId, String password) {
        Schedule orig = scheduleRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다."));
        if (!orig.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호가 일치하지 않습니다.");
        }
        scheduleRepo.update(id, todo, userId);
    }

// 일정 삭제 → 단건 조회 → 비밀번호 검증

    @Transactional
    public void delete(Long id, DeleteScheduleRequestDto dto) {
        Schedule orig = scheduleRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다."));
        if (!orig.getPassword().equals(dto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호가 일치하지 않습니다.");
        }
        scheduleRepo.deleteById(id);
    }
}
