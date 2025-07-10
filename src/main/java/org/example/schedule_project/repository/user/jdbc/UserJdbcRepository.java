package org.example.schedule_project.repository.user.jdbc;

import org.example.schedule_project.entity.user.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // resultSet → User 엔티티로 매핑해주는 RowMapper
    private final RowMapper<User> rowMapper = (rs, rowNum) -> {
        User u = new User();                // User(String username, String email) 대신 빈 생성 후 set 사용
        u.setId(rs.getLong("id"));
        u.setName(rs.getString("username"));
        u.setEmail(rs.getString("email"));
        return u;
    };

//    User 저장
 // JPA와 달리 ID를 리턴받으려면 LAST_INSERT_ID()를 별도로 조회합니다
    public Long save(String email, String username) {
        String sql = "INSERT INTO user (email, username) VALUES (?, ?)";
        jdbcTemplate.update(sql, email, username);
        // MySQL: 마지막으로 INSERT된 ID를 조회
        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
    }


//     User 전체 조회

    public List<User> findAll() {
        String sql = "SELECT * FROM user";
        return jdbcTemplate.query(sql, rowMapper);
    }


//     User 단건 조회
    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM user WHERE id = ?";
        List<User> list = jdbcTemplate.query(sql, rowMapper, id);
        return list.stream().findFirst();
    }

//      User 업데이트 (email, username)

    public void update(Long id, String email, String username) {
        String sql = "UPDATE user SET email = ?, username = ? WHERE id = ?";
        jdbcTemplate.update(sql, email, username, id);
    }


//     User 삭제
    public void deleteById(Long id) {
        String sql = "DELETE FROM user WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
