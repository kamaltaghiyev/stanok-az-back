package az.stanok.stanokazback.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCreateDto implements Serializable {
    private String title;
    private String titleAz;
    private String titleRu;
    private String slug;
    private String description;
    private String descriptionAz;
    private String descriptionRu;
    @Nullable
    private List<Long> tagListIds;
    @Nullable
    private String youtubeVideo;
}
