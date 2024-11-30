package com.iuh.fit.badminton_backend.service;

import com.iuh.fit.badminton_backend.mapper.GenericMapper;
import com.iuh.fit.badminton_backend.models.User;
import com.iuh.fit.badminton_backend.dto.UserDTO;
import com.iuh.fit.badminton_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final GenericMapper genericMapper;

    @Autowired
    public UserService(UserRepository userRepository, GenericMapper genericMapper) {
        this.userRepository = userRepository;
        this.genericMapper = genericMapper;
    }

    /**
     * Find a user by their username.
     * @param username the username of the user.
     * @return an Optional containing the UserDTO if found.
     */
    public Optional<UserDTO> getUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(u -> genericMapper.convertToDto(u, UserDTO.class));
    }

    /**
     * Find a user by their email.
     * @param email the email of the user.
     * @return an Optional containing the UserDTO if found.
     */
    public Optional<UserDTO> getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(u -> genericMapper.convertToDto(u, UserDTO.class));
    }

    /**
     * Find all users by their role.
     * @param role the role of the user (e.g., ADMIN, STUDENT, COACH).
     * @return a list of UserDTOs.
     */
    public List<UserDTO> getUsersByRole(String role) {
        List<User> users = userRepository.findByRole(role);
        return users.stream()
                .map(user -> genericMapper.convertToDto(user, UserDTO.class))
                .toList();
    }

    /**
     * Save or update a user.
     * @param userDTO the user DTO to save .
     * @return the saved or updated user DTO.
     */
    public UserDTO addUsers(UserDTO userDTO) {
        // check if the user already exists
        Optional<User> existingUser = userRepository.findByUsername(userDTO.getUsername());
        // if user already not exists, create new user
        if (existingUser.isEmpty()) {
            User user = genericMapper.convertToEntity(userDTO, User.class);
            User savedUser = userRepository.save(user);
            return genericMapper.convertToDto(savedUser, UserDTO.class);
        }
        // if user already exists, thông báo user đã tồn tại
        return null;
    }

    /**
     * Update an existing user.
     * @param id the ID of the user to update.
     * @param userDTO the updated user DTO.
     * @return the updated user DTO.
     */

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            return null;
        }
        User updatedUser = genericMapper.convertToEntity(userDTO, User.class);
        updatedUser.setId(id);
        User savedUser = userRepository.save(updatedUser);
        return genericMapper.convertToDto(savedUser, UserDTO.class);
    }

    /**
     * Get all users.
     * @return a list of UserDTOs.
     */

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> genericMapper.convertToDto(user, UserDTO.class))
                .toList();
    }



    /**
     * Delete a user by ID.
     * @param id the ID of the user.
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Find a user by their ID.
     * @param id the ID of the user.
     * @return an Optional containing the UserDTO if found.
     */
    public Optional<UserDTO> getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(u -> genericMapper.convertToDto(u, UserDTO.class));
    }
}
