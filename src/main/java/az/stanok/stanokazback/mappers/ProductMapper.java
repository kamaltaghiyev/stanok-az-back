package az.stanok.stanokazback.mappers;

import az.stanok.stanokazback.dto.product.ProductCreateDto;
import az.stanok.stanokazback.dto.product.ProductResponseDto;
import az.stanok.stanokazback.models.Product;
import az.stanok.stanokazback.repo.TagRepo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {CommonMapper.class, PostMapper.class, Collectors.class, DateTimeFormatter.class}, imports = { Collectors.class, DateTimeFormatter.class})
public abstract class ProductMapper {
    @Autowired
    protected TagRepo tagRepo;
    @Autowired
    protected TagMapper tagMapper;

    @Mapping(target = "createdAt", expression = "java(entity.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))")
    @Mapping(target = "tagDtos", expression = "java(entity.getTagsList() != null ? entity.getTagsList().stream().map(tagMapper::toDto).collect(Collectors.toList()) : null)")
    public abstract ProductResponseDto toDto(Product entity);

    @Mapping(target = "tagsList", expression = "java(dto.getTagListIds() != null ? tagRepo.findAllById(dto.getTagListIds()) : null)")
    public abstract Product toEntity(ProductCreateDto dto);
}
