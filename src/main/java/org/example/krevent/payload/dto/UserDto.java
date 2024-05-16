package org.example.krevent.payload.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.Setter;
import lombok.Value;
import org.example.krevent.models.User;
import org.example.krevent.models.enums.Role;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO for {@link User}
 */
@Data
public class UserDto implements Serializable {
    @JsonProperty("id")
    Long id;

    @JsonProperty("first_name")
    String firstName;

    @JsonProperty("last_name")
    String lastName;

    @JsonProperty("email")
    @Email
    String email;

    @JsonProperty("role")
    Role role;

    @JsonProperty("event_seats")
    Set<Long> eventSeatIds;

    @JsonProperty("date_of_creation")
    LocalDateTime dateOfCreation;

    @JsonProperty("is_enabled")
    boolean isEnabled;
}