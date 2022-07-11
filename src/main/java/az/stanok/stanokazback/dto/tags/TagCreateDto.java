package az.stanok.stanokazback.dto.tags;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagCreateDto implements Serializable {
    private String title;
    private String titleAz;
    private String titleRu;

    private String slug;
}
