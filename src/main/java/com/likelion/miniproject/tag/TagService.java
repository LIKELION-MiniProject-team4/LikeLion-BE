package com.likelion.miniproject.tag;

import com.likelion.miniproject.global.certificate.CertificateAccessChecker;
import com.likelion.miniproject.professor.Professor;
import com.likelion.miniproject.professor.ProfessorService;
import com.likelion.miniproject.global.certificate.exception.CertificateNotApprovedException;
import com.likelion.miniproject.tag.exception.DuplicateTagClickException;
import com.likelion.miniproject.tag.exception.DuplicateTagNameException;
import com.likelion.miniproject.tag.exception.TagNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final TagClickRepository tagClickRepository;
    private final ProfessorService professorService;
    private final CertificateAccessChecker certificateAccessChecker;

    @Transactional
    public TagResponseDto createTag(TagCreateRequestDto request) {
        if (tagRepository.existsByName(request.name())) {
            throw new DuplicateTagNameException();
        }
        Tag tag = tagRepository.save(Tag.builder().name(request.name()).build());
        return TagResponseDto.from(tag);
    }

    @Transactional(readOnly = true)
    public List<TagResponseDto> getAllTags() {
        return tagRepository.findAll().stream().map(TagResponseDto::from).toList();
    }

    /** 수강확인서 승인자만 클릭 가능(403), 동일 조합 재클릭 불가(409) */
    @Transactional
    public void click(Long userId, Long professorId, Long tagId) {
        if (!certificateAccessChecker.isApproved(userId, professorId)) {
            throw new CertificateNotApprovedException();
        }

        if (tagClickRepository.existsByUserIdAndProfessorIdAndTagId(userId, professorId, tagId)) {
            throw new DuplicateTagClickException();
        }

        Professor professor = professorService.getProfessorOrThrow(professorId);
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(TagNotFoundException::new);

        tagClickRepository.save(TagClick.builder().userId(userId).professor(professor).tag(tag).build());
    }
}