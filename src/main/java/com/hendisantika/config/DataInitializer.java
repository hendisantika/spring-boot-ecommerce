package com.hendisantika.config;

import com.hendisantika.model.Role;
import com.hendisantika.model.User;
import com.hendisantika.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-ecommerce
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 18/11/25
 * Time: 06.30
 * To change this template use File | Settings | File Templates.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        log.info("Initializing default users...");

        // Create admin user if not exists
        if (!userRepository.existsByUsername("admin")) {
            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .email("admin@shophub.com")
                    .firstName("Admin")
                    .lastName("User")
                    .role(Role.ROLE_ADMIN)
                    .enabled(true)
                    .accountNonExpired(true)
                    .accountNonLocked(true)
                    .credentialsNonExpired(true)
                    .build();
            userRepository.save(admin);
            log.info("✅ Admin user created: username=admin, password=admin123");
        }

        // Create regular user if not exists
        if (!userRepository.existsByUsername("user")) {
            User user = User.builder()
                    .username("user")
                    .password(passwordEncoder.encode("user123"))
                    .email("user@shophub.com")
                    .firstName("John")
                    .lastName("Doe")
                    .role(Role.ROLE_USER)
                    .enabled(true)
                    .accountNonExpired(true)
                    .accountNonLocked(true)
                    .credentialsNonExpired(true)
                    .build();
            userRepository.save(user);
            log.info("✅ Regular user created: username=user, password=user123");
        }

        log.info("✅ Default users initialization complete!");
    }
}
