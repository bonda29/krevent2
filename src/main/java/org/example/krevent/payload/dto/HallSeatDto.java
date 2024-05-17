package org.example.krevent.payload.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Value;
import org.example.krevent.models.HallSeat;
import org.example.krevent.models.enums.SeatType;

import java.io.Serializable;

/**
 * DTO for {@link HallSeat}
 */
@Data
public class HallSeatDto implements Serializable {
    Long id;
    String row;
    int seat;
    SeatType type;
    double price;

    @JsonProperty("is_booked")
    boolean isBooked;

    @JsonProperty("ticket_id")
    Long ticketId;

    @JsonProperty("event_hall_id")
    Long eventHallId;
}