package az.stanok.stanokazback.repo;

import az.stanok.stanokazback.models.Image;
import az.stanok.stanokazback.models.Post;
import az.stanok.stanokazback.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepo extends JpaRepository<Image, Long> {
    List<Image> findAllByPost(Post post);
    List<Image> findAllByProduct(Product product);
}
