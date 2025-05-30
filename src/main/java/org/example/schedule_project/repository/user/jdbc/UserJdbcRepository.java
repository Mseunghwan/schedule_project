package org.example.schedule_project.repository.user.jdbc;

import org.example.schedule_project.entity.user.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<User> userRowMapper = (rs, rowNum) ->

            new User(
                    rs.getString("username"),
                    rs.getString("email")
            );

    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM user WHERE id = ?";
        return jdbcTemplate.query(sql, userRowMapper, id).stream().findFirst();
    }
}
