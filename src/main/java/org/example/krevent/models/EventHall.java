package org.example.krevent.models;

import jakarta.persistence.*;
import lombok.*;
import org.example.krevent.models.abstracts.BaseEntity;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "event_halls")
public class EventHall extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "seat_view", columnDefinition = "LONGTEXT")
    private String seatView;

    @Column(name = "capacity")
    private int capacity;

    @OneToMany(mappedBy = "eventHall", orphanRemoval = true)
    private Set<Event> events = new LinkedHashSet<>();

    @OneToMany(mappedBy = "eventHall", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<HallSeat> hallSeats = new LinkedHashSet<>();

}
