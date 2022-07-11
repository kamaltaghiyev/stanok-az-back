package az.stanok.stanokazback.repo;

import az.stanok.stanokazback.models.Post;
import az.stanok.stanokazback.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {
    Optional<Post> findBySlug(String slug);
}
