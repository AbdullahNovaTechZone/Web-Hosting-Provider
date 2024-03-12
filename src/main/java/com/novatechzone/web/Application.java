package com.novatechzone.web;

import com.novatechzone.web.domain.security.entity.User;
import com.novatechzone.web.domain.security.entity.UserRole;
import com.novatechzone.web.domain.security.repos.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@RequiredArgsConstructor
@SpringBootApplication
public class Application {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    public void saveAdmin() {
        Optional<User> optionalUser = userRepository.findByUsername("abdullahzufar@novatechzone.com");
        if (optionalUser.isEmpty()) {
            userRepository.save(
                    User.builder()
                            .name("Abdullah Zufar")
                            .username("abdullahzufar@novatechzone.com")
                            .password(passwordEncoder.encode("1234"))
                            .userRole(UserRole.SUPER_USER).build()
            );
        }
    }
}
