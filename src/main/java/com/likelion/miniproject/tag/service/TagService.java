package com.likelion.miniproject.tag.service;

import com.likelion.miniproject.global.certificate.CertificateAccessChecker;
import com.likelion.miniproject.global.certificate.exception.CertificateNotApprovedException;
import com.likelion.miniproject.professor.entity.Professor;
import com.likelion.miniproject.professor.service.ProfessorService;
import com.likelion.miniproject.tag.controller.request.TagCreateRequest;
import com.likelion.miniproject.tag.controller.response.MyTagClickResponse;
import com.likelion.miniproject.tag.controller.response.TagResponse;
import com.likelion.miniproject.tag.entity.Tag;
import com.likelion.miniproject.tag.entity.TagClick;
import com.likelion.miniproject.tag.exception.DuplicateTagClickException;
import com.likelion.miniproject.tag.exception.DuplicateTagNameException;
import com.likelion.miniproject.tag.exception.TagNotFoundException;
import com.likelion.miniproject.tag.repository.TagClickRepository;
import com.likelion.miniproject.tag.repository.TagRepository;
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
    public TagResponse createTag(TagCreateRequest request) {
        if (tagRepository.existsByName(request.getName())) {
            throw new DuplicateTagNameException();
        }
        Tag tag = tagRepository.save(request.toEntity());
        return TagResponse.from(tag);
    }

    @Transactional(readOnly = true)
    public List<TagResponse> getAllTags() {
        return tagRepository.findAll().stream().map(TagResponse::from).toList();
    }

    @Transactional
    public void click(Long userId, Long professorId, Long tagId) {
        Professor professor = professorService.getProfessorOrThrow(professorId);

        if (!certificateAccessChecker.isApproved(userId, professorId)) {
            throw new CertificateNotApprovedException();
        }

        if (tagClickRepository.existsByUserIdAndProfessorIdAndTagId(userId, professorId, tagId)) {
            throw new DuplicateTagClickException();
        }

        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(TagNotFoundException::new);

        tagClickRepository.save(TagClick.builder().userId(userId).professor(professor).tag(tag).build());
    }

    @Transactional(readOnly = true)
    public List<MyTagClickResponse> getMyTagClicks(Long userId) {
        return tagClickRepository.findByUserIdWithProfessorAndTag(userId).stream()
                .map(MyTagClickResponse::from)
                .toList();
    }
}