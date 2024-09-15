package com.bessy.authservice.dto;

import com.bessy.authservice.enums.Role;
import lombok.Data;

import java.util.UUID;

@Data
public class UserDto {
    private UUID id;
    private String username;
    private String password;
    private Role role;
}
