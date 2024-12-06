package com.iuh.fit.badminton_backend.service;

import com.iuh.fit.badminton_backend.mapper.GenericMapper;
import com.iuh.fit.badminton_backend.models.Course;
import com.iuh.fit.badminton_backend.dto.CourseDTO;
import com.iuh.fit.badminton_backend.models.Feedback;
import com.iuh.fit.badminton_backend.repository.CourseRepository;
import com.iuh.fit.badminton_backend.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final GenericMapper genericMapper;
    private final FeedbackRepository feedbackRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, GenericMapper genericMapper, FeedbackRepository feedbackRepository) {
        this.courseRepository = courseRepository;
        this.genericMapper = genericMapper;
        this.feedbackRepository = feedbackRepository;
    }

    /**
     * Tìm tất cả các khóa học
     * @return danh sách các khóa học
     */
    public List<CourseDTO> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream()
                .map(course -> genericMapper.convertToDto(course, CourseDTO.class))
                .toList();
    }

    /**
     * Tìm khóa học theo tên
     * @param courseName tên khóa học
     * @return khóa học nếu tồn tại, null nếu không
     */
    public Optional<CourseDTO> getCourseByName(String courseName) {
        Optional<Course> course = courseRepository.findByCourseName(courseName);
        return course.map(c -> genericMapper.convertToDto(c, CourseDTO.class));
    }


    /**
     * Tìm tất cả khóa học theo cấp độ
     * @param level cấp độ khóa học
     * @return danh sách khóa học
     */
    public List<CourseDTO> getCoursesByLevel(String level) {
        List<Course> courses = courseRepository.findByLevel(level);
        return courses.stream()
                .map(course -> genericMapper.convertToDto(course, CourseDTO.class))
                .toList();
    }

    /**
     * Lưu một khóa học mới hoặc cập nhật khóa học nếu đã tồn tại
     * @param courseDto đối tượng DTO khóa học
     * @return khóa học đã được lưu hoặc cập nhật
     */
    public CourseDTO saveOrUpdateCourse(CourseDTO courseDto) {
        Course course = genericMapper.convertToEntity(courseDto, Course.class);
        Course savedCourse = courseRepository.save(course);
        return genericMapper.convertToDto(savedCourse, CourseDTO.class);
    }

    /**
     * Xóa một khóa học theo ID
     * @param id id của khóa học
     */
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    //tìm tất cả feedback khi biết id và feedbackDate
    // Tìm tất cả khóa học có feedback id

    //getCoursesWithHighestRatings
    public List<CourseDTO> getCoursesWithHighestRatingsInPeriod(LocalDate startDate, LocalDate endDate) {
        List<Course> courses = courseRepository.findCoursesWithHighestRatingsInPeriod(startDate, endDate);
        return courses.stream()
                .map(course -> genericMapper.convertToDto(course, CourseDTO.class))
                .toList();
    }

    public List<CourseDTO> getCoursesWithHighestRatings() {
        return courseRepository.findCoursesWithHighestRatings().stream()
                .map(course -> genericMapper.convertToDto(course, CourseDTO.class))
                .toList();
    }
}
