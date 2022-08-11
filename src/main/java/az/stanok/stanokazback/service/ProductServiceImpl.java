package az.stanok.stanokazback.service;

import az.stanok.stanokazback.dto.post.PostResponseDto;
import az.stanok.stanokazback.dto.product.ProductCreateDto;
import az.stanok.stanokazback.dto.product.ProductResponseDto;
import az.stanok.stanokazback.exceptions.core.DorogaException;
import az.stanok.stanokazback.exceptions.core.NotFoundException;
import az.stanok.stanokazback.mappers.ProductMapper;
import az.stanok.stanokazback.models.Post;
import az.stanok.stanokazback.models.Product;
import az.stanok.stanokazback.models.Tag;
import az.stanok.stanokazback.repo.ProductRepo;
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
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;
    private final ProductMapper productMapper;
    private final TagRepo tagRepo;
    private final ImageService imageServise;

    @Override
    public ProductResponseDto create(ProductCreateDto createDto) {
        if (productRepo.findBySlug(createDto.getSlug()).isPresent())
            throw new DorogaException("Post with this slug is already exists");

        Product post = productMapper.toEntity(createDto);
        post = fromTagsIdsToEntity(createDto.getTagListIds(), post);
        return productMapper.toDto(productRepo.save(post));
    }

    @Override
    public ProductResponseDto update(Long id, ProductCreateDto updateDto) {
        Product existed = productRepo.findById(id).orElseThrow(() -> new NotFoundException(Product.class, id));
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

        if (updateDto.getProperties() != null)
            existed.setProperties(updateDto.getProperties());
        if (updateDto.getPropertiesAz() != null)
            existed.setPropertiesAz(updateDto.getPropertiesAz());
        if (updateDto.getPropertiesRu() != null)
            existed.setPropertiesRu(updateDto.getPropertiesRu());

        if (updateDto.getPrice() != null)
            existed.setPrice(updateDto.getPrice());
        if (updateDto.getPriceAz() != null)
            existed.setPriceAz(updateDto.getPriceAz());
        if (updateDto.getPriceRu() != null)
            existed.setPriceRu(updateDto.getPriceRu());

        existed = fromTagsIdsToEntity(updateDto.getTagListIds(), existed);

        return productMapper.toDto(productRepo.save(existed));
    }

    @Override
    public void delete(Long id) {
        productRepo.deleteById(id);
    }

    @Override
    public Page<ProductResponseDto> getAll(int pageNum, int limit) {
        Page<Product> page = productRepo.findAll(PageRequest.of(pageNum, limit, Sort.by("createdAt").descending()));
        return page.map(productMapper::toDto);
    }

    @Override
    public ProductResponseDto getBySlug(String slug) {
        Product post = productRepo.findBySlug(slug).orElseThrow(() -> new NotFoundException(Product.class, slug));
        return productMapper.toDto(post);
    }

    @Override
    public ProductResponseDto getById(Long id) {
        Product post = productRepo.findById(id).orElseThrow(() -> new NotFoundException(Product.class, id));
        return productMapper.toDto(post);
    }

    @Override
    public ProductResponseDto uploadPostImage(Long id, List<MultipartFile> files) {
        Product post = productRepo.getById(id);
        for(MultipartFile file : files) {
            imageServise.uploadProductImage(file, post);
        }

        ProductResponseDto postResponseDto = productMapper.toDto(productRepo.save(post));
        postResponseDto.setImageList(imageServise.getAllProductImageLinks(post));
        return postResponseDto;
    }

    private Product fromTagsIdsToEntity(List<Long> ids, Product entity) {
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
