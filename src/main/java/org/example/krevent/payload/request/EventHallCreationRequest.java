package org.example.krevent.payload.request;

import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class EventHallCreationRequest {
    @Size(min = 3, max = 50)
    String name;
    String description;
    Integer capacity;
}
