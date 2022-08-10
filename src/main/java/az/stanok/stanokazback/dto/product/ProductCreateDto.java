package az.stanok.stanokazback.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateDto implements Serializable {
    private String title;
    private String titleAz;
    private String titleRu;
    private String slug;
    private String description;
    private String descriptionAz;
    private String descriptionRu;
    private String price;
    private String priceAz;
    private String priceRu;
    private String properties;
    private String propertiesAz;
    private String propertiesRu;
    @Nullable
    private List<Long> tagListIds;
}
