package com.iuh.fit.badminton_backend.service;

import com.iuh.fit.badminton_backend.dto.CourseDTO;
import com.iuh.fit.badminton_backend.dto.UserDTO;
import com.iuh.fit.badminton_backend.mapper.GenericMapper;
import com.iuh.fit.badminton_backend.models.Course;
import com.iuh.fit.badminton_backend.models.Feedback;
import com.iuh.fit.badminton_backend.dto.FeedbackDTO;
import com.iuh.fit.badminton_backend.models.User;
import com.iuh.fit.badminton_backend.repository.CourseRepository;
import com.iuh.fit.badminton_backend.repository.FeedbackRepository;
import com.iuh.fit.badminton_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final GenericMapper genericMapper;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository, GenericMapper genericMapper, CourseRepository courseRepository, UserRepository userRepository) {
        this.feedbackRepository = feedbackRepository;
        this.genericMapper = genericMapper;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    /**
     * Tìm tất cả phản hồi của một học viên theo ID học viên
     * @param studentId ID của học viên
     * @return danh sách phản hồi của học viên
     */
    public List<FeedbackDTO> getFeedbacksByStudentId(Long studentId) {
        List<Feedback> feedbacks = feedbackRepository.findByStudentId(studentId);
        return feedbacks.stream()
                .map(feedback -> genericMapper.convertToDto(feedback, FeedbackDTO.class))
                .toList();
    }

    /**
     * Tìm tất cả phản hồi cho một khóa học theo ID khóa học
     * @param courseId ID của khóa học
     * @return danh sách phản hồi cho khóa học
     */
    public List<FeedbackDTO> getFeedbacksByCourseId(Long courseId) {
        List<Feedback> feedbacks = feedbackRepository.findByCourseId(courseId);
        return feedbacks.stream()
                .map(feedback -> genericMapper.convertToDto(feedback, FeedbackDTO.class))
                .toList();
    }

    /**
     * Tìm phản hồi của một học viên cho một khóa học cụ thể
     * @param studentId ID của học viên
     * @param courseId ID của khóa học
     * @return phản hồi của học viên cho khóa học
     */
    public Optional<FeedbackDTO> getFeedbackByStudentAndCourse(Long studentId, Long courseId) {
        Optional<Feedback> feedback = feedbackRepository.findByStudentIdAndCourseId(studentId, courseId);
        return feedback.map(f -> genericMapper.convertToDto(f, FeedbackDTO.class));
    }

    /**
     * Tìm tất cả phản hồi của một học viên theo đánh giá (rating)
     * @param studentId ID của học viên
     * @param rating mức đánh giá từ 1 đến 5
     * @return danh sách phản hồi của học viên theo rating
     */
    public List<FeedbackDTO> getFeedbacksByStudentIdAndRating(Long studentId, int rating) {
        List<Feedback> feedbacks = feedbackRepository.findByStudentIdAndRating(studentId, rating);
        return feedbacks.stream()
                .map(feedback -> genericMapper.convertToDto(feedback, FeedbackDTO.class))
                .toList();
    }

    /**
     * Tìm tất cả phản hồi theo ngày gửi
     * @param feedbackDate ngày gửi phản hồi
     * @return danh sách phản hồi theo ngày gửi
     */
    public List<FeedbackDTO> getFeedbacksByDate(LocalDate feedbackDate) {
        List<Feedback> feedbacks = feedbackRepository.findByFeedbackDate(feedbackDate);
        return feedbacks.stream()
                .map(feedback -> genericMapper.convertToDto(feedback, FeedbackDTO.class))
                .toList();
    }

    /**
     * Lưu phản hồi mới hoặc cập nhật phản hồi nếu đã tồn tại
     * @param feedbackDTO đối tượng DTO của phản hồi
     * @return phản hồi đã được lưu hoặc cập nhật
     */
    public FeedbackDTO addFeedback(FeedbackDTO feedbackDTO) {
        // Check if feedback already exists
        Optional<Feedback> existingFeedback = feedbackRepository.findByStudentIdAndCourseId(feedbackDTO.getStudentId(), feedbackDTO.getCourseId());
        if (existingFeedback.isEmpty()) {
            // Check if course exists, if not, create new course
            Course course = courseRepository.findById(feedbackDTO.getCourseId()).orElseGet(() -> {
                Course newCourse = new Course();
                newCourse.setId(feedbackDTO.getCourseId());
                newCourse.setCourseName("New Course"); // Set other necessary fields
                return courseRepository.save(newCourse);
            });

            // Check if user exists, if not, create new user
            User user = userRepository.findById(feedbackDTO.getStudentId()).orElseGet(() -> {
                User newUser = new User();
                newUser.setId(feedbackDTO.getStudentId());
                newUser.setUsername("New User"); // Set other necessary fields
                return userRepository.save(newUser);
            });

            // Convert and save feedback
            Feedback feedback = genericMapper.convertToEntity(feedbackDTO, Feedback.class);
            feedback.setCourse(course);
            feedback.setStudent(user);
            Feedback savedFeedback = feedbackRepository.save(feedback);
            return genericMapper.convertToDto(savedFeedback, FeedbackDTO.class);
        }
        // If feedback already exists, update feedback
        return updateFeedback(feedbackDTO);
    }

    public FeedbackDTO getFeedbackById(Long id) {
        Optional<Feedback> feedback = feedbackRepository.findById(id);
        return feedback.map(f -> genericMapper.convertToDto(f, FeedbackDTO.class)).orElse(null);
    }

    public FeedbackDTO updateFeedback(FeedbackDTO feedbackDTO) {
        // check if feedback already exists
        Optional<Feedback> existingFeedback = feedbackRepository.findById(feedbackDTO.getId());
        // if feedback already exists, update feedback
        if (existingFeedback.isPresent()) {
            //check course and student already exists
            Feedback feedback = genericMapper.convertToEntity(feedbackDTO, Feedback.class);
            Feedback updatedFeedback = feedbackRepository.save(feedback);
            return genericMapper.convertToDto(updatedFeedback, FeedbackDTO.class);
        }
        // if feedback does not exist, create new feedback using fun addFeedback
        return addFeedback(feedbackDTO);
    }

    /**
     * Xóa một phản hồi theo ID
     * @param id ID của phản hồi
     */
    public void deleteFeedback(Long id) {
        feedbackRepository.deleteById(id);
    }
    //get feedback by studentId and courseId
    public Optional<Feedback> getFeedbacksByCourseIdandStudentId(Long studentId, Long courseId) {
        return feedbackRepository.findByStudentIdAndCourseId(studentId, courseId);
    }
}

