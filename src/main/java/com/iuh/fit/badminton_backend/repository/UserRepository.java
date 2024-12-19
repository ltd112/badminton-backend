package com.iuh.fit.badminton_backend.repository;

import com.iuh.fit.badminton_backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find a user by their username
    Optional<User> findByUsername(String username);

    // Find a user by their email
    Optional<User> findByEmail(String email);

    // Find users by their role
    List<User> findByRole(String role);

    // change password
    @Modifying
    @Query("UPDATE User u SET u.password = :newPassword WHERE u.username = :username")
    void changePassword(String newPassword, String username);

}
