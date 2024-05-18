package org.example.krevent;

import org.example.krevent.auth.AuthenticationService;
import org.example.krevent.payload.request.RegisterRequest;
import org.example.krevent.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import static org.example.krevent.models.enums.Role.ADMIN;
import static org.example.krevent.models.enums.Role.MANAGER;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class Server {

    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(AuthenticationService authenticationService, UserRepository userRepository) {
        return args -> {
            if (userRepository.existsByEmail("admin@mail.com")) {
                var admin = RegisterRequest.builder()
                        .firstName("Admin")
                        .lastName("Admin")
                        .email("admin@mail.com")
                        .password("password")
                        .role(ADMIN)
                        .build();
                System.out.println("Admin token: " + authenticationService.register(admin).getAccessToken());
            }
            if (userRepository.existsByEmail("manager@mail.com")) {
                var manager = RegisterRequest.builder()
                        .firstName("Manager")
                        .lastName("Manager")
                        .email("manager@mail.com")
                        .password("password")
                        .role(MANAGER)
                        .build();
                System.out.println("Manager token: " + authenticationService.register(manager).getAccessToken());
            }
        };
    }
}
