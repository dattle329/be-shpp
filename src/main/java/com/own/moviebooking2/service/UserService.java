package com.own.moviebooking2.service;

import com.own.moviebooking2.config.jwt.JwtService;
import com.own.moviebooking2.dto.request.UserLoginRequest;
import com.own.moviebooking2.dto.response.UserLoginResponse;
import com.own.moviebooking2.entity.MyUserPrincipal;
import com.own.moviebooking2.entity.User;
import com.own.moviebooking2.repository.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    public UserService(UserRepository userRepository, @Lazy AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public UserLoginResponse login(UserLoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User authenticatedUser = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow();
        String jwtToken = jwtService.generateToken(new MyUserPrincipal(authenticatedUser));
        String refreshToken = jwtService.generateRefreshToken(new MyUserPrincipal(authenticatedUser));

        MyUserPrincipal userDetails = (MyUserPrincipal) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return getUserLoginResponse(loginRequest, jwtToken, refreshToken, roles);
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
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new MyUserPrincipal(user.get());
    }
}
