package org.example.schedule_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing  // ← 요거!
@EnableScheduling // 이게 있어야 자동으로 date 생성 허용
public class ScheduleProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScheduleProjectApplication.class, args);
    }

}
