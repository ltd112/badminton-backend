package com.iuh.fit.badminton_backend.repository;

import com.iuh.fit.badminton_backend.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    // Tìm khóa học theo tên
    Optional<Course> findByCourseName(String courseName);

    // Tìm khóa học theo ngày bắt đầu

    // Tìm tất cả khóa học theo cấp độ
    List<Course> findByLevel(String level);

    // tìm all khóa học có feedback id
    List<Course> findByFeedbackId(Long feedbackId);

    // Tìm tất cả khóa học của một huấn luyện viên


    //findCoursesWithHighestRatingsInPeriod
    @Query("SELECT c FROM Course c " +
            "JOIN Feedback f ON c.id = f.course.id " +
            "WHERE f.feedbackDate BETWEEN :startDate AND :endDate " +
            "GROUP BY c.id " +
            "ORDER BY AVG(f.rating) DESC")
    List<Course> findCoursesWithHighestRatingsInPeriod(LocalDate startDate, LocalDate endDate);

    @Query("SELECT c FROM Course c " +
            "JOIN Feedback f ON c.id = f.course.id " +
            "GROUP BY c.id " +
            "ORDER BY AVG(f.rating) DESC")
    List<Course> findCoursesWithHighestRatings();
}
