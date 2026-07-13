package com.likelion.miniproject.tag.controller.response;

import com.likelion.miniproject.tag.entity.Tag;

public record TagResponse(
        Long tagId,
        String name
) {
    public static TagResponse from(Tag tag) {
        return new TagResponse(tag.getId(), tag.getName());
    }
}