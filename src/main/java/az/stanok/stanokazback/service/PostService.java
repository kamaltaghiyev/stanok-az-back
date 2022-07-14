package az.stanok.stanokazback.service;

import az.stanok.stanokazback.dto.post.PostCreateDto;
import az.stanok.stanokazback.dto.post.PostResponseDto;
import org.springframework.data.domain.Page;

public interface PostService {
    PostResponseDto create(PostCreateDto createDto);
    PostResponseDto update(Long id, PostCreateDto updateDto);
    void delete(Long id);
    Page<PostResponseDto> getAll(int pageNum, int limit);
    PostResponseDto getBySlug(String slug);
    PostResponseDto getById(Long id);
}
