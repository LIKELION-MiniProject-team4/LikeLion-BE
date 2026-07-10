package com.likelion.miniproject.tag.exception;

import com.likelion.miniproject.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TagErrorCode implements ErrorCode {
    TAG_NOT_FOUND(HttpStatus.NOT_FOUND, "TAG_404", "존재하지 않는 태그입니다."),
    DUPLICATE_TAG_NAME(HttpStatus.CONFLICT, "TAG_409_1", "이미 존재하는 태그명입니다."),
    DUPLICATE_TAG_CLICK(HttpStatus.CONFLICT, "TAG_409_2", "이미 클릭한 태그입니다."),
    CERTIFICATE_NOT_APPROVED(HttpStatus.FORBIDDEN, "TAG_403", "수강확인서 승인자만 이용할 수 있습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}