package az.stanok.stanokazback.controller.public_api;

import az.stanok.stanokazback.dto.ApiResponse;
import az.stanok.stanokazback.dto.tags.TagResponseDto;
import az.stanok.stanokazback.service.TagService;
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
@RequestMapping("api/v1/public/tag")
@RequiredArgsConstructor
public class TagPublicController {

    private final TagService tagService;

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
    @GetMapping("/{id}")
    public ApiResponse<TagResponseDto> getById(@PathVariable Long id) {
        return ApiResponse.success(tagService.getById(id));
    }
}
