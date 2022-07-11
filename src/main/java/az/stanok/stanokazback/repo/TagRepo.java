package az.stanok.stanokazback.repo;

import az.stanok.stanokazback.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepo extends JpaRepository<Tag, Long> {
    Optional<Tag> findBySlug(String slug);
}
