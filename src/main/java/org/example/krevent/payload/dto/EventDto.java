package org.example.krevent.payload.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Value;
import org.example.krevent.models.Event;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link Event}
 */
@Data
public class EventDto implements Serializable {
    Long id;
    String name;
    String description;
    LocalDateTime date;

    @JsonProperty("event_hall_Id")
    Long eventHallId;

    @JsonProperty("image_urls")
    List<String> imageUrls;
}