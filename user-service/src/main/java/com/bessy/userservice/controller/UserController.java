package com.bessy.userservice.controller;

import com.bessy.userservice.dto.AuthUserDto;
import com.bessy.userservice.dto.UserDto;
import com.bessy.userservice.exc.NotFoundException;
import com.bessy.userservice.request.RegisterRequest;
import com.bessy.userservice.request.UserUpdateRequest;
import com.bessy.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<UserDto> save(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(modelMapper.map(userService.saveUser(request), UserDto.class));
    }

    @GetMapping("/getAll")
   // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getAll() {
        return ResponseEntity.ok(userService.getAll().stream()
                .map(user -> modelMapper.map(user, UserDto.class)).toList());
    }
   // @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getUserById/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(modelMapper.map(userService.getUserById(id), UserDto.class));
    }

    @GetMapping("/getUserByEmail/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(modelMapper.map(userService.getUserByEmail(email), UserDto.class));
    }

    @GetMapping("/getUserByUsername/{username}")
    public ResponseEntity<AuthUserDto> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(modelMapper.map(userService.getUserByUsername(username), AuthUserDto.class));
    }

   /* @PutMapping("/update")
    //@PreAuthorize("hasRole('ADMIN') or @userService.getUserById(#request.id).username == principal")
    public ResponseEntity<UserDto> updateUserById(@Valid @RequestPart UserUpdateRequest request,
                                                  @RequestPart(required = false) MultipartFile file) {
        return ResponseEntity.ok(modelMapper.map(userService.updateUserById(request, file), UserDto.class));
    }*/
   @PutMapping("/update")
   public ResponseEntity<?> updateUserById(@Valid @RequestPart UserUpdateRequest request,
                                           @RequestPart(required = false) MultipartFile file) {
       try {
           UserDto updatedUser = modelMapper.map(userService.updateUserById(request, file), UserDto.class);
           return ResponseEntity.ok(updatedUser);
       } catch (NotFoundException e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID: " + request.getId());
       }
   }


    @DeleteMapping("/deleteUserById/{id}")
   // @PreAuthorize("hasRole('ADMIN') or @userService.getUserById(#id).username == principal")
    public ResponseEntity<Void> deleteUserById(@PathVariable String id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }
}
