package az.stanok.stanokazback.repo;

import az.stanok.stanokazback.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);


    @Modifying
    @Query("update User u " +
            "set u.password = :newPassword " +
            "where u.id = :userId")
    void updatePassword(long userId, String newPassword);


}
