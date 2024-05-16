package org.example.krevent.models;

import jakarta.persistence.*;
import lombok.*;
import org.example.krevent.models.abstracts.BaseEntity;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "events")
//todo: add Room or Hall to the event instead of seatView
public class Event extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "seat_view", columnDefinition = "LONGTEXT")
    private String seatView;

    @Column(name = "date")
    private LocalDateTime date;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EventSeat> eventSeats = new LinkedHashSet<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "event_image_urls", joinColumns = @JoinColumn(name = "event_id"))
    @Column(name = "image_url")
    private List<String> imageUrls;

}
