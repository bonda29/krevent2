package org.example.krevent.payload.request;

import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class EventCreationRequest {
    @Size(min = 3, max = 50)
    String name;
    String description;
    Long eventHallId;
    String date;
}
