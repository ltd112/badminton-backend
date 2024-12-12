package com.iuh.fit.badminton_backend.dto;

import com.iuh.fit.badminton_backend.models.enums.Level;
import lombok.Data;

import java.util.List;

@Data
public class CourseDTO {

    private Long id;

    private String courseName; // Tên khóa học

    private String description; // Mô tả chi tiết khóa học

    private Level level; // Cấp độ khóa học: BEGINNER, INTERMEDIATE, ADVANCED


    private Double fee; // Học phí của khóa học

//    private Double averageRating;

    private List<FeedbackDTO> feedbacks; // Phản hồi từ học viên

    private List<LessonDTO> lessons; // Danh sách bài học trong khóa học

    // Getters and Setters
}
