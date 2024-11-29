package com.iuh.fit.badminton_backend.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserDTO {

    private Long id;

    private String username;// Tên đăng nhập

    private String password; // Mật khẩu

    private String role; // Vai trò của người dùng: ADMIN, STUDENT, COACH

    private String fullName; // Tên đầy đủ

    private String email; // Email

    private String phoneNumber; // Số điện thoại

    private LocalDate dateOfBirth; // Ngày sinh

    private List<CourseDTO> courses; // Danh sách khóa học (nếu là Hướng dẫn viên)

    private List<RegistrationDTO> registrations; // Danh sách học viên đã đăng ký (nếu là Học viên)

    // Getters and Setters
}
