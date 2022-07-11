package az.stanok.stanokazback.controller.admin;

import az.stanok.stanokazback.dto.ApiResponse;
import az.stanok.stanokazback.dto.post.PostCreateDto;
import az.stanok.stanokazback.dto.post.PostResponseDto;
import az.stanok.stanokazback.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("api/v1/admin/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @SneakyThrows
    @PostMapping
    public ApiResponse<PostResponseDto> create(@RequestBody PostCreateDto request) {

        return ApiResponse.success(postService.create(request));
    }
}
