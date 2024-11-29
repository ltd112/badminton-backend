package com.iuh.fit.badminton_backend.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "lessons")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course; // Thuộc về khóa học nào

    @Column(nullable = false)
    private String title; // Tên bài học

    @Column(nullable = false, length = 5000)
    private String content; // Nội dung chi tiết bài học

    private int lessonOrder; // Thứ tự của bài học trong khóa

    // Video hoặc tài liệu đi kèm
    private String videoUrl;

    private String documentUrl;
    // img save in database as bytea
    private byte[] img;


    // Getters and Setters
}
