package az.stanok.stanokazback.controller.public_api;

import az.stanok.stanokazback.dto.ApiResponse;
import az.stanok.stanokazback.dto.post.PostResponseDto;
import az.stanok.stanokazback.dto.product.ProductResponseDto;
import az.stanok.stanokazback.service.PostService;
import az.stanok.stanokazback.service.ProductService;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("api/v1/public/product")
@RequiredArgsConstructor
public class ProductPublicController {

    private final ProductService service;

    @SneakyThrows
    @GetMapping
    public ApiResponse<Page<ProductResponseDto>> getAll(
            @ApiParam(value = "номер страницы", required = true)
            @RequestParam(value = "page", defaultValue = "0") int page,
            @ApiParam(value = "количество карточек информации на странице", required = true)
            @RequestParam(value = "size", defaultValue = "10") int pageSize
    ) {

        return ApiResponse.success(service.getAll(page, pageSize));
    }
    @SneakyThrows
    @GetMapping("/{slug}")
    public ApiResponse<ProductResponseDto> getBySlug(@PathVariable String slug) {

        return ApiResponse.success(service.getBySlug(slug));
    }
}
