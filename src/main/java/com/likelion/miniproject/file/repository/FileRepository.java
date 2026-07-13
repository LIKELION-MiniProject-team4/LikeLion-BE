package com.likelion.miniproject.file.repository;

import com.likelion.miniproject.file.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
