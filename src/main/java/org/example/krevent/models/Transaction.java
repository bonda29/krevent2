package org.example.krevent.models;

import jakarta.persistence.*;
import lombok.*;
import org.example.krevent.models.abstracts.BaseEntity;
import org.example.krevent.models.enums.TransactionStatus;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transactions")
public class Transaction extends BaseEntity {
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(orphanRemoval = true)
    private Set<HallSeat> hallSeats = new LinkedHashSet<>();

    @Column(name = "session_id")
    private String sessionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TransactionStatus status;

    @Column(name = "time_of_creation")
    private LocalDateTime timeOfCreation;
}
