package az.stanok.stanokazback.service;

import az.stanok.stanokazback.dto.post.PostCreateDto;
import az.stanok.stanokazback.dto.post.PostResponseDto;
import az.stanok.stanokazback.exceptions.core.DorogaException;
import az.stanok.stanokazback.mappers.PostMapper;
import az.stanok.stanokazback.models.Post;
import az.stanok.stanokazback.models.Tag;
import az.stanok.stanokazback.repo.PostRepo;
import az.stanok.stanokazback.repo.TagRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepo postRepo;
    private final PostMapper postMapper;
    private final TagRepo tagRepo;
    @Override
    public PostResponseDto create(PostCreateDto dto) {
        if (postRepo.findBySlug(dto.getSlug()).isPresent())
            throw new DorogaException("Post with this slug is already exists");

        Post post = postMapper.toEntity(dto);
        if (dto.getTagListIds() != null) {
            List<Tag> tags = new ArrayList<>();
            dto.getTagListIds().forEach(id -> {
                tags.add(tagRepo.getOne(id));
            });
            post.setTagsList(tags);
        }
        return postMapper.toDto(postRepo.save(post));
    }
}
