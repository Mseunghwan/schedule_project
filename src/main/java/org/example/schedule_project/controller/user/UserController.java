package org.example.schedule_project.controller.user;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.schedule_project.dto.user.CreateUserRequestDto;
import org.example.schedule_project.dto.user.UserRequestDto;
import org.example.schedule_project.dto.user.UserResponseDto;
import org.example.schedule_project.entity.user.User;
import org.example.schedule_project.service.user.jdbc.UserServiceJdbc;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceJdbc userService;

    // 유저 생성, 유저 조회, 유저 수정, 유저 삭제 구현

    // 유저 생성
    @PostMapping
    public ResponseEntity<User> save(@RequestBody @Valid CreateUserRequestDto requestDto) {
        // @Valid가 있어야 Dto로 받은 값의 유효성 검사가 작동한다
        UserResponseDto userResponseDto =
                userService.save(
                        requestDto.getEmail(),
                        requestDto.getUsername()
                );
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // 유저 전체 조회
    @GetMapping()
    public ResponseEntity<List<UserResponseDto>> getAll() {
        List<UserResponseDto> userResponseDtoList = userService.findAll();

        return new ResponseEntity<>(userResponseDtoList, HttpStatus.OK);
    }

    // 유저 개별 조회
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id) {
        UserResponseDto userResponseDto = userService.findById(id);

        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    // 유저 수정
    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequestDto requestDto
    ){
        userService.updateUser(id, requestDto.getEmail(), requestDto.getUsername());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 유저 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
