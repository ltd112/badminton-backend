package com.iuh.fit.badminton_backend.dto;

import lombok.Data;

@Data
public class LessonDTO {

    private Long id;

    private Long courseId; // ID của khóa học mà bài học thuộc về

    private String courseName; // Tên khóa học mà bài học thuộc về

    private String title; // Tên bài học

    private String content; // Nội dung chi tiết bài học

    private int lessonOrder; // Thứ tự của bài học trong khóa học

    private String videoUrl; // URL video đi kèm (nếu có)

    private String documentUrl; // URL tài liệu đi kèm (nếu có)

    private String img; // Hình ảnh minh họa (nếu có)
    // Getters and Setters
}
