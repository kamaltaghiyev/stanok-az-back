package az.stanok.stanokazback.service;

import az.stanok.stanokazback.exceptions.core.DorogaException;
import az.stanok.stanokazback.exceptions.core.NotFoundException;
import az.stanok.stanokazback.models.users.User;
import az.stanok.stanokazback.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;


    public User findById(long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(User.class, id));
    }

    public boolean existsById(long id) {
        return userRepo.existsById(id);
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }


    public User getUserEntityById(long id) {
        return userRepo.findById(id).orElseThrow(() -> new NotFoundException(User.class, id));
    }


    public User getUserEntity(String email) {
        return Objects.requireNonNull(userRepo.findByEmail(email).orElseThrow(() -> new NotFoundException(User.class, email)));
    }


    public User createUser(User user) {
        if (!userRepo.existsByEmail(user.getEmail())) {
            User saved = userRepo.save(user);
            return saved;
        }

        throw new DorogaException("Data already used");
    }
}