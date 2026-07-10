package com.likelion.miniproject.tag;

public record TagResponseDto(
        Long tagId,
        String name
) {
    public static TagResponseDto from(Tag tag) {
        return new TagResponseDto(tag.getId(), tag.getName());
    }
}