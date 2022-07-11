package az.stanok.stanokazback.mappers;

import az.stanok.stanokazback.dto.post.PostCreateDto;
import az.stanok.stanokazback.dto.post.PostResponseDto;
import az.stanok.stanokazback.models.Post;
import az.stanok.stanokazback.repo.TagRepo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {CommonMapper.class, PostMapper.class, Collectors.class, DateTimeFormatter.class}, imports = { Collectors.class, DateTimeFormatter.class})
public abstract class PostMapper {
    @Autowired
    protected TagRepo tagRepo;
    @Autowired
    protected TagMapper tagMapper;

    @Mapping(target = "createdAt", expression = "java(entity.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))")
    @Mapping(target = "tagDtos", expression = "java(entity.getTagsList() != null ? entity.getTagsList().stream().map(tagMapper::toDto).collect(Collectors.toList()) : null)")
    @Mapping(target = "buysCount", source = "entity.buysCount")
    public abstract PostResponseDto toDto(Post entity);

    @Mapping(target = "tagsList", expression = "java(dto.getTagListIds() != null ? tagRepo.findAllById(dto.getTagListIds()) : null)")
    public abstract Post toEntity(PostCreateDto dto);

}
