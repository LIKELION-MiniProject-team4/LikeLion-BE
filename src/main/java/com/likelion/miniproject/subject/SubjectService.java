package com.likelion.miniproject.subject;

import com.likelion.miniproject.subject.exception.SubjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;

    /** review 등 다른 도메인에서 "이 과목이 진짜 있나" 확인할 때 재사용 */
    public Subject getSubjectOrThrow(Long subjectId) {
        return subjectRepository.findById(subjectId)
                .orElseThrow(SubjectNotFoundException::new);
    }
}