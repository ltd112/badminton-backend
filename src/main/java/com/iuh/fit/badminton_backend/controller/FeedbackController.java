package com.iuh.fit.badminton_backend.controller;

import com.iuh.fit.badminton_backend.dto.ApiResponse;
import com.iuh.fit.badminton_backend.dto.FeedbackDTO;
import com.iuh.fit.badminton_backend.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/feedbacks")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    /**
     * Lấy danh sách phản hồi của một học viên theo ID.
     * @param studentId ID của học viên.
     * @return danh sách phản hồi.
     */
    @GetMapping("/student/{studentId}")
    public ApiResponse<List<FeedbackDTO>> getFeedbacksByStudentId(@PathVariable Long studentId) {
        List<FeedbackDTO> feedbacks = feedbackService.getFeedbacksByStudentId(studentId);
        return ApiResponse.success("Danh sách phản hồi của học viên.", feedbacks);
    }

    /**
     * Lấy danh sách phản hồi cho một khóa học theo ID.
     * @param courseId ID của khóa học.
     * @return danh sách phản hồi.
     */
    @GetMapping("/course/{courseId}")
    public ApiResponse<List<FeedbackDTO>> getFeedbacksByCourseId(@PathVariable Long courseId) {
        List<FeedbackDTO> feedbacks = feedbackService.getFeedbacksByCourseId(courseId);
        return ApiResponse.success("Danh sách phản hồi cho khóa học.", feedbacks);
    }

    /**
     * Lấy phản hồi của một học viên cho một khóa học.
     * @param studentId ID của học viên.
     * @param courseId ID của khóa học.
     * @return phản hồi của học viên cho khóa học.
     */
    @GetMapping("/student/{studentId}/course/{courseId}")
    public ApiResponse<FeedbackDTO> getFeedbackByStudentAndCourse(@PathVariable Long studentId, @PathVariable Long courseId) {
        return feedbackService.getFeedbackByStudentAndCourse(studentId, courseId)
                .map(feedback -> ApiResponse.success("Phản hồi tìm thấy.", feedback))
                .orElseGet(() -> ApiResponse.error("Phản hồi không tồn tại.", null));
    }

    /**
     * Lấy danh sách phản hồi theo ngày gửi.
     * @param date ngày gửi phản hồi.
     * @return danh sách phản hồi.
     */
    @GetMapping("/date/{date}")
    public ApiResponse<List<FeedbackDTO>> getFeedbacksByDate(@PathVariable String date) {
        LocalDate feedbackDate = LocalDate.parse(date);
        List<FeedbackDTO> feedbacks = feedbackService.getFeedbacksByDate(feedbackDate);
        return ApiResponse.success("Danh sách phản hồi theo ngày.", feedbacks);
    }

    /**
     * Thêm mới hoặc cập nhật phản hồi.
     * @param feedbackDTO thông tin phản hồi.
     * @return phản hồi đã được lưu hoặc cập nhật.
     */
    @PostMapping
    public ApiResponse<FeedbackDTO> addFeedback(@RequestBody FeedbackDTO feedbackDTO) {
        FeedbackDTO savedFeedback = feedbackService.addFeedback(feedbackDTO);
        return ApiResponse.success("Phản hồi đã được lưu.", savedFeedback);
    }

    /**
     * Cập nhật phản hồi.
     * @param feedbackDTO thông tin phản hồi.
     * @return phản hồi đã được cập nhật.
     */
    @PutMapping
    public ApiResponse<FeedbackDTO> updateFeedback(@RequestBody FeedbackDTO feedbackDTO) {
        FeedbackDTO updatedFeedback = feedbackService.updateFeedback(feedbackDTO);
        return ApiResponse.success("Phản hồi đã được cập nhật.", updatedFeedback);
    }

    /**
     * Xóa phản hồi theo ID.
     * @param id ID của phản hồi.
     * @return thông báo thành công hoặc lỗi.
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteFeedback(@PathVariable Long id) {
        feedbackService.deleteFeedback(id);
        return ApiResponse.success("Phản hồi đã được xóa.", null);
    }

    /**
     * Lấy phản hồi theo ID.
     * @param id ID của phản hồi.
     * @return phản hồi nếu tồn tại.
     */
    @GetMapping("/{id}")
    public ApiResponse<FeedbackDTO> getFeedbackById(@PathVariable Long id) {
        FeedbackDTO feedback = feedbackService.getFeedbackById(id);
        if (feedback != null) {
            return ApiResponse.success("Phản hồi tìm thấy.", feedback);
        }
        return ApiResponse.error("Phản hồi không tồn tại.", null);
    }
}
