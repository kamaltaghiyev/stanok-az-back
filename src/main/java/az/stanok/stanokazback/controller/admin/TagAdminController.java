package az.stanok.stanokazback.controller.admin;

import az.stanok.stanokazback.dto.ApiResponse;
import az.stanok.stanokazback.dto.tags.TagCreateDto;
import az.stanok.stanokazback.dto.tags.TagResponseDto;
import az.stanok.stanokazback.service.TagService;
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
@RequestMapping("api/v1/admin/tag")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @SneakyThrows
    @PostMapping
    public ApiResponse<TagResponseDto> create(@RequestBody TagCreateDto request) {

        return ApiResponse.success(tagService.create(request));
    }
    @SneakyThrows
    @PutMapping("/{id}")
    public ApiResponse<TagResponseDto> update(@PathVariable Long id, @RequestBody TagCreateDto request) {

        return ApiResponse.success(tagService.update(id, request));
    }
    @SneakyThrows
    @GetMapping
    public ApiResponse<Page<TagResponseDto>> getAll(
            @ApiParam(value = "номер страницы", required = true)
            @RequestParam(value = "page", defaultValue = "0") int page,
            @ApiParam(value = "количество карточек информации на странице", required = true)
            @RequestParam(value = "size", defaultValue = "10") int pageSize
    ) {

        return ApiResponse.success(tagService.getAll(page, pageSize));
    }
    @DeleteMapping("/{id}")
    @ApiOperation("Delete posts from list ids")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        tagService.delete(id);
        return ApiResponse.success();
    }
    @GetMapping("/{id}")
    public ApiResponse<TagResponseDto> getById(@PathVariable Long id) {
        return ApiResponse.success(tagService.getById(id));
    }
}
