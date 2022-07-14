package az.stanok.stanokazback.service;

import az.stanok.stanokazback.dto.tags.TagCreateDto;
import az.stanok.stanokazback.dto.tags.TagResponseDto;
import org.springframework.data.domain.Page;

public interface TagService {
    TagResponseDto create(TagCreateDto dto);
    TagResponseDto update(Long id, TagCreateDto updateDto);
    void delete(Long id);
    Page<TagResponseDto> getAll(int pageNum, int limit);
    TagResponseDto getById(Long id);
}
