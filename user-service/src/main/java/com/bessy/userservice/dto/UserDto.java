package com.bessy.userservice.dto;

import com.bessy.userservice.enums.Role;
import com.bessy.userservice.model.UserDetails;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private String id;
    private String username;
    private String email;
    private Role role;
    private UserDetails userDetails;
}
