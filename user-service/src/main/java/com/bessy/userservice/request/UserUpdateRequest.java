package com.bessy.userservice.request;

import com.bessy.userservice.model.UserDetails;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserUpdateRequest {
    @NotBlank(message = "Id is required")
    private String id;
    private String username;
    private String password;
    private UserDetails userDetails;
}
