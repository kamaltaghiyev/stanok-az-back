package az.stanok.stanokazback.dto.product;

import az.stanok.stanokazback.dto.tags.TagResponseDto;
import az.stanok.stanokazback.models.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto implements Serializable {
    private Long id;
    private String title;
    private String titleAz;
    private String titleRu;
    private String slug;
    private String properties;
    private String propertiesAz;
    private String propertiesRu;
    private String description;
    private String descriptionAz;
    private String descriptionRu;
    private String price;
    private String priceAz;
    private String priceRu;
    private Integer likeCount;
    private Integer reviewCount;
    private Integer buysCount;
    private String createdAt;
    private List<TagResponseDto> tagDtos;
    private List<ImageDto> imageList;
    private String youtubeVideo;
}
