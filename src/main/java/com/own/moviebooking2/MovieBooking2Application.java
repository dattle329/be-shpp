package com.own.moviebooking2;

import com.own.moviebooking2.entity.User;
import com.own.moviebooking2.entity.UserRole;
import com.own.moviebooking2.enums.Role;
import com.own.moviebooking2.repository.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
@AllArgsConstructor
public class MovieBooking2Application implements CommandLineRunner {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;



    public static void main(String[] args) {
        SpringApplication.run(MovieBooking2Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("dattle99").isPresent() && userRepository.findByUsername("dattle329").isPresent()) {
            return;
        }

        Set<UserRole> userRoleSet = new HashSet<>();
        userRoleSet.add(new UserRole(Role.USER_ROLE));

        Set<UserRole> adminRoleSet = new HashSet<>();
        adminRoleSet.add(new UserRole(Role.ADMIN_ROLE));
        adminRoleSet.add(new UserRole(Role.USER_ROLE));

        User user = new User();
        user.setUsername("dattle99");
        user.setPassword(passwordEncoder.encode("Ledat999@"));
        user.setRoles(userRoleSet);

        User admin = new User();
        admin.setUsername("dattle329");
        admin.setPassword(passwordEncoder.encode("Ledat999@"));
        admin.setRoles(adminRoleSet);

        userRepository.save(user);
        userRepository.save(admin);
    }
}
