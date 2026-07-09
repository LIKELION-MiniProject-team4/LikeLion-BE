package com.likelion.miniproject.file.exception;

import com.likelion.miniproject.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum FileErrorCode implements ErrorCode {
    INVALID_FILE_TYPE(HttpStatus.BAD_REQUEST, "FILE_400_1", "허용되지 않는 파일 형식입니다."),
    INVALID_FILE_SIZE(HttpStatus.BAD_REQUEST, "FILE_400_2", "파일 용량이 허용 범위를 초과했습니다."),
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "FILE_404_1", "파일을 찾을 수 없습니다."),
    FILE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "FILE_500_1", "파일 업로드에 실패했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
