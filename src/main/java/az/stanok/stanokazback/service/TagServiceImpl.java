package az.stanok.stanokazback.service;

import az.stanok.stanokazback.dto.tags.TagCreateDto;
import az.stanok.stanokazback.dto.tags.TagResponseDto;
import az.stanok.stanokazback.exceptions.core.DorogaException;
import az.stanok.stanokazback.mappers.TagMapper;
import az.stanok.stanokazback.models.Tag;
import az.stanok.stanokazback.repo.TagRepo;
import lombok.RequiredArgsConstructor;
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
}
