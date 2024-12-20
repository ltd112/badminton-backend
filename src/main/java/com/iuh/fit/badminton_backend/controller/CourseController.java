package com.iuh.fit.badminton_backend.controller;

import com.iuh.fit.badminton_backend.dto.ApiResponse;
import com.iuh.fit.badminton_backend.dto.CourseDTO;
import com.iuh.fit.badminton_backend.dto.RevenueByCourseDTO;
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
     * Lấy khóa học theo ID
     * @param id ID của khóa học
     * @return ApiResponse chứa khóa học tìm được
     */
    @GetMapping("/{id}")
    public ApiResponse<CourseDTO> getCourseById(@PathVariable Long id) {
        Optional<CourseDTO> courseDTO = courseService.getCourseById(id);
        return courseDTO.map(dto -> ApiResponse.success("Lấy khóa học thành công", dto))
                .orElseGet(() -> ApiResponse.error("Không tìm thấy khóa học với ID " + id, null));
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
        //kiểm tra xem khóa học có tồn tại không
        Optional<CourseDTO> courseDTO = courseService.getCourseById(id);
        //return error nếu không tồn tại
        if (courseDTO.isEmpty()) {
            return ApiResponse.error("Không tìm thấy khóa học với ID " + id, null);
        }
        courseService.deleteCourse(id);

        return ApiResponse.success("Khóa học đã được xóa thành công", null);
    }

    //tìm tất cả feedback khi biết id và feedbackDate
    // Tìm tất cả khóa học có feedback id

    // Lấy dánh sách khóa học có rating cao nhất trong khoảng thời gian
    @GetMapping("/highest-rating-course")
    public ApiResponse<List<CourseDTO>> findCoursesWithHighestRatingsInPeriod(@RequestParam String startDate, @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        List<CourseDTO> courseDTOs = courseService.getCoursesWithHighestRatingsInPeriod(start, end);
        if (courseDTOs.isEmpty()) {
            return ApiResponse.error("Không tìm thấy khóa học nào", null);
        }
        return ApiResponse.success("Lấy danh sách khóa học có rating cao nhất trong khoảng thời gian thành công", courseDTOs);
    }
    //tìm tất cả khóa học có rating cao nhất
    @GetMapping("/highest-rated-courses-all")
    public ApiResponse<List<CourseDTO>> findCoursesWithHighestRatings() {
        List<CourseDTO> courseDTOs = courseService.getCoursesWithHighestRatings();
        if (courseDTOs.isEmpty()) {
            return ApiResponse.error("Không tìm thấy khóa học nào", null);
        }
        return ApiResponse.success("Lấy danh sách khóa học có rating cao nhất thành công", courseDTOs);
    }

    // Đếm số lượng khóa học
    @GetMapping("/count")
    public ApiResponse<Long> countCourses() {
        long count = courseService.countCourses();
        return ApiResponse.success("Số lượng khóa học", count);
    }

    //tìm khóa học theo tên
    @GetMapping("/search-course")
    public ApiResponse<List<CourseDTO>> searchCourse(@RequestParam String keyword) {
        List<CourseDTO> courseDTOs = courseService.searchCoursesByName(keyword);
        if (courseDTOs.isEmpty()) {
            return ApiResponse.error("Không tìm thấy khóa học nào", null);
        }
        return ApiResponse.success("Tìm kiếm khóa học thành công", courseDTOs);
    }
    //tìm khóa học có tổng phí cao nhất
    @GetMapping("/highest-total-fee-paid")
    public ApiResponse<List<CourseDTO>> getCourseWithHighestTotalFeePaid() {
        List<CourseDTO> courseDTOs = courseService.getCourseWithHighestTotalFeePaid();
        if (courseDTOs.isEmpty()) {
            return ApiResponse.error("Không tìm thấy khóa học nào", null);
        }
        return ApiResponse.success("Lấy khóa học có tổng phí cao nhất thành công", courseDTOs);
    }

    @GetMapping("/highest-purchase-count")
    public ApiResponse<List<CourseDTO>> getCourseWithHighestPurchaseCount() {
        List<CourseDTO> courseDTOs = courseService.getCourseWithHighestPurchaseCount();
        if (courseDTOs.isEmpty()) {
            return ApiResponse.error("Không tìm thấy khóa học nào", null);
        }
        return ApiResponse.success("Lấy khóa học có số lượt mua cao nhất thành công", courseDTOs);
    }

    @GetMapping("/revenue-by-course-in-period")
    public ApiResponse<List<RevenueByCourseDTO>> getRevenueByCourseInPeriod(@RequestParam String startDate, @RequestParam String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        List<RevenueByCourseDTO> revenueDTOs = courseService.getRevenueByCourseInPeriod(start, end);
        if (revenueDTOs.isEmpty()) {
            return ApiResponse.error("Không tìm thấy dữ liệu", null);
        }
        return ApiResponse.success("Lấy doanh thu theo khóa học trong khoảng thời gian thành công", revenueDTOs);
    }
}
