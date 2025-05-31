package org.example.schedule_project.repository.schedule.jdbc;

import org.example.schedule_project.entity.schedule.Schedule;
import org.example.schedule_project.jdbc.mapper.ScheduleRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ScheduleJdbcRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ScheduleRowMapper rowMapper; // schedule 엔티티로 매핑해주는 RowMapper

    public ScheduleJdbcRepository(JdbcTemplate jdbcTemplate, ScheduleRowMapper rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }

    public Long save(String todo, Long userId, String password) {
        String sql = "INSERT INTO schedule (todo, user_id, password) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, todo, userId, password);
        // 방금 INSERT된 PK 값을 가져오기 (MySQL: LAST_INSERT_ID())
        Long newId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        return newId;
    }

    public Optional<Schedule> findById(Long id) {
        try {
            Schedule s = jdbcTemplate.queryForObject(
                    "SELECT * FROM schedule WHERE id = ?", rowMapper, id);
            return Optional.of(s);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<Schedule> findAll() {
        return jdbcTemplate.query("SELECT * FROM schedule", rowMapper);
    }

    public List<Schedule> findByUserId(Long userId) {
        return jdbcTemplate.query("SELECT * FROM schedule WHERE user_id = ?", rowMapper, userId);
    }

    public void update(Long id, String todo, Long userId) {
        String sql = "UPDATE schedule SET todo = ?, user_id = ? WHERE id = ?";
        jdbcTemplate.update(sql, todo, userId, id);
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM schedule WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
