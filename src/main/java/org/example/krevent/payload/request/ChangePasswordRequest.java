package org.example.krevent.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChangePasswordRequest {

    @JsonProperty("current_password")
    private String currentPassword;

    @JsonProperty("new_password")
    private String newPassword;

    @JsonProperty("confirmation_password")
    private String confirmationPassword;
}
