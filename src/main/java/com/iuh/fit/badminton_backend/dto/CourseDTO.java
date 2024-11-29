package com.iuh.fit.badminton_backend.dto;

import com.iuh.fit.badminton_backend.models.enums.CourseStatus;
import com.iuh.fit.badminton_backend.models.enums.Level;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CourseDTO {

    private Long id;

    private String courseName; // Tên khóa học

    private String description; // Mô tả chi tiết khóa học

    private Level level; // Cấp độ khóa học: BEGINNER, INTERMEDIATE, ADVANCED

    private CourseStatus status; // Trạng thái khóa học: UPCOMING, ONGOING, COMPLETED, CANCELLED

    private int maxParticipants; // Số lượng học viên tối đa

    private Double fee; // Học phí của khóa học

    private Long coachId; // ID của hướng dẫn viên

    private String coachName; // Tên hướng dẫn viên

    private LocalDate startDate; // Ngày bắt đầu khóa học

    private LocalDate endDate; // Ngày kết thúc khóa học

    private List<ScheduleDTO> schedules; // Danh sách lịch học

    private List<RegistrationDTO> registrations; // Danh sách học viên đăng ký

    private List<FeedbackDTO> feedbacks; // Phản hồi từ học viên

    private List<LessonDTO> lessons; // Danh sách bài học trong khóa học

    // Getters and Setters
}
