package org.example.schedule_project.repository.schedule.jdbc;

import org.example.schedule_project.dto.schedule.ScheduleResponseDto;
import org.example.schedule_project.dto.schedule.DeleteScheduleRequestDto;
import org.example.schedule_project.entity.schedule.Schedule;
import org.example.schedule_project.entity.user.User;
import org.example.schedule_project.repository.user.jdbc.UserJdbcRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ScheduleServiceJdbc {

    private final JdbcTemplate jdbcTemplate;
    private final UserJdbcRepository userRepo;

    public ScheduleServiceJdbc(JdbcTemplate jdbcTemplate, UserJdbcRepository userRepo) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepo = userRepo;
    }

    private final RowMapper<Schedule> rowMapper = (rs, rowNum) -> {
        Schedule s = new Schedule();
        s.setId(rs.getLong("id"));
        s.setTodo(rs.getString("todo"));
        s.setPassword(rs.getString("password"));
        User u = new User();
        u.setId(rs.getLong("user_id"));
        s.setUser(u);
        return s;
    };

    public ScheduleResponseDto save(String todo, Long userId, String password) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        String sql = "INSERT INTO schedule (todo, user_id, password) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, todo, userId, password);

        String selectSql = "SELECT * FROM schedule WHERE id = LAST_INSERT_ID()";
        Schedule schedule = jdbcTemplate.queryForObject(selectSql, rowMapper);
        return new ScheduleResponseDto(schedule.getId(), schedule.getTodo(), schedule.getUser().getId());
    }

    public List<ScheduleResponseDto> findAll() {
        String sql = "SELECT * FROM schedule";
        return jdbcTemplate.query(sql, rowMapper).stream()
                .map(s -> new ScheduleResponseDto(s.getId(), s.getTodo(), s.getUser().getId()))
                .toList();
    }

    public ScheduleResponseDto findById(Long id) {
        String sql = "SELECT * FROM schedule WHERE id = ?";
        try {
            Schedule schedule = jdbcTemplate.queryForObject(sql, rowMapper, id);
            return new ScheduleResponseDto(schedule.getId(), schedule.getTodo(), schedule.getUser().getId());
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다.");
        }
    }

    public List<ScheduleResponseDto> findByUserId(Long userId) {
        String sql = "SELECT * FROM schedule WHERE user_id = ?";
        return jdbcTemplate.query(sql, rowMapper, userId)
                .stream()
                .map(s -> new ScheduleResponseDto(
                        s.getId(),
                        s.getTodo(),
                        s.getUser().getId()))
                .toList();
    }

    public void updateSchedule(Long id, String todo, Long userId, String password) {
        // 1) 원본 일정 조회 (비밀번호 검증용)
        Schedule orig;
        try {
            orig = jdbcTemplate.queryForObject(
                    "SELECT * FROM schedule WHERE id = ?", rowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다.");
        }
        // 2) 비밀번호 틀리면 예외
        if (!orig.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호가 일치하지 않습니다.");
        }
        // 3) 업데이트
        String updateSql = "UPDATE schedule SET todo = ?, user_id = ? WHERE id = ?";
        jdbcTemplate.update(updateSql, todo, userId, id);
    }

    public void delete(Long id, DeleteScheduleRequestDto dto) {
        // 1) 원본 일정 조회
        Schedule orig;
        try {
            orig = jdbcTemplate.queryForObject(
                    "SELECT * FROM schedule WHERE id = ?", rowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다.");
        }
        // 2) 비밀번호 검증
        if (!orig.getPassword().equals(dto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호가 일치하지 않습니다.");
        }
        // 3) 삭제
        jdbcTemplate.update("DELETE FROM schedule WHERE id = ?", id);
    }

}