package org.example.krevent.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class FreeTicketCreationRequest {
    @JsonProperty("name")
    String name;

    @JsonProperty("hall_seat_id")
    Long hallSeatId;
}
