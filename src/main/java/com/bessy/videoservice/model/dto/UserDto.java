package com.bessy.videoservice.model.dto;

import com.bessy.videoservice.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {
    private String id;
    private String username;
    private String email;
    private UserRole role;
}
// TEST TEST