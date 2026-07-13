package com.likelion.miniproject.file.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "file")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    @Column(name = "uploader_id", nullable = false)
    private Long uploaderId;

    @Column(name = "original_name", nullable = false)
    private String originalName;

    @Column(name = "stored_name", nullable = false)
    private String storedName;

    @Column(name = "file_url", nullable = false, length = 2048)
    private String fileUrl;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    @Column(name = "file_size", nullable = false)
    private long fileSize;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    private File(
            Long uploaderId,
            String originalName,
            String storedName,
            String fileUrl,
            String contentType,
            long fileSize
    ) {
        this.uploaderId = uploaderId;
        this.originalName = originalName;
        this.storedName = storedName;
        this.fileUrl = fileUrl;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.createdAt = LocalDateTime.now();
    }

    public static File create(
            Long uploaderId,
            String originalName,
            String storedName,
            String fileUrl,
            String contentType,
            long fileSize
    ) {
        return new File(uploaderId, originalName, storedName, fileUrl, contentType, fileSize);
    }

    public void markDeleted() {
        this.deletedAt = LocalDateTime.now();
    }
}
