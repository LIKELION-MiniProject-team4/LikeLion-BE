package com.likelion.miniproject.infrastructure.s3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;

/**
 * 백엔드가 파일을 직접 받아서(멀티파트) S3에 올리는 방식.
 * 짐짝처럼 presigned URL로 프론트가 S3에 직접 올리는 방식이 아니라, 서버가 중간에서 한 번에 처리한다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class S3FileStorage {

    private final S3Client s3Client;
    private final S3Properties s3Properties;

    public String upload(MultipartFile multipartFile, String key) {
        try {
            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(s3Properties.getS3().getBucket())
                            .key(key)
                            .contentType(multipartFile.getContentType())
                            .build(),
                    RequestBody.fromInputStream(multipartFile.getInputStream(), multipartFile.getSize())
            );
        } catch (IOException | S3Exception e) {
            log.error("S3 파일 업로드 실패 - key: {}", key, e);
            throw new S3UploadException(e);
        }

        return buildPublicUrl(key);
    }

    public void delete(String key) {
        try {
            s3Client.deleteObject(
                    DeleteObjectRequest.builder()
                            .bucket(s3Properties.getS3().getBucket())
                            .key(key)
                            .build()
            );
        } catch (S3Exception e) {
            log.error("S3 파일 삭제 실패 - key: {}", key, e);
        }
    }

    private String buildPublicUrl(String key) {
        return String.format(
                "https://%s.s3.%s.amazonaws.com/%s",
                s3Properties.getS3().getBucket(),
                s3Properties.getRegion(),
                key
        );
    }
}
