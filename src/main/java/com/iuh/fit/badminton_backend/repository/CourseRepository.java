package com.iuh.fit.badminton_backend.repository;

import com.iuh.fit.badminton_backend.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    // Tìm khóa học theo tên
    Optional<Course> findByCourseName(String courseName);

    // Tìm khóa học theo trạng thái
    List<Course> findByStatus(String status);

    // Tìm khóa học theo ngày bắt đầu
    List<Course> findByStartDate(LocalDate startDate);

    // Tìm tất cả khóa học theo cấp độ
    List<Course> findByLevel(String level);

    // Tìm tất cả khóa học của một huấn luyện viên
    List<Course> findByCoachId(Long coachId);
}