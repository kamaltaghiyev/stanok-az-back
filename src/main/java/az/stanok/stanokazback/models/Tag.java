package az.stanok.stanokazback.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tags")
public class Tag extends BaseEntity{
    @Column(name = "title", length = 10240)
    private String title;
    @Column(name = "titleAz", length = 10240)
    private String titleAz;
    @Column(name = "titleRu", length = 10240)
    private String titleRu;

    @Column(name = "slug", length = 10240, unique = true)
    private String slug;
}
