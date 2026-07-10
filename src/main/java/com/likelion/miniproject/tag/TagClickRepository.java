package com.likelion.miniproject.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagClickRepository extends JpaRepository<TagClick, Long> {

    boolean existsByUserIdAndProfessorIdAndTagId(Long userId, Long professorId, Long tagId);

    // 마이페이지 "내 태그 클릭 이력" 에서 나중에 사용
    List<TagClick> findByUserIdOrderByCreatedAtDesc(Long userId);

    // 교수 상세페이지에서 "태그별 클릭수" 보여줄 때 나중에 사용
    @Query("SELECT tc.tag.id AS tagId, tc.tag.name AS tagName, COUNT(tc) AS clickCount " +
            "FROM TagClick tc WHERE tc.professor.id = :professorId " +
            "GROUP BY tc.tag.id, tc.tag.name ORDER BY COUNT(tc) DESC")
    List<TagClickCount> countByProfessorGroupByTag(@Param("professorId") Long professorId);

    interface TagClickCount {
        Long getTagId();
        String getTagName();
        Long getClickCount();
    }
}