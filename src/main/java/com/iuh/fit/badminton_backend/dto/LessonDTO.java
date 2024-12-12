package com.iuh.fit.badminton_backend.dto;

import lombok.Data;

@Data
public class LessonDTO {

    private Long id;
    private Long courseId;
    private String courseName;
    private String title;
    private String content;
    private int lessonOrder;
    private String videoUrl; // URL video from S3
    private String imgUrl; // Image URL from S3
}