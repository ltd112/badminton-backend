package com.iuh.fit.badminton_backend.controller;

import com.iuh.fit.badminton_backend.dto.ApiResponse;
import com.iuh.fit.badminton_backend.dto.ScheduleDTO;
import com.iuh.fit.badminton_backend.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    /**
     * Get schedules by course ID.
     * @param courseId the ID of the course.
     * @return ApiResponse containing the list of schedules.
     */
    @GetMapping("/course/{courseId}")
    public ApiResponse<List<ScheduleDTO>> getSchedulesByCourseId(@PathVariable Long courseId) {
        List<ScheduleDTO> schedules = scheduleService.getSchedulesByCourseId(courseId);
        if (schedules.isEmpty()) {
            return ApiResponse.error("No schedules found for this course", null);
        }
        return ApiResponse.success("Schedules fetched successfully", schedules);
    }

    /**
     * Get schedules by course ID and start time.
     * @param courseId the ID of the course.
     * @param startTime the start time of the schedule.
     * @return ApiResponse containing the list of schedules.
     */
    @GetMapping("/course/{courseId}/start-time")
    public ApiResponse<List<ScheduleDTO>> getSchedulesByCourseIdAndStartTime(
            @PathVariable Long courseId, @RequestParam LocalDateTime startTime) {
        List<ScheduleDTO> schedules = scheduleService.getSchedulesByCourseIdAndStartTime(courseId, startTime);
        if (schedules.isEmpty()) {
            return ApiResponse.error("No schedules found for this course and start time", null);
        }
        return ApiResponse.success("Schedules fetched successfully", schedules);
    }

    /**
     * Get schedules by location.
     * @param location the location of the schedule.
     * @return ApiResponse containing the list of schedules.
     */
    @GetMapping("/location")
    public ApiResponse<List<ScheduleDTO>> getSchedulesByLocation(@RequestParam String location) {
        List<ScheduleDTO> schedules = scheduleService.getSchedulesByLocation(location);
        if (schedules.isEmpty()) {
            return ApiResponse.error("No schedules found for this location", null);
        }
        return ApiResponse.success("Schedules fetched successfully", schedules);
    }

    /**
     * Get schedules by a range of start times.
     * @param startTime the start time range.
     * @param endTime the end time range.
     * @return ApiResponse containing the list of schedules.
     */
    @GetMapping("/start-time-range")
    public ApiResponse<List<ScheduleDTO>> getSchedulesByStartTimeBetween(
            @RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime) {
        List<ScheduleDTO> schedules = scheduleService.getSchedulesByStartTimeBetween(startTime, endTime);
        if (schedules.isEmpty()) {
            return ApiResponse.error("No schedules found within this time range", null);
        }
        return ApiResponse.success("Schedules fetched successfully", schedules);
    }

    /**
     * Save or update a schedule.
     * @param scheduleDTO the schedule DTO to save .
     * @return ApiResponse containing the saved or updated schedule.
     */
    @PostMapping("/add")
    public ApiResponse<ScheduleDTO> saveSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        ScheduleDTO savedSchedule = scheduleService.addSchedule(scheduleDTO);
        return ApiResponse.success("Schedule saved successfully", savedSchedule);
    }

    /**
     * Update an existing schedule.
     * @param scheduleDTO the updated schedule DTO.
     * @param id the ID of the schedule to update.
     * @return ApiResponse containing the updated schedule.
     */
    @PostMapping("/update")
    public ApiResponse<ScheduleDTO> updateSchedule(@RequestBody ScheduleDTO scheduleDTO, @RequestParam Long id) {
        Optional<ScheduleDTO> updatedSchedule = scheduleService.updateSchedule(id, scheduleDTO);
        return updatedSchedule.map(dto -> ApiResponse.success("Schedule updated successfully", dto))
                .orElseGet(() -> ApiResponse.error("Schedule not found", null));
    }

    /**
     * Delete a schedule by ID.
     * @param id the ID of the schedule to delete.
     * @return ApiResponse containing the result of the deletion.
     */
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ApiResponse.success("Schedule deleted successfully", null);
    }

    /**
     * Get a schedule by ID.
     * @param id the ID of the schedule.
     * @return ApiResponse containing the schedule if found.
     */
    @GetMapping("/{id}")
    public ApiResponse<ScheduleDTO> getScheduleById(@PathVariable Long id) {
        Optional<ScheduleDTO> schedule = scheduleService.getScheduleById(id);
        return schedule.map(scheduleDTO -> ApiResponse.success("Schedule fetched successfully", scheduleDTO))
                .orElseGet(() -> ApiResponse.error("Schedule not found", null));
    }
}
