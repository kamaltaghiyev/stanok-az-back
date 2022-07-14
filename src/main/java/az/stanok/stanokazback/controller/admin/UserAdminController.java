package az.stanok.stanokazback.controller.admin;

import az.stanok.stanokazback.dto.auth.UserDto;
import az.stanok.stanokazback.exceptions.core.ResourceNotFoundException;
import az.stanok.stanokazback.models.users.User;
import az.stanok.stanokazback.repo.UserRepo;
import az.stanok.stanokazback.security.CurrentUser;
import az.stanok.stanokazback.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAdminController {
    @Autowired
    private UserRepo userRepository;

    @GetMapping("/api/v1/user/me")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public UserDto getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}
