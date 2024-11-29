package com.iuh.fit.badminton_backend.controller;

import com.iuh.fit.badminton_backend.dto.ApiResponse;
import com.iuh.fit.badminton_backend.dto.LessonDTO;
import com.iuh.fit.badminton_backend.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    private final LessonService lessonService;

    @Autowired
    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    /**
     * Lấy tất cả bài học của một khóa học theo ID khóa học
     * @param courseId ID của khóa học
     * @return ApiResponse chứa danh sách bài học
     */
    @GetMapping("/course/{courseId}")
    public ApiResponse<List<LessonDTO>> getLessonsByCourseId(@PathVariable Long courseId) {
        List<LessonDTO> lessonDTOs = lessonService.getLessonsByCourseId(courseId);
        if (lessonDTOs.isEmpty()) {
            return ApiResponse.error("Không tìm thấy bài học cho khóa học với ID " + courseId, null);
        }
        return ApiResponse.success("Lấy danh sách bài học thành công", lessonDTOs);
    }

    /**
     * Lấy bài học theo khóa học và thứ tự bài học
     * @param courseId ID của khóa học
     * @param lessonOrder thứ tự bài học trong khóa học
     * @return ApiResponse chứa danh sách bài học
     */
    @GetMapping("/course/{courseId}/order/{lessonOrder}")
    public ApiResponse<List<LessonDTO>> getLessonsByCourseIdAndLessonOrder(
            @PathVariable Long courseId,
            @PathVariable int lessonOrder) {
        List<LessonDTO> lessonDTOs = lessonService.getLessonsByCourseIdAndLessonOrder(courseId, lessonOrder);
        if (lessonDTOs.isEmpty()) {
            return ApiResponse.error("Không tìm thấy bài học theo thứ tự " + lessonOrder + " của khóa học với ID " + courseId, null);
        }
        return ApiResponse.success("Lấy danh sách bài học theo thứ tự thành công", lessonDTOs);
    }

    /**
     * Lưu hoặc cập nhật bài học
     * @param lessonDTO đối tượng DTO của bài học
     * @return ApiResponse chứa bài học đã lưu hoặc cập nhật
     */
    @PostMapping
    public ApiResponse<LessonDTO> saveOrUpdateLesson(@RequestBody LessonDTO lessonDTO) {
        LessonDTO savedLessonDTO = lessonService.saveOrUpdateLesson(lessonDTO);
        return ApiResponse.success("Bài học đã được lưu hoặc cập nhật thành công", savedLessonDTO);
    }

    /**
     * Xóa bài học theo ID
     * @param id ID của bài học
     * @return ApiResponse thông báo kết quả
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteLesson(@PathVariable Long id) {
        lessonService.deleteLesson(id);
        return ApiResponse.success("Bài học đã được xóa thành công", null);
    }

    /**
     * Tìm bài học theo ID
     * @param id ID của bài học
     * @return ApiResponse chứa bài học tìm được
     */
    @GetMapping("/{id}")
    public ApiResponse<LessonDTO> getLessonById(@PathVariable Long id) {
        Optional<LessonDTO> lessonDTO = lessonService.getLessonById(id);
        return lessonDTO.map(dto -> ApiResponse.success("Lấy thông tin bài học thành công", dto))
                .orElseGet(() -> ApiResponse.error("Không tìm thấy bài học với ID " + id, null));
    }
}
