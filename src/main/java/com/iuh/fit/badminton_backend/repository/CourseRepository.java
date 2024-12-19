package com.iuh.fit.badminton_backend.repository;

import com.iuh.fit.badminton_backend.dto.CourseDTO;
import com.iuh.fit.badminton_backend.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    // Tìm khóa học theo tên
    Optional<Course> findByCourseName(String courseName);

    @Query("SELECT c, AVG(f.rating) as avgRating FROM Course c " +
            "JOIN Feedback f ON c.id = f.course.id " +
            "GROUP BY c.id ")

    // Tìm tất cả khóa học theo cấp độ
    List<Course> findByLevel(String level);

    // Tìm tất cả khóa học của một huấn luyện viên

    //findCoursesWithHighestRatingsInPeriod
    @Query("SELECT c, AVG(f.rating) as avgRating FROM Course c " +
            "JOIN Feedback f ON c.id = f.course.id " +
            "WHERE f.feedbackDate BETWEEN :startDate AND :endDate " +
            "GROUP BY c.id, c.courseName, c.description, c.level, c.fee " +
            "ORDER BY avgRating DESC")
    List<Object[]> findCoursesWithHighestRatingsInPeriod(LocalDate startDate, LocalDate endDate);
    //findCoursesWithHighestRatings
    @Query("SELECT c, AVG(f.rating) as avgRating FROM Course c " +
            "JOIN Feedback f ON c.id = f.course.id " +
            "GROUP BY c.id " +
            "ORDER BY avgRating DESC")
    List<Object[]> findCoursesWithHighestRatings();

    //findAllCoursesWithAvgRating
    @Query("SELECT c, AVG(f.rating) as avgRating FROM Course c " +
            "JOIN Feedback f ON c.id = f.course.id " +
            "GROUP BY c.id ")
    List<Object[]> findAllCoursesWithAvgRating();
    //findCoursesByNameContainingWithAvgRating
    @Query("SELECT c, AVG(f.rating) as avgRating FROM Course c " +
            "JOIN Feedback f ON c.id = f.course.id " +
            "WHERE c.courseName LIKE %:keyword% " +
            "GROUP BY c.id")
    List<Object[]> findCoursesByNameContainingWithAvgRating(@Param("keyword") String keyword);
    // findCourseWithHighestTotalFeePaid
    @Query("SELECT c, SUM(r.feePaid) as totalFeePaid FROM Course c " +
            "JOIN Registration r ON c.id = r.course.id " +
            "GROUP BY c.id " +
            "ORDER BY totalFeePaid DESC")
    List<Object[]> findCourseWithHighestTotalFeePaid();
    // findCourseWithHighestTotalFeePaidInPeriod
    @Query("SELECT c, SUM(r.feePaid) as totalFeePaid FROM Course c " +
            "JOIN Registration r ON c.id = r.course.id " +
            "WHERE r.registrationDate BETWEEN :startDate AND :endDate " +
            "GROUP BY c.id " +
            "ORDER BY totalFeePaid DESC")
    List<Object[]> findCourseWithHighestTotalFeePaidInPeriod(LocalDate startDate, LocalDate endDate);
    // findCourseWithHighestPurchaseCount
    @Query("SELECT c, COUNT(r.id) as purchaseCount FROM Course c " +
            "JOIN Registration r ON c.id = r.course.id " +
            "GROUP BY c.id " +
            "ORDER BY purchaseCount DESC")
    List<Object[]> findCourseWithHighestPurchaseCount();

    @Query("SELECT c.courseName, SUM(r.feePaid) as totalRevenue " +
            "FROM Course c " +
            "JOIN Registration r ON c.id = r.course.id " +
            "WHERE c.fee > 0 AND r.registrationDate BETWEEN :startDate AND :endDate " +
            "GROUP BY c.courseName " +
            "ORDER BY totalRevenue DESC")
    List<Object[]> findRevenueByCourseInPeriod(LocalDate startDate, LocalDate endDate);


}
