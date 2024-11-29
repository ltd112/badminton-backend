package com.iuh.fit.badminton_backend.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RegistrationDTO {

    private Long id;

    private Long studentId; // ID của học viên

    private String studentFullName; // Tên đầy đủ của học viên

    private Long courseId; // ID của khóa học mà học viên đã đăng ký

    private String courseName; // Tên khóa học mà học viên đã đăng ký

    private LocalDate registrationDate; // Ngày đăng ký khóa học

    private boolean paymentStatus; // Trạng thái thanh toán

    private Double feePaid; // Số tiền đã thanh toán

    // Getters and Setters
}
