package com.bessy.userservice.request;

import com.bessy.userservice.model.UserDetails;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
public class UserUpdateRequest {
    @NotBlank(message = "Id is required")
    private UUID id;
    private String username;
    private String password;
    private UserDetails userDetails;
}
