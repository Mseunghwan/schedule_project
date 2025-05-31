package org.example.schedule_project.jdbc.mapper;

import org.example.schedule_project.entity.schedule.Schedule;
import org.example.schedule_project.entity.user.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ScheduleRowMapper implements RowMapper<Schedule> {
    @Override
    public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
        Schedule s = new Schedule();
        s.setId(rs.getLong("id"));
        s.setTodo(rs.getString("todo"));
        s.setPassword(rs.getString("password"));
        User u = new User();
        u.setId(rs.getLong("user_id"));
        s.setUser(u);
        return s;
    }
}
