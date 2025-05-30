package org.example.schedule_project.controller.schedule;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.schedule_project.dto.schedule.CreateScheduleRequestDto;
import org.example.schedule_project.dto.schedule.DeleteScheduleRequestDto;
import org.example.schedule_project.dto.schedule.ScheduleResponseDto;
import org.example.schedule_project.dto.schedule.UpdateScheduleRequestDto;
import org.example.schedule_project.repository.schedule.jdbc.ScheduleServiceJdbc;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    // JPA 서비스 대신 JDBC 서비스 주입
    private final ScheduleServiceJdbc scheduleService;

//    private final ScheduleService scheduleService;

    // 일정 생성
    // @Valid가 있어야 Dto로 받은 값의 유효성 검사가 작동한다
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> save(@RequestBody @Valid CreateScheduleRequestDto requestDto) {
        ScheduleResponseDto scheduleResponseDto = scheduleService.save(
                requestDto.getTodo(),
                requestDto.getUserId(),
                requestDto.getPassword()
        );
        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.CREATED);
    }


    // Paging
    @GetMapping("/page")
    public ResponseEntity<List<ScheduleResponseDto>> getSchedules(Pageable pageable) {
        // JdbcService에서는 페이징 미지원 시 findAll 사용
        List<ScheduleResponseDto> scheduleList = scheduleService.findAll();
        return new ResponseEntity<>(scheduleList, HttpStatus.OK);
    }



    // 전체 일정 조회
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> getAll() {
        List<ScheduleResponseDto> scheduleResponseDtoList = scheduleService.findAll();
        return new ResponseEntity<>(scheduleResponseDtoList, HttpStatus.OK);
    }

    // 일정 id로 조회
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> getById(@PathVariable Long id) {
        ScheduleResponseDto scheduleResponseDto = scheduleService.findById(id);
        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.OK);
    }

     // --> User id로 일정 검색될 수 있도록 수정
     @GetMapping("/user/{userId}")
     public ResponseEntity<List<ScheduleResponseDto>> getSchedulesByUser(@PathVariable Long userId) {
         List<ScheduleResponseDto> schedulesResponseDto = scheduleService.findByUserId(userId);
         return new ResponseEntity<>(schedulesResponseDto, HttpStatus.OK);
     }


    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateSchedule(
            @PathVariable Long id,
            @RequestBody UpdateScheduleRequestDto requestDto) {
        scheduleService.updateSchedule(id, requestDto.getTodo(), requestDto.getUserId(), requestDto.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long id,
            @RequestBody DeleteScheduleRequestDto requestDto) {
        scheduleService.delete(id, requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
