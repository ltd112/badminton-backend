package com.iuh.fit.badminton_backend.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FeedbackDTO {

    private Long id;

    private Long studentId; // ID của học viên gửi phản hồi

    private String studentName; // Tên học viên gửi phản hồi

    private Long courseId; // ID của khóa học được phản hồi

    private String courseName; // Tên khóa học được phản hồi

    private String content; // Nội dung phản hồi

    private int rating; // Đánh giá từ 1 đến 5

    private LocalDate feedbackDate; // Ngày gửi phản hồi

    // Getters and Setters
}
