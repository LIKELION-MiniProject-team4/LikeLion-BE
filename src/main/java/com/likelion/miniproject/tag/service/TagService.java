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
import com.likelion.miniproject.tag.event.TagClickedEvent;
import com.likelion.miniproject.tag.repository.TagClickRepository;
import com.likelion.miniproject.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.likelion.miniproject.global.point.UserPointManager;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final TagClickRepository tagClickRepository;
    private final ProfessorService professorService;
    private final CertificateAccessChecker certificateAccessChecker;
    private final ApplicationEventPublisher eventPublisher;

    private static final int TAG_CLICK_POINT_REWARD = 2;
    private final UserPointManager userPointManager;

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

        // 이 교수에 대한 첫 태그 클릭인지 저장 전에 확인해야 한다 (저장 후엔 항상 true가 됨).
        boolean isFirstClickForProfessor = !tagClickRepository.existsByUserIdAndProfessorId(userId, professorId);

        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(TagNotFoundException::new);

        tagClickRepository.save(TagClick.builder().userId(userId).professor(professor).tag(tag).build());
        userPointManager.earn(userId, TAG_CLICK_POINT_REWARD);
        TagClick tagClick = tagClickRepository.save(TagClick.builder().userId(userId).professor(professor).tag(tag).build());

        if (isFirstClickForProfessor) {
            eventPublisher.publishEvent(new TagClickedEvent(tagClick.getId(), userId, professorId));
        }
    }

    @Transactional(readOnly = true)
    public List<MyTagClickResponse> getMyTagClicks(Long userId) {
        return tagClickRepository.findByUserIdWithProfessorAndTag(userId).stream()
                .map(MyTagClickResponse::from)
                .toList();
    }
}