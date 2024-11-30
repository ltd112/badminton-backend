package com.iuh.fit.badminton_backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.iuh.fit.badminton_backend.util.CustomLocalDateDeserializer;
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

    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth; // Ngày sinh

    // Danh sách học viên đã đăng ký (nếu là Học viên)

    // Getters and Setters
}
