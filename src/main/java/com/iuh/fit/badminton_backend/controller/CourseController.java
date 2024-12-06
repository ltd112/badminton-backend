package com.iuh.fit.badminton_backend.controller;

import com.iuh.fit.badminton_backend.dto.ApiResponse;
import com.iuh.fit.badminton_backend.dto.CourseDTO;
import com.iuh.fit.badminton_backend.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    /**
     * Lấy tất cả khóa học
     * @return ApiResponse chứa danh sách khóa học
     */
    @GetMapping("/all")
    public ApiResponse<List<CourseDTO>> getAllCourses() {
        List<CourseDTO> courseDTOs = courseService.getAllCourses();
        if (courseDTOs.isEmpty()) {
            return ApiResponse.error("Không tìm thấy khóa học nào", null);
        }
        return ApiResponse.success("Lấy danh sách khóa học thành công", courseDTOs);
    }

    /**
     * Lấy khóa học theo tên
     * @param courseName tên khóa học
     * @return ApiResponse chứa khóa học tìm được
     */
    @GetMapping("/name/{courseName}")
    public ApiResponse<CourseDTO> getCourseByName(@PathVariable String courseName) {
        Optional<CourseDTO> courseDTO = courseService.getCourseByName(courseName);
        return courseDTO.map(dto -> ApiResponse.success("Lấy khóa học thành công", dto))
                .orElseGet(() -> ApiResponse.error("Không tìm thấy khóa học với tên " + courseName, null));
    }

    /**
     * Lấy khóa học theo trạng thái
     * @param status trạng thái khóa học
     * @return ApiResponse chứa danh sách khóa học
     */
    @GetMapping("/status/{status}")
    public ApiResponse<List<CourseDTO>> getCoursesByStatus(@PathVariable String status) {
        List<CourseDTO> courseDTOs = courseService.getCoursesByStatus(status);
        if (courseDTOs.isEmpty()) {
            return ApiResponse.error("Không tìm thấy khóa học với trạng thái " + status, null);
        }
        return ApiResponse.success("Lấy danh sách khóa học theo trạng thái thành công", courseDTOs);
    }

    /**
     * Lấy khóa học theo ngày bắt đầu
     * @param startDate ngày bắt đầu khóa học
     * @return ApiResponse chứa danh sách khóa học
     */
    @GetMapping("/start-date/{startDate}")
    public ApiResponse<List<CourseDTO>> getCoursesByStartDate(@PathVariable String startDate) {
        LocalDate parsedStartDate = LocalDate.parse(startDate);
        List<CourseDTO> courseDTOs = courseService.getCoursesByStartDate(parsedStartDate);
        if (courseDTOs.isEmpty()) {
            return ApiResponse.error("Không tìm thấy khóa học bắt đầu vào ngày " + startDate, null);
        }
        return ApiResponse.success("Lấy danh sách khóa học theo ngày bắt đầu thành công", courseDTOs);
    }

    /**
     * Lấy khóa học theo cấp độ
     * @param level cấp độ khóa học
     * @return ApiResponse chứa danh sách khóa học
     */
    @GetMapping("/level/{level}")
    public ApiResponse<List<CourseDTO>> getCoursesByLevel(@PathVariable String level) {
        List<CourseDTO> courseDTOs = courseService.getCoursesByLevel(level);
        if (courseDTOs.isEmpty()) {
            return ApiResponse.error("Không tìm thấy khóa học với cấp độ " + level, null);
        }
        return ApiResponse.success("Lấy danh sách khóa học theo cấp độ thành công", courseDTOs);
    }

    /**
     * Lấy tất cả khóa học của một huấn luyện viên
     * @param coachId ID của huấn luyện viên
     * @return ApiResponse chứa danh sách khóa học của huấn luyện viên
     */
    @GetMapping("/coach/{coachId}")
    public ApiResponse<List<CourseDTO>> getCoursesByCoach(@PathVariable Long coachId) {
        List<CourseDTO> courseDTOs = courseService.getCoursesByCoach(coachId);
        if (courseDTOs.isEmpty()) {
            return ApiResponse.error("Không tìm thấy khóa học của huấn luyện viên với ID " + coachId, null);
        }
        return ApiResponse.success("Lấy danh sách khóa học của huấn luyện viên thành công", courseDTOs);
    }

    /**
     * Lưu hoặc cập nhật khóa học
     * @param courseDTO đối tượng DTO của khóa học
     * @return ApiResponse chứa khóa học đã lưu hoặc cập nhật
     */
    @PostMapping
    public ApiResponse<CourseDTO> saveOrUpdateCourse(@RequestBody CourseDTO courseDTO) {
        CourseDTO savedCourseDTO = courseService.saveOrUpdateCourse(courseDTO);
        return ApiResponse.success("Khóa học đã được lưu hoặc cập nhật thành công", savedCourseDTO);
    }

    /**
     * Xóa khóa học theo ID
     * @param id ID của khóa học
     * @return ApiResponse thông báo kết quả
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ApiResponse.success("Khóa học đã được xóa thành công", null);
    }
}
