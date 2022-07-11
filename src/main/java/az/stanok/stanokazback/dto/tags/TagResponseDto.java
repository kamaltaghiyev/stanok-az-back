package az.stanok.stanokazback.dto.tags;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagResponseDto {
    private Long id;
    private String createdAt;
    private String title;
    private String titleAz;
    private String titleRu;

    private String slug;
}
