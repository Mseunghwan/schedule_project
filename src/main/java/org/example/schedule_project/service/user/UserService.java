package org.example.schedule_project.service.user;

import jakarta.transaction.Transactional;
import org.example.schedule_project.dto.user.UserResponseDto;
import org.example.schedule_project.entity.user.User;
import org.example.schedule_project.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDto save(String email, String username) {

        // 받은 정보를 바탕으로 User 생성
        User user = new User(email, username);

        // userRepo에 User 정보 저장
        User savedUser =  userRepository.save(user);

        // Dto로 반환
        return new UserResponseDto(savedUser.getId(), savedUser.getEmail(), savedUser.getUsername());

    }

    public List<UserResponseDto> findAll(){
        return userRepository.findAll().stream().map(UserResponseDto::toDto).toList();
    }

    public UserResponseDto findById(Long id) {

        User user = userRepository.findByIdOrThrow(id);

        return new UserResponseDto(user.getId(), user.getEmail(), user.getUsername());
    }

    @Transactional
    public void updateUser(Long id, String email, String username) {
        User findUser = userRepository.findByIdOrThrow(id);

        findUser.update(email, username);
    }

    public void deleteUser(Long id) {
        User findUser = userRepository.findByIdOrThrow(id);

        userRepository.delete(findUser);
    }
}
