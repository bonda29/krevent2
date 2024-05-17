package org.example.krevent.models;

import jakarta.persistence.*;
import lombok.*;
import org.example.krevent.models.abstracts.BaseEntity;
import org.example.krevent.models.enums.SeatType;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "event_seats")
public class HallSeat extends BaseEntity {
    @Column(name = "row")
    private String row;

    @Column(name = "seat")
    private int seat;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private SeatType type;

    @Column(name = "price")
    private double price;

    @Column(name = "is_booked", nullable = false, columnDefinition = "boolean default false")
    private boolean isBooked;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "ticket_id", unique = true)
    private Ticket ticket;

    @ManyToOne(optional = false)
    @JoinColumn(name = "event_hall_id", nullable = false)
    private EventHall eventHall;

    @PrePersist
    protected void onCreate() {
        this.price = this.type.getPrice();
    }
}
