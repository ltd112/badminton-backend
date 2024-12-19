package com.iuh.fit.badminton_backend.service;

import com.iuh.fit.badminton_backend.dto.RevenueByCourseDTO;
import com.iuh.fit.badminton_backend.mapper.GenericMapper;
import com.iuh.fit.badminton_backend.models.Course;
import com.iuh.fit.badminton_backend.dto.CourseDTO;
import com.iuh.fit.badminton_backend.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final GenericMapper genericMapper;

    @Autowired
    public CourseService(CourseRepository courseRepository, GenericMapper genericMapper) {
        this.courseRepository = courseRepository;
        this.genericMapper = genericMapper;
    }

    /**
     * Tìm tất cả các khóa học
     *
     * @return danh sách các khóa học
     */
    public List<CourseDTO> getAllCourses() {
        List<Object[]> results = courseRepository.findAllCoursesWithAvgRating();
        return results.stream()
                .map(result -> {
                    Course course = (Course) result[0];
                    double avgRating = (double) result[1];
                    CourseDTO courseDTO = genericMapper.convertToDto(course, CourseDTO.class);
                    courseDTO.setAverageRating(avgRating); // Set the average rating
                    courseDTO.setLessons(null); // Exclude lessons
                    return courseDTO;
                })
                .collect(Collectors.toList());
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

    public List<CourseDTO> getCoursesWithHighestRatingsInPeriod(LocalDate startDate, LocalDate endDate) {
        List<Object[]> results = courseRepository.findCoursesWithHighestRatingsInPeriod(startDate, endDate);
        return results.stream()
                .map(result -> {
                    Course course = (Course) result[0];
                    double avgRating = (double) result[1];
                    CourseDTO courseDTO = genericMapper.convertToDto(course, CourseDTO.class);
                    courseDTO.setAverageRating(avgRating); // Set the average rating
                    courseDTO.setLessons(null); // Exclude lessons
                    return courseDTO;
                })
                .collect(Collectors.toList());
    }

     public List<CourseDTO> getCoursesWithHighestRatings() {
        return courseRepository.findCoursesWithHighestRatings().stream()
                .map(result -> {
                    Course course = (Course) result[0];
                    double avgRating = (double) result[1];
                    CourseDTO courseDTO = genericMapper.convertToDto(course, CourseDTO.class);
                    courseDTO.setAverageRating(avgRating); // Set the average rating
                    courseDTO.setLessons(null); // Exclude lessons
                    return courseDTO;
                })
                .collect(Collectors.toList());
    }
    /**
     * Tìm khóa học theo ID
     * @param id ID của khóa học
     * @return khóa học nếu tồn tại, null nếu không
     */
    public Optional<CourseDTO> getCourseById(Long id) {
        Optional<Course> course = courseRepository.findById(id);
        return course.map(c -> genericMapper.convertToDto(c, CourseDTO.class));
    }

    public long countCourses() {
        return courseRepository.count();
    }

    public List<CourseDTO> searchCoursesByName(String keyword) {
        List<Object[]> results = courseRepository.findCoursesByNameContainingWithAvgRating(keyword);
        return results.stream()
                .map(result -> {
                    Course course = (Course) result[0];
                    double avgRating = (double) result[1];
                    CourseDTO courseDTO = genericMapper.convertToDto(course, CourseDTO.class);
                    courseDTO.setAverageRating(avgRating); // Set the average rating
                    courseDTO.setLessons(null); // Exclude lessons
                    return courseDTO;
                })
                .collect(Collectors.toList());
    }

    public List<CourseDTO> getCourseWithHighestTotalFeePaid() {
        List<Object[]> results = courseRepository.findCourseWithHighestTotalFeePaid();
        return results.stream()
                .map(result -> {
                    Course course = (Course) result[0];
                    double totalFeePaid = (double) result[1];
                    CourseDTO courseDTO = genericMapper.convertToDto(course, CourseDTO.class);
                    courseDTO.setTotalFeePaid(totalFeePaid); // Set the total fee paid
                    return courseDTO;
                })
                .collect(Collectors.toList());
    }
    //
    public List<CourseDTO> getCourseWithHighestPurchaseCount() {
        List<Object[]> results = courseRepository.findCourseWithHighestPurchaseCount();
        return results.stream()
                .map(result -> {
                    Course course = (Course) result[0];
                    long purchaseCount = (long) result[1];
                    CourseDTO courseDTO = genericMapper.convertToDto(course, CourseDTO.class);
                    courseDTO.setPurchaseCount(purchaseCount); // Set the purchase count
                    return courseDTO;
                })
                .collect(Collectors.toList());
    }
    public List<RevenueByCourseDTO> getRevenueByCourseInPeriod(LocalDate startDate, LocalDate endDate) {
        List<Object[]> results = courseRepository.findRevenueByCourseInPeriod(startDate, endDate);
        return results.stream()
                .map(result -> new RevenueByCourseDTO((String) result[0], (Double) result[1]))
                .collect(Collectors.toList());
    }

}
