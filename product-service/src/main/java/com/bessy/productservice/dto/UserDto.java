package com.bessy.productservice.dto;

import com.bessy.productservice.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {
    private String id;
    private String username;
    private String email;
    private UserRole role;
}
// TEST TEST