package az.stanok.stanokazback.dto.post;

import az.stanok.stanokazback.dto.tags.TagResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDto {
    private Long id;
    private String title;
    private String titleAz;
    private String titleRu;
    private String slug;
    private String description;
    private String descriptionAz;
    private String descriptionRu;
    private Integer likeCount;
    private Integer reviewCount;
    private Integer buysCount;
    private String createdAt;
    private List<TagResponseDto> tagDtos;
}
