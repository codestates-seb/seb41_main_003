package com.mainproject.server.advice;

import com.mainproject.server.constant.ErrorCode;
import com.mainproject.server.dto.ErrorResponse;
import com.mainproject.server.exception.ServiceLogicException;
import com.mainproject.server.webhook.WebHookDto;
import com.mainproject.server.webhook.WebHookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionAdvice {

    private final WebHookService webHookService;

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse methodArgumentNotValidHandle(
            MethodArgumentNotValidException e
    ) {
        return ErrorResponse.of(e.getBindingResult());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse constraintViolationExceptionHandler(
            ConstraintViolationException e
    ) {
        return ErrorResponse.of(e.getConstraintViolations());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorResponse httpRequestMethodNotSupportedExceptionHandler(
            HttpRequestMethodNotSupportedException e
    ) {
        return ErrorResponse.of(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse httpMessageNotReadableExceptionHandler(
            HttpMessageNotReadableException e
    ) {
        return ErrorResponse.of(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse missingServletRequestParameterExceptionHandler(
            MissingServletRequestParameterException e
    ) {
        return ErrorResponse.of(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse exceptionHandler(Exception e) {
        WebHookDto dto = getServerErrorWebHookDto();
        webHookService.callEvent(dto);
        return ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity serviceLogicExceptionHandler(
            ServiceLogicException e
    ) {
        if (e.getErrorCode().equals(ErrorCode.INTERNAL_SERVER_ERROR)) {
            WebHookDto dto = getServerErrorWebHookDto();
            webHookService.callEvent(dto);
        }
        return new ResponseEntity<>(
                ErrorResponse.of(e.getErrorCode()),
                HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }

    private static WebHookDto getServerErrorWebHookDto() {
        String name = ErrorCode.INTERNAL_SERVER_ERROR.name();
        String message = ErrorCode.INTERNAL_SERVER_ERROR.getMessage();
        int status = ErrorCode.INTERNAL_SERVER_ERROR.getStatus();
        return new WebHookDto(" 📢   " + status + " ❗ " + message +'\n' +"⏱️  Time: "+ LocalDateTime.now());
    }
}

