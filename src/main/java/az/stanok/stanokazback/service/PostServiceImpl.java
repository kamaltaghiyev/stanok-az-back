package az.stanok.stanokazback.service;

import az.stanok.stanokazback.dto.post.PostCreateDto;
import az.stanok.stanokazback.dto.post.PostResponseDto;
import az.stanok.stanokazback.exceptions.core.DorogaException;
import az.stanok.stanokazback.exceptions.core.NotFoundException;
import az.stanok.stanokazback.mappers.PostMapper;
import az.stanok.stanokazback.models.Post;
import az.stanok.stanokazback.models.Tag;
import az.stanok.stanokazback.repo.PostRepo;
import az.stanok.stanokazback.repo.TagRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepo postRepo;
    private final PostMapper postMapper;
    private final TagRepo tagRepo;
    private final ImageService imageServise;

    @Override
    public PostResponseDto create(PostCreateDto dto) {
        if (postRepo.findBySlug(dto.getSlug()).isPresent())
            throw new DorogaException("Post with this slug is already exists");

        Post post = postMapper.toEntity(dto);
        post = fromTagsIdsToEntity(dto.getTagListIds(), post);

        return postMapper.toDto(postRepo.save(post));
    }

    @Override
    public PostResponseDto update(Long id, PostCreateDto updateDto) {
        Post existed = postRepo.findById(id).orElseThrow(() -> new NotFoundException(Post.class, id));
        if (updateDto.getTitle() != null)
            existed.setTitle(updateDto.getTitle());
        if (updateDto.getTitleAz() != null)
            existed.setTitleAz(updateDto.getTitleAz());
        if (updateDto.getTitleRu() != null)
            existed.setTitleAz(updateDto.getTitleRu());
        if (updateDto.getSlug() != null)
            existed.setSlug(updateDto.getSlug());
        if (updateDto.getDescription() != null)
            existed.setDescription(updateDto.getDescription());
        if (updateDto.getDescriptionAz() != null)
            existed.setDescriptionAz(updateDto.getDescriptionAz());
        if (updateDto.getDescriptionRu() != null)
            existed.setDescriptionRu(updateDto.getDescriptionRu());
        if (updateDto.getYoutubeVideo() != null)
            existed.setYoutubeVideo(updateDto.getYoutubeVideo());

        existed = fromTagsIdsToEntity(updateDto.getTagListIds(), existed);

        return postMapper.toDto(postRepo.save(existed));
    }

    @Override
    public void delete(Long id) {
        postRepo.deleteById(id);
    }

    @Override
    public Page<PostResponseDto> getAll(int pageNum, int limit) {
        Page<Post> page = postRepo.findAll(PageRequest.of(pageNum, limit, Sort.by("createdAt").descending()));
        return page.map(postMapper::toDto);
    }

    @Override
    public PostResponseDto getBySlug(String slug) {
        Post post = postRepo.findBySlug(slug).orElseThrow(() -> new NotFoundException(Post.class, slug));
        return postMapper.toDto(post);
    }

    @Override
    public PostResponseDto getById(Long id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new NotFoundException(Post.class, id));
        return postMapper.toDto(post);
    }

    @Override
    public PostResponseDto uploadPostImage(Long id, List<MultipartFile> files) {
        Post post = postRepo.getById(id);
        for(MultipartFile file : files) {
            imageServise.uploadPostImage(file, post);
        }

        PostResponseDto postResponseDto = postMapper.toDto(postRepo.save(post));
        postResponseDto.setImageList(imageServise.getAllPostImageLinks(post));
        return postResponseDto;
    }

    private Post fromTagsIdsToEntity(List<Long> ids, Post entity) {
        if (ids != null) {
            List<Tag> tags = new ArrayList<>();
            ids.forEach(tagId -> {
                tags.add(tagRepo.getOne(tagId));
            });
            entity.setTagsList(tags);
        }
        return entity;
    }
}
