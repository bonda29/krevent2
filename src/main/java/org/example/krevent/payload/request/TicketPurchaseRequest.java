package org.example.krevent.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value
public class TicketPurchaseRequest {
    @JsonProperty("user_id")
    Long userId;

    @JsonProperty("hall_seat_ids")
    List<Long> hallSeatIds;
}
