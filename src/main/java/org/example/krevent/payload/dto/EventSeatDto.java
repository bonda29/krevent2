package org.example.krevent.payload.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Value;
import org.example.krevent.models.EventSeat;

import java.io.Serializable;

/**
 * DTO for {@link EventSeat}
 */
@Data
public class EventSeatDto implements Serializable {
    @JsonProperty
    Long id;

    @JsonProperty("row")
    int row;

    @JsonProperty("seat")
    int seat;

    @JsonProperty("price")
    double price;

    @JsonProperty("is_booked")
    boolean isBooked;

    @JsonProperty("event_id")
    Long eventId;

    @JsonProperty("user_id")
    Long userId;
}