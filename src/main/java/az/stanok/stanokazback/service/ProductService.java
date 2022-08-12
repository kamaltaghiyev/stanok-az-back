package az.stanok.stanokazback.service;

import az.stanok.stanokazback.dto.product.ProductCreateDto;
import az.stanok.stanokazback.dto.product.ProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    ProductResponseDto create(ProductCreateDto createDto);

    ProductResponseDto update(Long id, ProductCreateDto updateDto);

    void delete(Long id);

    Page<ProductResponseDto> getAll(int pageNum, int limit);

    ProductResponseDto getBySlug(String slug);

    ProductResponseDto getById(Long id);

    ProductResponseDto uploadPostImage(Long id, List<MultipartFile> files);

    void deletePostImage(Long id);
}
