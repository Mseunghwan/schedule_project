package org.example.schedule_project.controller.schedule;

import lombok.RequiredArgsConstructor;
import org.example.schedule_project.dto.schedule.CreateScheduleRequestDto;
import org.example.schedule_project.dto.schedule.DeleteScheduleRequestDto;
import org.example.schedule_project.dto.schedule.ScheduleResponseDto;
import org.example.schedule_project.dto.schedule.UpdateScheduleRequestDto;
import org.example.schedule_project.entity.schedule.Schedule;
import org.example.schedule_project.service.schedule.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 일정 생성
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> save(@RequestBody CreateScheduleRequestDto requestDto) {
        ScheduleResponseDto scheduleResponseDto =
                scheduleService.save(
                        requestDto.getTodo(),
                        requestDto.getUserId(),
                        requestDto.getPassword()
                );

        return new ResponseEntity<>(HttpStatus.CREATED);

}

    // 전체 일정 조회
    @GetMapping()
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
            @RequestBody UpdateScheduleRequestDto requestDto){

        scheduleService.updateSchedule(id, requestDto.getTodo(), requestDto.getUserId(), requestDto.getPassword());

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long id,
            @RequestBody DeleteScheduleRequestDto requestDto
    ) {
        scheduleService.delete(id, requestDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
