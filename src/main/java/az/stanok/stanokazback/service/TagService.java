package az.stanok.stanokazback.service;

import az.stanok.stanokazback.dto.tags.TagCreateDto;
import az.stanok.stanokazback.dto.tags.TagResponseDto;

public interface TagService {
    TagResponseDto create(TagCreateDto dto);
}
