package org.example.krevent.payload.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.krevent.models.EventHall;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link EventHall}
 */
@Data
public class EventHallDto implements Serializable {
    Long id;
    String name;
    String description;

    @JsonProperty("seat_view")
    String seatView;
    int capacity;

    @JsonProperty("event_ids")
    Set<Long> eventIds;

    @JsonProperty("hall_seat_ids")
    Set<Long> hallSeatIds;
}