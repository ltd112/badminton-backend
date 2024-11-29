package com.iuh.fit.badminton_backend.service;

import com.iuh.fit.badminton_backend.mapper.GenericMapper;
import com.iuh.fit.badminton_backend.models.Schedule;
import com.iuh.fit.badminton_backend.dto.ScheduleDTO;
import com.iuh.fit.badminton_backend.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final GenericMapper genericMapper;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository, GenericMapper genericMapper) {
        this.scheduleRepository = scheduleRepository;
        this.genericMapper = genericMapper;
    }

    /**
     * Find all schedules by course ID.
     * @param courseId ID of the course.
     * @return list of schedule DTOs.
     */
    public List<ScheduleDTO> getSchedulesByCourseId(Long courseId) {
        List<Schedule> schedules = scheduleRepository.findByCourseId(courseId);
        return schedules.stream()
                .map(schedule -> genericMapper.convertToDto(schedule, ScheduleDTO.class))
                .toList();
    }

    /**
     * Find schedules by course ID and start time.
     * @param courseId ID of the course.
     * @param startTime the start time of the schedule.
     * @return list of schedule DTOs.
     */
    public List<ScheduleDTO> getSchedulesByCourseIdAndStartTime(Long courseId, LocalDateTime startTime) {
        List<Schedule> schedules = scheduleRepository.findByCourseIdAndStartTime(courseId, startTime);
        return schedules.stream()
                .map(schedule -> genericMapper.convertToDto(schedule, ScheduleDTO.class))
                .toList();
    }

    /**
     * Find schedules by location.
     * @param location the location of the schedule.
     * @return list of schedule DTOs.
     */
    public List<ScheduleDTO> getSchedulesByLocation(String location) {
        List<Schedule> schedules = scheduleRepository.findByLocation(location);
        return schedules.stream()
                .map(schedule -> genericMapper.convertToDto(schedule, ScheduleDTO.class))
                .toList();
    }

    /**
     * Find schedules by a range of start times.
     * @param startTime the start time range.
     * @param endTime the end time range.
     * @return list of schedule DTOs.
     */
    public List<ScheduleDTO> getSchedulesByStartTimeBetween(LocalDateTime startTime, LocalDateTime endTime) {
        List<Schedule> schedules = scheduleRepository.findByStartTimeBetween(startTime, endTime);
        return schedules.stream()
                .map(schedule -> genericMapper.convertToDto(schedule, ScheduleDTO.class))
                .toList();
    }

    /**
     * Save or update a schedule.
     * @param scheduleDTO DTO of the schedule.
     * @return saved or updated schedule DTO.
     */
    public ScheduleDTO saveOrUpdateSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = genericMapper.convertToEntity(scheduleDTO, Schedule.class);
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return genericMapper.convertToDto(savedSchedule, ScheduleDTO.class);
    }

    /**
     * Delete a schedule by ID.
     * @param id ID of the schedule.
     */
    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }

    /**
     * Find a schedule by ID.
     * @param id ID of the schedule.
     * @return an Optional containing the schedule DTO if found.
     */
    public Optional<ScheduleDTO> getScheduleById(Long id) {
        Optional<Schedule> schedule = scheduleRepository.findById(id);
        return schedule.map(sch -> genericMapper.convertToDto(sch, ScheduleDTO.class));
    }
}
