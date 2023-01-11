package com.mainproject.server.dto;

import com.mainproject.server.constant.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class ErrorResponse {
    private int status;
    private String message;
    private List<FieldError> fieldErrors;
    private List<ConstraintViolationError> violationErrors;

    private ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
    private ErrorResponse(List<FieldError> fieldErrors,
                          List<ConstraintViolationError> violationErrors) {
        this.fieldErrors = fieldErrors;
        this.violationErrors = violationErrors;
    }

    public static ErrorResponse of(BindingResult bindingResult) {
        return new ErrorResponse(FieldError.of(bindingResult),null);
    }

    public static ErrorResponse of(Set<ConstraintViolation<?>> violations) {
        return new ErrorResponse(null, ConstraintViolationError.of(violations));
    }

    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(errorCode.getStatus(), errorCode.getMessage());
    }

    public static ErrorResponse of(HttpStatus status) {
        return new ErrorResponse(status.value(), status.getReasonPhrase());
    }
    public static ErrorResponse of(HttpStatus httpStatus, String message) {
        return new ErrorResponse(httpStatus.value(), message);
    }

    private static class FieldError {
        private String field;
        private Object rejectedValue;
        private String reason;
        private FieldError(String field, Object rejectedValue, String reason) {
            this.field = field;
            this.rejectedValue = rejectedValue;
            this.reason = reason;
        }

        public static List<FieldError> of(BindingResult bindingResult) {
            List<org.springframework.validation.FieldError> fieldErrorList =
                    bindingResult.getFieldErrors();
            return fieldErrorList.stream()
                    .map(error ->
                            new FieldError(
                                    error.getField(),
                                    error.getRejectedValue() ==null?
                                            "" : error.getRejectedValue().toString(),
                                    error.getDefaultMessage()
                            )).collect(Collectors.toList());
        }
    }

    private static class ConstraintViolationError {
        private String propertyPath;
        private Object rejectedValue;
        private String reason;

        private ConstraintViolationError(String propertyPath,
                                         Object rejectedValue,
                                         String reason) {
            this.propertyPath = propertyPath;
            this.rejectedValue = rejectedValue;
            this.reason = reason;
        }

        public static List<ConstraintViolationError> of(
                Set<ConstraintViolation<?>> constraintViolations
        ) {
            return constraintViolations.stream()
                    .map(constraintViolation ->
                            new ConstraintViolationError(
                                    constraintViolation.getPropertyPath().toString(),
                                    constraintViolation.getInvalidValue().toString(),
                                    constraintViolation.getMessage()
                            )).collect(Collectors.toList());
        }
    }
}