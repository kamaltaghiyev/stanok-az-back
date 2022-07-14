package az.stanok.stanokazback.controller.admin;

import az.stanok.stanokazback.dto.ApiResponse;
import az.stanok.stanokazback.dto.post.PostCreateDto;
import az.stanok.stanokazback.dto.post.PostResponseDto;
import az.stanok.stanokazback.dto.product.ProductCreateDto;
import az.stanok.stanokazback.dto.product.ProductResponseDto;
import az.stanok.stanokazback.service.PostService;
import az.stanok.stanokazback.service.ProductService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("api/v1/admin/product")
@RequiredArgsConstructor
public class ProductAdminController {
    private final ProductService postService;

    @SneakyThrows
    @PostMapping
    public ApiResponse<ProductResponseDto> create(@RequestBody ProductCreateDto request) {

        return ApiResponse.success(postService.create(request));
    }
    @SneakyThrows
    @PutMapping("/{id}")
    public ApiResponse<ProductResponseDto> update(@PathVariable Long id, @RequestBody ProductCreateDto request) {

        return ApiResponse.success(postService.update(id, request));
    }
    @SneakyThrows
    @GetMapping
    public ApiResponse<Page<ProductResponseDto>> getAll(
            @ApiParam(value = "номер страницы", required = true)
            @RequestParam(value = "page", defaultValue = "0") int page,
            @ApiParam(value = "количество карточек информации на странице", required = true)
            @RequestParam(value = "size", defaultValue = "10") int pageSize
    ) {

        return ApiResponse.success(postService.getAll(page, pageSize));
    }
    @DeleteMapping("/{id}")
    @ApiOperation("Delete posts from list ids")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        postService.delete(id);
        return ApiResponse.success();
    }
    @GetMapping("/{id}")
    public ApiResponse<ProductResponseDto> getById(@PathVariable Long id) {
        return ApiResponse.success(postService.getById(id));
    }
}
