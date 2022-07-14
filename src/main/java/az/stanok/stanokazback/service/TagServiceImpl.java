package az.stanok.stanokazback.service;

import az.stanok.stanokazback.dto.tags.TagCreateDto;
import az.stanok.stanokazback.dto.tags.TagResponseDto;
import az.stanok.stanokazback.exceptions.core.DorogaException;
import az.stanok.stanokazback.exceptions.core.NotFoundException;
import az.stanok.stanokazback.mappers.TagMapper;
import az.stanok.stanokazback.models.Tag;
import az.stanok.stanokazback.repo.TagRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepo tagRepo;
    private final TagMapper tagMapper;

    @Override
    public TagResponseDto create(TagCreateDto dto) {
        if (tagRepo.findBySlug(dto.getSlug()).isPresent())
            throw new DorogaException("Tag with this slug is already exists");

        Tag tag = tagMapper.toEntity(dto);
        return tagMapper.toDto(tagRepo.save(tag));
    }

    @Override
    public TagResponseDto update(Long id, TagCreateDto updateDto) {
        Tag existed = tagRepo.findById(id).orElseThrow(() -> new NotFoundException(Tag.class, id));
        if (updateDto.getTitle() != null)
            existed.setTitle(updateDto.getTitle());
        if (updateDto.getTitleAz() != null)
            existed.setTitleAz(updateDto.getTitleAz());
        if (updateDto.getTitleRu() != null)
            existed.setTitleAz(updateDto.getTitleRu());
        if (updateDto.getSlug() != null)
            existed.setSlug(updateDto.getSlug());

        return tagMapper.toDto(tagRepo.save(existed));
    }

    @Override
    public void delete(Long id) {
        tagRepo.deleteById(id);
    }

    @Override
    public Page<TagResponseDto> getAll(int pageNum, int limit) {
        Page<Tag> page = tagRepo.findAll(PageRequest.of(pageNum, limit, Sort.by("createdAt").descending()));
        return page.map(tagMapper::toDto);
    }

    @Override
    public TagResponseDto getById(Long id) {
        Tag tag = tagRepo.findById(id).orElseThrow(() -> new NotFoundException(Tag.class, id));
        return tagMapper.toDto(tag);
    }
}
