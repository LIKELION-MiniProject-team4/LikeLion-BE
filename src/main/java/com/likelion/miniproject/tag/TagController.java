package com.likelion.miniproject.tag;

import com.likelion.miniproject.global.security.jwt.AuthUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping("/api/tags")
    public ResponseEntity<List<TagResponseDto>> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTags());
    }

    @PostMapping("/api/admin/tags")
    public ResponseEntity<TagResponseDto> createTag(@Valid @RequestBody TagCreateRequestDto request) {
        return ResponseEntity.status(201).body(tagService.createTag(request));
    }

    @PostMapping("/api/professors/{professorId}/tags/{tagId}/clicks")
    public ResponseEntity<Void> clickTag(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long professorId,
            @PathVariable Long tagId
    ) {
        tagService.click(authUser.userId(), professorId, tagId);
        return ResponseEntity.ok().build();
    }
}