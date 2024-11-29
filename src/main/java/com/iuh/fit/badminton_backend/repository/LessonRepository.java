package com.iuh.fit.badminton_backend.repository;

import com.iuh.fit.badminton_backend.models.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    // Find lessons by course ID
    List<Lesson> findByCourseId(Long courseId);

    // Find lessons by course ID and lesson order
    List<Lesson> findByCourseIdAndLessonOrder(Long courseId, int lessonOrder);

    // You can add more custom query methods here if needed
}
