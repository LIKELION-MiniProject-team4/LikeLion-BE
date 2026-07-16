package com.likelion.miniproject.global.exception;

import com.likelion.miniproject.global.response.GlobalApiErrorResponse;
import com.likelion.miniproject.global.security.exception.AuthException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<GlobalApiErrorResponse> handleApplicationException(ApplicationException e) {
        ErrorCode errorCode = e.getErrorCode();
        String traceId = traceId();

        log.warn("event=application_exception name={} code={} message={} traceId={} context={}",
                errorCode.name(), errorCode.getCode(), e.getMessage(), traceId, e.getContext());

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(GlobalApiErrorResponse.of(e, traceId));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalApiErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, Object> details = fieldErrorsToMap(e.getBindingResult().getFieldErrors());
        String traceId = traceId();

        log.warn("event=validation_exception traceId={} details={}", traceId, details);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(GlobalApiErrorResponse.of(CommonErrorCode.INVALID_INPUT, traceId, details));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<GlobalApiErrorResponse> handleBindException(BindException e) {
        Map<String, Object> details = fieldErrorsToMap(e.getBindingResult().getFieldErrors());
        String traceId = traceId();

        log.warn("event=bind_exception traceId={} details={}", traceId, details);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(GlobalApiErrorResponse.of(CommonErrorCode.INVALID_INPUT, traceId, details));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<GlobalApiErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
        Map<String, Object> details = e.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        v -> v.getPropertyPath().toString(),
                        v -> v.getMessage(),
                        (a, b) -> a
                ));
        String traceId = traceId();

        log.warn("event=constraint_violation_exception traceId={} details={}", traceId, details);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(GlobalApiErrorResponse.of(CommonErrorCode.INVALID_INPUT, traceId, details));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<GlobalApiErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        String traceId = traceId();
        Map<String, Object> details = Map.of(
                "parameter", e.getName(),
                "rejectedValue", String.valueOf(e.getValue())
        );

        log.warn("event=type_mismatch_exception traceId={} details={}", traceId, details);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(GlobalApiErrorResponse.of(CommonErrorCode.INVALID_INPUT, traceId, details));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<GlobalApiErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        String traceId = traceId();

        log.warn("event=data_integrity_violation traceId={} message={}", traceId, e.getMessage());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(GlobalApiErrorResponse.of(CommonErrorCode.CONFLICT, traceId));
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<GlobalApiErrorResponse> handleAuthException(AuthException e) {
        ErrorCode errorCode = e.getErrorCode();
        String traceId = traceId();

        log.warn("event=auth_exception name={} code={} message={} traceId={}",
                errorCode.name(), errorCode.getCode(), e.getMessage(), traceId);

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(GlobalApiErrorResponse.of(errorCode, traceId));
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<GlobalApiErrorResponse> handleAuthorizationDeniedException(AuthorizationDeniedException e) {
        String traceId = traceId();

        log.warn("event=authorization_denied traceId={} message={}", traceId, e.getMessage());

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(GlobalApiErrorResponse.of(CommonErrorCode.ACCESS_DENIED, traceId));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalApiErrorResponse> handleException(Exception e) {
        String traceId = traceId();

        log.error("event=unhandled_exception traceId={}", traceId, e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(GlobalApiErrorResponse.of(CommonErrorCode.INTERNAL_SERVER_ERROR, traceId));
    }

    private String traceId() {
        return MDC.get("traceId");
    }

    private Map<String, Object> fieldErrorsToMap(java.util.List<FieldError> fieldErrors) {
        Map<String, Object> details = new HashMap<>();
        fieldErrors.forEach(fe -> details.put(fe.getField(), fe.getDefaultMessage()));
        return details;
    }
}
