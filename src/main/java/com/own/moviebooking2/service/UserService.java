package com.own.moviebooking2.service;

import com.own.moviebooking2.config.jwt.JwtService;
import com.own.moviebooking2.dto.request.UserLoginRequest;
import com.own.moviebooking2.dto.response.CreateTokenAgainResponse;
import com.own.moviebooking2.dto.response.UserLoginResponse;
import com.own.moviebooking2.entity.MyUserPrincipal;
import com.own.moviebooking2.entity.User;
import com.own.moviebooking2.enums.Role;
import com.own.moviebooking2.exception.CommonException;
import com.own.moviebooking2.repository.UserRepository;
import com.own.moviebooking2.repository.UserRoleRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository, @Lazy AuthenticationManager authenticationManager, @Lazy JwtService jwtService, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public String signUp(UserLoginRequest userLoginRequest) {
        userRepository.findByUsername(userLoginRequest.getUsername()).ifPresent(user -> {
            throw new CommonException("user already exists");
        });
        User user = new User();
        user.setUsername(userLoginRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userLoginRequest.getPassword()));
        userRoleRepository.findByRole(Role.USER_ROLE).ifPresent(role ->
                user.setRoles(Set.of(role))
        );

        userRepository.save(user);
        return "User registered successfully";
    }

    public UserLoginResponse login(UserLoginRequest loginRequest) {
        try {Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            User authenticatedUser = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow();
            String accessToken = jwtService.generateToken(new MyUserPrincipal(authenticatedUser));
            String refreshToken = jwtService.generateRefreshToken(new MyUserPrincipal(authenticatedUser));

            MyUserPrincipal userDetails = (MyUserPrincipal) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            return getUserLoginResponse(loginRequest, accessToken, refreshToken, roles);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommonException("Invalid username or password");
        }
    }

    public CreateTokenAgainResponse refreshAccessToken(HttpServletRequest request,
                                                       HttpServletResponse response) {
        String refreshToken = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        String username = jwtService.extractUsername(refreshToken);
        UserDetails userDetails = this.loadUserByUsername(username);
        if (jwtService.isRefreshTokenValid(refreshToken, userDetails)) {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
            String newAccessToken = jwtService.generateToken(userDetails);
            return new CreateTokenAgainResponse(newAccessToken, refreshToken);
        }
        return null;
    }

    private UserLoginResponse getUserLoginResponse(UserLoginRequest loginRequest, String jwtToken, String refreshToken, List<String> roles) {
        long expiredTime = jwtService.getExpirationTime();
        return UserLoginResponse.builder()
                .username(loginRequest.getUsername())
                .roles(roles)
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .expiresIn(expiredTime < 60000 ? TimeUnit.MILLISECONDS.toSeconds(expiredTime) + "second" : TimeUnit.MILLISECONDS.toMinutes(expiredTime) + "minutes")
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return new MyUserPrincipal(user.get());
    }

    public String getUserFromToken(HttpServletRequest request) {
        String token = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("accessToken")) {
                    token = cookie.getValue();
                }
            }
        }
        String username = jwtService.extractUsername(token);
        return "hello " + username;
    }
}
