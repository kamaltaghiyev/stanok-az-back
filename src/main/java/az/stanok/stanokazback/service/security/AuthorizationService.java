package az.stanok.stanokazback.service.security;

import az.stanok.stanokazback.dto.auth.AuthorizationUserResponse;
import az.stanok.stanokazback.dto.auth.CreateUserRequest;
import az.stanok.stanokazback.dto.auth.UpdatePasswordRequest;
import az.stanok.stanokazback.exceptions.core.DorogaException;
import az.stanok.stanokazback.exceptions.core.NotFoundException;
import az.stanok.stanokazback.models.users.User;
import az.stanok.stanokazback.models.users.UserRole;
import az.stanok.stanokazback.repo.UserRepo;
import az.stanok.stanokazback.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final PasswordEncoder encoder;
    private final UserRepo userRepo;
    private final UserService userService;

    public AuthorizationUserResponse login(String phone, String password, HttpServletResponse response) {

        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(phone, password)
            );

            User user = (User) authenticate.getPrincipal();
            String authToken = jwtTokenService.generateAccessToken(authenticate);
            response.setHeader(HttpHeaders.AUTHORIZATION, authToken);

//            response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.AUTHORIZATION);


            return new AuthorizationUserResponse(user.getEmail(), authToken);

        } catch (BadCredentialsException exception) {
            throw new DorogaException("Введён неправильный логин или пароль!");

        }
    }

    public void createUser(CreateUserRequest request) throws DorogaException {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setAuthority(UserRole.ROLE_ADMIN);
        userService.createUser(user);
    }

    @Transactional
    public void updatePassword(UpdatePasswordRequest request) {
        Optional<User> optional = userRepo.findById(getCurrent().getId());
        User currentUser = optional.orElseThrow(() ->
                new NotFoundException(String.format("User not found = %d", getCurrent().getId())));

        if (!encoder.matches(request.getOldPassword(), currentUser.getPassword())) {
            throw new DorogaException("Old password is incorrect");
        }

        userRepo.updatePassword(currentUser.getId(), encoder.encode(request.getNewPassword()));
    }

    /**
     * Метод генерации пароля определенной длины
     *
     * @param length - длина пароля
     * @return сгенерированный пароль объект типа String (строка)
     */
    private String generatePassword(int length) {
        if (length < 6) {
            throw new IllegalArgumentException("Generated password should be at least 6 chars length");
        }

        // Допустимые символы
        final char[] lowercase = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        final char[] uppercase = "ABCDEFGJKLMNPRSTUVWXYZ".toCharArray();
        final char[] numbers = "0123456789".toCharArray();
        final char[] allAllowed = "abcdefghijklmnopqrstuvwxyzABCDEFGJKLMNPRSTUVWXYZ0123456789".toCharArray();

        // Использование криптографического генератора случайных чисел
        Random random = new SecureRandom();

        // Генерация стартового объекта
        StringBuilder password = new StringBuilder();

        // Конкатенация рандомного символа
        for (int i = 0; i < length - 4; i++) {
            password.append(allAllowed[random.nextInt(allAllowed.length)]);
        }

        // Убеждаемся, что политика паролей соблюдается, вставив необходимые случайные символы в случайные позиции.
        password.insert(random.nextInt(password.length()), lowercase[random.nextInt(lowercase.length)]);
        password.insert(random.nextInt(password.length()), uppercase[random.nextInt(uppercase.length)]);
        password.insert(random.nextInt(password.length()), numbers[random.nextInt(numbers.length)]);

        return password.toString();
    }

    public User getCurrent() {
        Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (o instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) o;
            return userRepo.findByEmail(userDetails.getUsername()).orElseThrow(() -> new NotFoundException(User.class, userDetails.getUsername()));
        } else if (o instanceof String) {
            String username = (String) o;
            log.warn("Expect User but received a string " + username);
            return userRepo.findByEmail(username).orElseThrow(() -> new NotFoundException(User.class, username));
        } else {
            log.error("Expect User but received any object " + o);
            throw new NotFoundException(AuthorizationService.class, String.valueOf(o));
        }
    }

}
