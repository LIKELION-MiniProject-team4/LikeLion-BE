package com.likelion.miniproject.tag.repository;

import com.likelion.miniproject.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
    boolean existsByName(String name);
}