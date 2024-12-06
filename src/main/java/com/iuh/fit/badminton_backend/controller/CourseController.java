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

    //tìm tất cả feedback khi biết id và feedbackDate
    // Tìm tất cả khóa học có feedback id

    @PostMapping("/feedback/{id}/{feedbackDate}")
    public ApiResponse<List<CourseDTO>> getCourseByFeedbackIdAndFeedbackDate(@PathVariable int rating, @PathVariable LocalDate feedbackDate) {
        List<CourseDTO> courseDTOs = courseService.getCoursesByRatingAndFeedbackDate(rating, feedbackDate);
        if (courseDTOs.isEmpty()) {
            return ApiResponse.error("Không tìm thấy khóa học nào", null);
        }
        return ApiResponse.success("Lấy danh sách khóa học thành công", courseDTOs);
    }
}
