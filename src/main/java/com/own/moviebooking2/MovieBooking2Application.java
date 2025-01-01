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

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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

        Optional<UserRole> userRole = userRoleRepository.findByRole(Role.USER_ROLE);
        Optional<UserRole> hrRole = userRoleRepository.findByRole(Role.HR_ROLE);
        Optional<UserRole> adminRole = userRoleRepository.findByRole(Role.ADMIN_ROLE);

        if (!userRole.isPresent() && !hrRole.isPresent() && !adminRole.isPresent()) {
            userRoleRepository.save(new UserRole(Role.USER_ROLE));
            userRoleRepository.save(new UserRole(Role.HR_ROLE));
            userRoleRepository.save(new UserRole(Role.ADMIN_ROLE));
        }

        Set<UserRole> userRoleSet = new HashSet<>();
        Set<UserRole> hrRoleSet = new HashSet<>();
        Set<UserRole> adminRoleSet = new HashSet<>();

        userRoleSet.add(userRole.get());

        hrRoleSet.add(userRole.get());
        hrRoleSet.add(hrRole.get());

        adminRoleSet.add(hrRole.get());
        adminRoleSet.add(userRole.get());
        adminRoleSet.add(adminRole.get());

        User user = new User();
        user.setUsername("dattle99");
        user.setPassword(passwordEncoder.encode("Ledat999@"));
        user.setRoles(userRoleSet);

        User hr = new User();
        hr.setUsername("lanle99");
        hr.setPassword(passwordEncoder.encode("Ledat999@"));
        hr.setRoles(hrRoleSet);

        User admin = new User();
        admin.setUsername("dattle329");
        admin.setPassword(passwordEncoder.encode("Ledat999@"));
        admin.setRoles(adminRoleSet);

        userRepository.save(user);
        userRepository.save(admin);
        userRepository.save(hr);
    }
}
