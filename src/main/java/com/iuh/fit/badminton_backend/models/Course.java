package com.iuh.fit.badminton_backend.models;

import com.iuh.fit.badminton_backend.models.enums.CourseStatus;
import com.iuh.fit.badminton_backend.models.enums.Level;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String courseName; // Tên khóa học

    @Column(nullable = false)
    private String description; // Mô tả chi tiết khóa học

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Level level; // Cấp độ khóa học: BEGINNER, INTERMEDIATE, ADVANCED

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CourseStatus status; // Trạng thái: UPCOMING, ONGOING, COMPLETED, CANCELLED

    @Column(nullable = false)
    private int maxParticipants; // Số lượng học viên tối đa

    @Column(nullable = false)
    private Double fee; // Học phí của khóa học

    @ManyToOne
    @JoinColumn(name = "coach_id", nullable = false)
    private User coach; // Hướng dẫn viên của khóa học

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Schedule> schedules; // Danh sách lịch học

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Registration> registrations; // Danh sách học viên đăng ký

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Feedback> feedbacks; // Phản hồi từ học viên

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Lesson> lessons; // Nội dung bài học trong khóa học

    // Ngày bắt đầu và kết thúc khóa học
    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

}
