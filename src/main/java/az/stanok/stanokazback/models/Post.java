package az.stanok.stanokazback.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class Post extends BaseEntity {

    @Column(name = "title", length = 10240)
    private String title;
    @Column(name = "title_az", length = 10240)
    private String titleAz;
    @Column(name = "title_ru", length = 10240)
    private String titleRu;
    @Column(name = "slug", length = 1024, unique = true)
    private String slug;
    @Column(name = "properties", length = 10240)
    private String properties;
    @Column(name = "description", length = 10240)
    private String description;
    @Column(name = "description_az", length = 10240)
    private String descriptionAz;
    @Column(name = "description_ru", length = 10240)
    private String descriptionRu;
    @Column(name = "like_count")
    private Integer likeCount = 0;
    @Column(name = "review_count")
    private Integer reviewCount = 0;
    @Column(name = "youtube_video", length = 1024)
    private String youtubeVideo;
    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinTable(name = "posts_tags_relation",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    private List<Tag> tagsList;
}
