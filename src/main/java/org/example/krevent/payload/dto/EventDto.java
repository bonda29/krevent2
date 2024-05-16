package org.example.krevent.payload.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.example.krevent.models.Event;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * DTO for {@link Event}
 */
@Data
public class EventDto implements Serializable {
    @JsonProperty("id")
    Long id;

    @JsonProperty("name")
    String name;

    @JsonProperty("description")
    String description;

    @JsonProperty("seat_view")
    String seatView;

    @JsonProperty("date")
    LocalDateTime date;

    @JsonProperty("event_seats")
    Set<Long> eventSeatIds;

    @JsonProperty("image_urls")
    List<String> imageUrls;
}