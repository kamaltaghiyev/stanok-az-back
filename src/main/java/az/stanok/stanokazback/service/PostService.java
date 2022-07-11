package az.stanok.stanokazback.service;

import az.stanok.stanokazback.dto.post.PostCreateDto;
import az.stanok.stanokazback.dto.post.PostResponseDto;

public interface PostService {
    PostResponseDto create(PostCreateDto createDto);
}
