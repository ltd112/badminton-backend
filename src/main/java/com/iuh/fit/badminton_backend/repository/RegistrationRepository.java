package com.iuh.fit.badminton_backend.repository;

import com.iuh.fit.badminton_backend.dto.RegistrationDTO;
import com.iuh.fit.badminton_backend.models.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    // Find registrations by student ID
    List<Registration> findByStudentId(Long studentId);

    // Find registrations by course ID
    List<Registration> findByCourseId(Long courseId);

    // Find registrations by student ID and course ID
    Optional<RegistrationDTO> findByStudentIdAndCourseId(Long studentId, Long courseId);

    // Find registrations by payment status
    List<Registration> findByPaymentStatus(boolean paymentStatus);

    // Find registrations by course ID and registration date
    List<Registration> findByCourseIdAndRegistrationDate(Long courseId, LocalDate registrationDate);

    @Query("SELECT SUM(r.feePaid) FROM Registration r")
    Double sumTotalFeePay();

    @Query("SELECT SUM(r.feePaid) FROM Registration r WHERE r.registrationDate BETWEEN :startDate AND :endDate")
    Double sumFeePaidBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    Optional<Registration> findFirstByStudentIdAndCourseId(Long studentId, Long courseId);

}
