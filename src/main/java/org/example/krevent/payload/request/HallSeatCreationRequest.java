package org.example.krevent.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.Value;
import org.example.krevent.models.enums.SeatType;

@Value
public class HallSeatCreationRequest {
    @Size(min = 1, max = 3)
    String row;
    Integer seat;
    SeatType type;
    @JsonProperty("event_hall_id")
    Long eventHallId;

}
