package az.stanok.stanokazback.mappers;

import az.stanok.stanokazback.dto.product.ProductCreateDto;
import az.stanok.stanokazback.dto.product.ProductResponseDto;
import az.stanok.stanokazback.models.Product;
import az.stanok.stanokazback.repo.TagRepo;
import az.stanok.stanokazback.service.ImageService;
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
    @Autowired
    protected ImageService imageService;

    @Mapping(target = "createdAt", expression = "java(entity.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))")
    @Mapping(target = "tagDtos", expression = "java(entity.getTagsList() != null ? entity.getTagsList().stream().map(tagMapper::toDto).collect(Collectors.toList()) : null)")
    @Mapping(target = "imageList", expression = "java(imageService.getAllProductImageLinks(entity))")
    @Mapping(target = "propertiesAz", source = "entity.propertiesAz")
    @Mapping(target = "propertiesRu", source = "entity.propertiesRu")
    @Mapping(target = "properties", source = "entity.properties")
    @Mapping(target = "priceAz", source = "entity.priceAz")
    @Mapping(target = "priceRu", source = "entity.priceRu")
    @Mapping(target = "price", source = "entity.price")
    public abstract ProductResponseDto toDto(Product entity);

    @Mapping(target = "tagsList", expression = "java(dto.getTagListIds() != null ? tagRepo.findAllById(dto.getTagListIds()) : null)")
    @Mapping(target = "propertiesAz", source = "dto.propertiesAz")
    @Mapping(target = "propertiesRu", source = "dto.propertiesRu")
    @Mapping(target = "properties", source = "dto.properties")
    @Mapping(target = "priceAz", source = "dto.priceAz")
    @Mapping(target = "priceRu", source = "dto.priceRu")
    @Mapping(target = "price", source = "dto.price")
    public abstract Product toEntity(ProductCreateDto dto);
}
