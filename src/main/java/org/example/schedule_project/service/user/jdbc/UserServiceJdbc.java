package org.example.schedule_project.service.user.jdbc;

import org.example.schedule_project.dto.user.UserResponseDto;
import org.example.schedule_project.entity.user.User;
import org.example.schedule_project.repository.user.jdbc.UserJdbcRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceJdbc {

    private final UserJdbcRepository userRepo;

    public UserServiceJdbc(UserJdbcRepository userRepo) {
        this.userRepo = userRepo;
    }

// 유저 생성
    @Transactional
    public UserResponseDto save(String email, String username) {
        Long newId = userRepo.save(email, username);
        // 바로 조회해서 DTO로 변환
        User user = userRepo.findById(newId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "저장 후 조회 불가"));
        return new UserResponseDto(user.getId(), user.getEmail(), user.getUsername());
    }

//      유저 전체 조회


    public List<UserResponseDto> findAll() {
        return userRepo.findAll().stream()
                .map(u -> new UserResponseDto(u.getId(), u.getEmail(), u.getUsername()))
                .collect(Collectors.toList());
    }


//     유저 단건 조회
    public UserResponseDto findById(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "해당 유저가 없습니다."));
        return new UserResponseDto(user.getId(), user.getEmail(), user.getUsername());
    }


//     유저 업데이트 (이메일, 사용자 이름 변경)

    @Transactional
    public void updateUser(Long id, String email, String username) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "해당 유저가 없습니다."));
        // 이메일 또는 이름을 변경하려면 UPDATE 쿼리 실행
        userRepo.update(id, email, username);
    }


//     유저 삭제
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "해당 유저가 없습니다."));
        userRepo.deleteById(id);
    }
}
