package org.example.schedule_project.dto.schedule;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateScheduleRequestDto {

    // jakarta.validation 에서 import
    @NotBlank(message = "할일은 비어있을 수 없습니다.")
    @Size(max = 200, message = "할일은 최대 200자 이내의 길이만 허용합니다.")
    private final String todo;

    private final Long userId;

    @NotBlank(message = "일정의 비밀번호는 필수값 입니다.")
    private final String password;


    public CreateScheduleRequestDto(String todo, Long userId, String password) {
        this.todo = todo;
        this.userId = userId;
        this.password = password;
    }
}
