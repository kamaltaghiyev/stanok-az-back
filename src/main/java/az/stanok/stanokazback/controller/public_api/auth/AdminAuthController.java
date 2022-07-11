package az.stanok.stanokazback.controller.public_api.auth;

import az.stanok.stanokazback.dto.ApiResponse;
import az.stanok.stanokazback.dto.auth.AuthRequest;
import az.stanok.stanokazback.dto.auth.AuthorizationUserResponse;
import az.stanok.stanokazback.dto.auth.CreateUserRequest;
import az.stanok.stanokazback.service.security.AuthorizationService;
import az.stanok.stanokazback.service.security.JwtTokenService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("api/v1/public/auth")
@RequiredArgsConstructor
public class AdminAuthController {

    private final AuthorizationService authorizationService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService tokenProvider;
//    @PostMapping("/login")
//    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        loginRequest.getEmail(),
//                        loginRequest.getPassword()
//                )
//        );
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        String token = tokenProvider.createToken(authentication);
//        return ResponseEntity.ok(new AuthResponse(token));
//    }

//    @PostMapping("/signup")
//    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
//        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
//            throw new BadRequestException("Email address already in use.");
//        }
//
//        // Creating user's account
//        User user = new User();
//        user.setName(signUpRequest.getName());
//        user.setEmail(signUpRequest.getEmail());
//        user.setPassword(signUpRequest.getPassword());
//        user.setProvider(AuthProvider.local);
//
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//
//        User result = userRepository.save(user);
//
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentContextPath().path("/user/me")
//                .buildAndExpand(result.getId()).toUri();
//
//        return ResponseEntity.created(location)
//                .body(new ApiResponse(true, "User registered successfully@"));
//    }

    @SneakyThrows
    @PostMapping("/login")
    public ApiResponse<AuthorizationUserResponse> login(@RequestBody @Valid AuthRequest request,
                                                        HttpServletResponse response) throws BadCredentialsException {


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.generateAccessToken(authentication);

        return ApiResponse.success(new AuthorizationUserResponse(request.getEmail(), token));
    }

    @SneakyThrows
    @PostMapping("/register")
    public ApiResponse<Void> register(@Valid @RequestBody CreateUserRequest request) {
        authorizationService.createUser(request);
        return ApiResponse.success();
    }
}
