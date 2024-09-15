package com.bessy.userservice.service;

import com.bessy.userservice.client.FileStorageClient;
import com.bessy.userservice.enums.Active;
import com.bessy.userservice.enums.Role;
import com.bessy.userservice.exc.NotFoundException;
import com.bessy.userservice.model.User;
import com.bessy.userservice.model.UserDetails;
import com.bessy.userservice.repository.UserRepository;
import com.bessy.userservice.request.RegisterRequest;
import com.bessy.userservice.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileStorageClient fileStorageClient;
    private final ModelMapper modelMapper;

    public User saveUser(RegisterRequest request) {
        User toSave = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .role(Role.USER)
                .active(Active.ACTIVE).build();
        return userRepository.save(toSave);
    }

    public List<User> getAll() {
        return userRepository.findAllByActive(Active.ACTIVE);
    }

    public User getUserById(UUID id) {
        return findUserById(id);
    }

    public User getUserByEmail(String email) {
        return findUserByEmail(email);
    }

    public User getUserByUsername(String username) {
        return findUserByUsername(username);
    }

    public User updateUserDetailsById(UserUpdateRequest request) {
        User toUpdate = findUserById(request.getId());

        if (toUpdate == null) {
            throw new NotFoundException("User not found");
        }

        if (request.getUserDetails() != null) {
            updateUserDetails(toUpdate.getUserDetails(), request.getUserDetails());
        }

        modelMapper.map(request, toUpdate);
        return userRepository.save(toUpdate);
    }

    public User updateUserProfilePicture(UUID id, MultipartFile file) {
        User toUpdate = findUserById(id);

        if (toUpdate == null) {
            throw new NotFoundException("User not found");
        }

        if (file != null) {
            String profilePicture = fileStorageClient.uploadImageToFIleSystem(file).getBody();
            if (profilePicture != null) {
                fileStorageClient.deleteImageFromFileSystem(toUpdate.getUserDetails().getProfilePicture());
                toUpdate.getUserDetails().setProfilePicture(profilePicture);
            }
        }

        return userRepository.save(toUpdate);
    }


    public void deleteUserById(UUID id) {
        User toDelete = findUserById(id);
        toDelete.setActive(Active.INACTIVE);
        userRepository.save(toDelete);
    }

    protected User findUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    protected User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    protected User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    /*private UserDetails updateUserDetails(UserDetails toUpdate, UserDetails request, MultipartFile file) {
        toUpdate = toUpdate == null ? new UserDetails() : toUpdate;

        if (file != null) {
            String profilePicture = fileStorageClient.uploadImageToFIleSystem(file).getBody();
            if (profilePicture != null) {
                fileStorageClient.deleteImageFromFileSystem(toUpdate.getProfilePicture());
                toUpdate.setProfilePicture(profilePicture);
            }
        }

        modelMapper.map(request, toUpdate);

        return toUpdate;
    }*/

    private void updateUserDetails(UserDetails toUpdate, UserDetails request) {

        modelMapper.map(request, toUpdate);
    }
}