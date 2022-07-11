package az.stanok.stanokazback.mappers;

import az.stanok.stanokazback.dto.tags.TagCreateDto;
import az.stanok.stanokazback.dto.tags.TagResponseDto;
import az.stanok.stanokazback.models.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {CommonMapper.class, TagMapper.class, Collectors.class, DateTimeFormatter.class}, imports = { Collectors.class, DateTimeFormatter.class})
public abstract class TagMapper {
    @Mapping(target = "createdAt", expression = "java(entity.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))")
    public abstract TagResponseDto toDto(Tag entity);

    public abstract Tag toEntity(TagCreateDto dto);

}
