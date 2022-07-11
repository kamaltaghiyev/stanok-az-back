package az.stanok.stanokazback.controller.admin;

import az.stanok.stanokazback.dto.ApiResponse;
import az.stanok.stanokazback.dto.tags.TagCreateDto;
import az.stanok.stanokazback.dto.tags.TagResponseDto;
import az.stanok.stanokazback.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("api/v1/admin/tag")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @SneakyThrows
    @PostMapping
    public ApiResponse<TagResponseDto> login(@RequestBody TagCreateDto request) {

        return ApiResponse.success(tagService.create(request));
    }

}
