package org.example.krevent.models;

import jakarta.persistence.*;
import lombok.*;
import org.example.krevent.models.abstracts.BaseEntity;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "event_seats")
public class EventSeat extends BaseEntity {
    @Column(name = "row")
    private int row;

    @Column(name = "seat")
    private int seat;

    @Column(name = "price")
    private double price;

    @Column(name = "is_booked", nullable = false, columnDefinition = "boolean default false")
    private boolean isBooked;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
}
