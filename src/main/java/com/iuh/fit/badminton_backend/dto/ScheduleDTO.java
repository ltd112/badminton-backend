package com.iuh.fit.badminton_backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScheduleDTO {

    private Long id;

    private Long courseId; // ID của khóa học

    private String courseName; // Tên khóa học

    private LocalDateTime startTime; // Thời gian bắt đầu lịch học

    private LocalDateTime endTime; // Thời gian kết thúc lịch học

    private String location; // Địa điểm tổ chức lớp học

    // Getters and Setters
}
