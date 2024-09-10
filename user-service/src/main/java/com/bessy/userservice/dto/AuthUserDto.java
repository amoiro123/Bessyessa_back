package com.bessy.userservice.dto;

import com.bessy.userservice.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class AuthUserDto {
    private String id;
    private String username;
    private String password;
    private Role role;
}