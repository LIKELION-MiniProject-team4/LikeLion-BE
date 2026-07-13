package com.likelion.miniproject.tag.controller.request;

import com.likelion.miniproject.tag.entity.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagCreateRequest {

    @NotBlank
    private String name;

    public Tag toEntity() {
        return Tag.builder().name(name).build();
    }
}