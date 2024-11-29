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
     * @param userDTO the user DTO to be saved or updated.
     * @return the saved or updated user DTO.
     */
    public UserDTO saveOrUpdateUser(UserDTO userDTO) {
        User user = genericMapper.convertToEntity(userDTO, User.class);

        // log the user's emal
        System.out.println("User email: " + user);
        User savedUser = userRepository.save(user);
        return genericMapper.convertToDto(savedUser, UserDTO.class);
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
