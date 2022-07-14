package az.stanok.stanokazback.dto.tags;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagResponseDto  implements Serializable {
    private Long id;
    private String createdAt;
    private String title;
    private String titleAz;
    private String titleRu;

    private String slug;
}
