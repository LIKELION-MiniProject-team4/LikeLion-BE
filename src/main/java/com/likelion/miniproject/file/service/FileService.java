package com.likelion.miniproject.file.service;

import com.likelion.miniproject.file.entity.File;
import com.likelion.miniproject.file.exception.FileErrorCode;
import com.likelion.miniproject.file.exception.FileNotFoundException;
import com.likelion.miniproject.file.exception.InvalidFileException;
import com.likelion.miniproject.file.repository.FileRepository;
import com.likelion.miniproject.file.s3.S3FileStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

// 지금은 수강확인서 업로드가 유일한 용도라 경로/허용 형식/용량을 상수로 고정.
// 두 번째 업로드 용도가 생기면 그때 타입별로 분리한다.
@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    private static final String S3_KEY_PREFIX = "enrollment-confirmation";
    private static final List<String> ALLOWED_CONTENT_TYPES = List.of("image/jpeg", "image/png", "application/pdf");
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024L; // 10MB

    private final S3FileStorage s3FileStorage;
    private final FileRepository fileRepository;

    @Transactional
    public Long upload(MultipartFile multipartFile, Long uploaderId) {
        validate(multipartFile);

        String storedName = UUID.randomUUID().toString();
        String key = S3_KEY_PREFIX + "/" + uploaderId + "/" + storedName;

        String fileUrl = s3FileStorage.upload(multipartFile, key);

        File file = File.create(
                uploaderId,
                multipartFile.getOriginalFilename(),
                storedName,
                fileUrl,
                multipartFile.getContentType(),
                multipartFile.getSize()
        );
        fileRepository.save(file);

        log.info("event=file_uploaded fileId={} uploaderId={}", file.getId(), uploaderId);

        return file.getId();
    }

    @Transactional(readOnly = true)
    public File getById(Long fileId) {
        return fileRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException(fileId));
    }

    @Transactional
    public void delete(Long fileId) {
        File file = getById(fileId);
        String key = S3_KEY_PREFIX + "/" + file.getUploaderId() + "/" + file.getStoredName();

        s3FileStorage.delete(key);
        file.markDeleted();

        log.info("event=file_deleted fileId={}", fileId);
    }

    private void validate(MultipartFile multipartFile) {
        if (!ALLOWED_CONTENT_TYPES.contains(multipartFile.getContentType())) {
            throw new InvalidFileException(FileErrorCode.INVALID_FILE_TYPE);
        }
        if (multipartFile.getSize() > MAX_FILE_SIZE) {
            throw new InvalidFileException(FileErrorCode.INVALID_FILE_SIZE);
        }
    }
}
