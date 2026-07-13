package com.likelion.miniproject.subject.service;

import com.likelion.miniproject.subject.entity.Subject;
import com.likelion.miniproject.subject.exception.SubjectNotFoundException;
import com.likelion.miniproject.subject.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/** 과목 등록 API는 범위 밖(out of scope). review 등에서 "존재 확인"용으로만 사용됨 */
@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;

    public Subject getSubjectOrThrow(Long subjectId) {
        return subjectRepository.findById(subjectId)
                .orElseThrow(SubjectNotFoundException::new);
    }
}