package com.iuh.fit.badminton_backend.repository;

import com.iuh.fit.badminton_backend.models.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    // Find registrations by student ID
    List<Registration> findByStudentId(Long studentId);

    // Find registrations by course ID
    List<Registration> findByCourseId(Long courseId);

    // Find registrations by student ID and course ID
    List<Registration> findByStudentIdAndCourseId(Long studentId, Long courseId);

    // Find registrations by payment status
    List<Registration> findByPaymentStatus(boolean paymentStatus);

    // Find registrations by course ID and registration date
    List<Registration> findByCourseIdAndRegistrationDate(Long courseId, LocalDate registrationDate);

    // You can add more custom query methods here if needed
}
