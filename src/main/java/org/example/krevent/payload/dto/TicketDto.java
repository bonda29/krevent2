package org.example.krevent.payload.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import org.example.krevent.models.Ticket;

import java.io.Serializable;

/**
 * DTO for {@link Ticket}
 */
@Value
public class TicketDto implements Serializable {
    Long id;

    @JsonProperty("user_id")
    Long userId;

    @JsonProperty("hall_seat_id")
    Long hallSeatId;
    double price;

    @JsonProperty("qr_code_image")
    String qrCodeImage;
}