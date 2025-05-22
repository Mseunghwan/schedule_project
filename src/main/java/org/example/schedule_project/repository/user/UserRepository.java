package org.example.schedule_project.repository.user;

import org.example.schedule_project.entity.user.User;
import org.example.schedule_project.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface UserRepository extends JpaRepository<User, Long> {

    default User findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException("해당 유저가 존재하지 않습니다."));
    }

}
