package az.stanok.stanokazback.service;

import az.stanok.stanokazback.dto.product.ProductCreateDto;
import az.stanok.stanokazback.dto.product.ProductResponseDto;
import org.springframework.data.domain.Page;

public interface ProductService {
    ProductResponseDto create(ProductCreateDto createDto);
    ProductResponseDto update(Long id, ProductCreateDto updateDto);
    void delete(Long id);
    Page<ProductResponseDto> getAll(int pageNum, int limit);
    ProductResponseDto getBySlug(String slug);
    ProductResponseDto getById(Long id);
}
