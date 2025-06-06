package org.example.schedule_project.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice // 다른 처리 필요없이 @RestController 에서 작동하는 exception handler
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)

    // 선택한 일정 정보를 조회할 수 없을 때 예외 발생 handler
    public ResponseEntity<Object> handleNotFound(NotFoundException e) {
        return buildResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    // 수정, 삭제 시 요청할 때 보내는 `비밀번호`가 일치하지 않을 때 예외 발생 handler
    @ExceptionHandler(MismatchException.class)
    public ResponseEntity<Object> handlePasswordMismatch(MismatchException e) {
        return buildResponse(HttpStatus.FORBIDDEN, e.getMessage());
    }

    // 이외 예외 발생 handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleOtherExceptions(Exception e) {
        e.printStackTrace();
        log.error("예기치 못한 서버 오류 발생", e);

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", "Internal Server Error");
        body.put("message", "서버 오류가 발생했습니다.");

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Response 형식 지정
    private ResponseEntity<Object> buildResponse(HttpStatus status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);

        return new ResponseEntity<>(body, status);
    }

    // 마찬가지로 유효성 검사를 위한 ExceptionHandler 작성
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException e) {
        e.printStackTrace(); // ★ 콘솔에 무조건 출력
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Bad Request");

        // 첫 번째 오류 메시지만 간단히 추출
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getDefaultMessage())
                .findFirst()
                .orElse("잘못된 입력입니다.");

        body.put("message", message);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }


}
