package org.example.schedule_project.controller;

import lombok.RequiredArgsConstructor;
import org.example.schedule_project.dto.CreateScheduleRequestDto;
import org.example.schedule_project.dto.ScheduleResponseDto;
import org.example.schedule_project.entity.Schedule;
import org.example.schedule_project.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
                        requestDto.getWriteUser(),
                        requestDto.getPassword()
                );

        return new ResponseEntity<>(HttpStatus.CREATED);

}
}
