package com.likelion.miniproject.tag.controller;

import com.likelion.miniproject.global.response.GlobalApiResponse;
import com.likelion.miniproject.global.security.jwt.AuthUser;
import com.likelion.miniproject.tag.controller.request.TagCreateRequest;
import com.likelion.miniproject.tag.controller.response.TagResponse;
import com.likelion.miniproject.tag.controller.response.TagResponseCode;
import com.likelion.miniproject.tag.service.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping("/api/tags")
    public ResponseEntity<GlobalApiResponse<List<TagResponse>>> getAllTags() {
        List<TagResponse> result = tagService.getAllTags();
        return ResponseEntity.ok(GlobalApiResponse.ok(TagResponseCode.TAG_LIST_FETCHED, result));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/api/tags")
    public ResponseEntity<GlobalApiResponse<TagResponse>> createTag(@Valid @RequestBody TagCreateRequest request) {
        TagResponse result = tagService.createTag(request);
        return ResponseEntity.status(201).body(GlobalApiResponse.created(TagResponseCode.TAG_CREATED, result));
    }

    @PostMapping("/api/professors/{professorId}/tags/{tagId}/clicks")
    public ResponseEntity<GlobalApiResponse<Void>> clickTag(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long professorId,
            @PathVariable Long tagId
    ) {
        tagService.click(authUser.userId(), professorId, tagId);
        return ResponseEntity.ok(GlobalApiResponse.ok(TagResponseCode.TAG_CLICKED));
    }
}