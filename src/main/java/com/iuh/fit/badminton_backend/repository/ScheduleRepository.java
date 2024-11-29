package com.iuh.fit.badminton_backend.repository;

import com.iuh.fit.badminton_backend.models.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    // Find schedules by course ID
    List<Schedule> findByCourseId(Long courseId);

    // Find schedules by course ID and start time
    List<Schedule> findByCourseIdAndStartTime(Long courseId, LocalDateTime startTime);

    // Find schedules by location
    List<Schedule> findByLocation(String location);

    // Find schedules by start time and end time range
    List<Schedule> findByStartTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    // You can add more custom queries here as needed
}
