package com.own.moviebooking2;

import com.own.moviebooking2.entity.User;
import com.own.moviebooking2.entity.UserRole;
import com.own.moviebooking2.enums.Role;
import com.own.moviebooking2.repository.UserRepository;
import com.own.moviebooking2.repository.UserRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootApplication
@AllArgsConstructor
public class MovieBooking2Application implements CommandLineRunner {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserRoleRepository userRoleRepository;


    public static void main(String[] args) {
        SpringApplication.run(MovieBooking2Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("dattle99").isPresent() && userRepository.findByUsername("dattle329").isPresent()) {
            return;
        }

        Map<Role, UserRole> roles = Arrays.stream(Role.values())
                .collect(Collectors.toMap(
                        role -> role,
                        role -> userRoleRepository.findByRole(role).orElseGet(() -> userRoleRepository.save(new UserRole(role)))
                ));

        Set<UserRole> userRoleSet = Set.of(roles.get(Role.USER_ROLE));
        Set<UserRole> hrRoleSet = Set.of(roles.get(Role.USER_ROLE), roles.get(Role.HR_ROLE));
        Set<UserRole> adminRoleSet = Set.of(roles.get(Role.USER_ROLE), roles.get(Role.HR_ROLE), roles.get(Role.ADMIN_ROLE));

        List<User> users = List.of(
                new User("dattle99", passwordEncoder.encode("Ledat999@"), userRoleSet),
                new User("lanle99", passwordEncoder.encode("Ledat999@"), hrRoleSet),
                new User("dattle329", passwordEncoder.encode("Ledat999@"), adminRoleSet)
        );

        userRepository.saveAll(users);
    }
}
