package org.example.krevent;

import org.example.krevent.models.EventHall;
import org.example.krevent.models.HallSeat;
import org.example.krevent.repository.EventHallRepository;
import org.example.krevent.repository.HallSeatRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.ArrayList;

import static org.example.krevent.models.enums.SeatType.*;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class Server {

    public static void main(String[] args) {
        SpringApplication.run(Server.class, args);
    }

    /*    @Bean
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
        }*/
    @Bean
    public CommandLineRunner commandLineRunner(EventHallRepository eventHallRepository, HallSeatRepository hallSeatRepository) {
        return args -> {
            if (eventHallRepository.count() == 0) {
                var eventHall = EventHall.builder()
                        .name("Синдикален дом на културата")
                        .description("Синдикален дом на културата на транспортните работници в България-София")
                        .capacity(457)
                        .build();
                eventHallRepository.save(eventHall);

                var hallSeats = new ArrayList<HallSeat>();

                //wooden front
                for (int i = 1; i <= 4; i++) {
                    for (int j = 1; j <= 20; j++) {
                        hallSeats.add(HallSeat.builder()
                                .row(i + "A")
                                .seat(j)
                                .type(REGULAR_WOODEN)
                                .price(REGULAR_WOODEN.getPrice())
                                .eventHall(eventHall)
                                .build());
                    }
                }

                //regular
                for (int i = 1; i <= 13; i++) {
                    int seats = (i % 2 == 1) ? 20 : 19;
                    for (int j = 1; j <= seats; j++) {
                        hallSeats.add(HallSeat.builder()
                                .row(String.valueOf(i))
                                .seat(j)
                                .type(REGULAR)
                                .price(REGULAR.getPrice())
                                .eventHall(eventHall)
                                .build());
                    }
                }
                //regular 14th row
                for (int i = 1; i <= 8; i++) {
                    hallSeats.add(HallSeat.builder()
                            .row("14")
                            .seat(i)
                            .type(REGULAR)
                            .price(REGULAR.getPrice())
                            .eventHall(eventHall)
                            .build());
                }


                //balcony 1st row
                for (int i = 1; i <= 17; i++) {
                    hallSeats.add(HallSeat.builder()
                            .row("1")
                            .seat(i)
                            .type(BALCONY)
                            .price(BALCONY.getPrice())
                            .eventHall(eventHall)
                            .build());
                }
                //balcony
                for (int i = 2; i <= 5; i++) {
                    int seats = (i % 2 == 0) ? 20 : 19;
                    for (int j = 1; j <= seats; j++) {
                        hallSeats.add(HallSeat.builder()
                                .row(String.valueOf(i))
                                .seat(j)
                                .type(BALCONY)
                                .price(BALCONY.getPrice())
                                .eventHall(eventHall)
                                .build());
                    }
                }
                //balcony 6Ath row
                for (int i = 1; i <= 20; i++) {
                    hallSeats.add(HallSeat.builder()
                            .row("6A")
                            .seat(i)
                            .type(BALCONY_WOODEN)
                            .price(BALCONY_WOODEN.getPrice())
                            .eventHall(eventHall)
                            .build());
                }

                hallSeatRepository.saveAll(hallSeats);
            }

        };
    }
}
