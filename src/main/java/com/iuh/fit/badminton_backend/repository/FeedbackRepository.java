package com.iuh.fit.badminton_backend.repository;

import com.iuh.fit.badminton_backend.models.Course;
import com.iuh.fit.badminton_backend.models.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    // Tìm tất cả phản hồi của một học viên theo ID học viên
    List<Feedback> findByStudentId(Long studentId);

    // Tìm tất cả phản hồi cho một khóa học theo ID khóa học
    List<Feedback> findByCourseId(Long courseId);

    // Tìm phản hồi của một học viên cho một khóa học cụ thể
    Optional<Feedback> findByStudentIdAndCourseId(Long studentId, Long courseId);

    // Tìm tất cả phản hồi của một học viên theo đánh giá (rating)
    List<Feedback> findByStudentIdAndRating(Long studentId, int rating);

    // Tìm tất cả phản hồi theo ngày gửi
    List<Feedback> findByFeedbackDate(LocalDate feedbackDate);

    // tìm tất cả khóa học có rating và feedbackDate cụ thể
    List<Feedback> findByRatingAndFeedbackDate(int rating, LocalDate feedbackDate);

}
