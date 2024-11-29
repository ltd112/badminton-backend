package com.iuh.fit.badminton_backend.service;

import com.iuh.fit.badminton_backend.mapper.GenericMapper;
import com.iuh.fit.badminton_backend.models.Lesson;
import com.iuh.fit.badminton_backend.dto.LessonDTO;
import com.iuh.fit.badminton_backend.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final GenericMapper genericMapper;

    @Autowired
    public LessonService(LessonRepository lessonRepository, GenericMapper genericMapper) {
        this.lessonRepository = lessonRepository;
        this.genericMapper = genericMapper;
    }

    /**
     * Tìm tất cả bài học của một khóa học theo ID khóa học
     * @param courseId ID của khóa học
     * @return danh sách bài học của khóa học
     */
    public List<LessonDTO> getLessonsByCourseId(Long courseId) {
        List<Lesson> lessons = lessonRepository.findByCourseId(courseId);
        return lessons.stream()
                .map(lesson -> genericMapper.convertToDto(lesson, LessonDTO.class))
                .toList();
    }

    /**
     * Tìm bài học của một khóa học theo thứ tự bài học
     * @param courseId ID của khóa học
     * @param lessonOrder thứ tự bài học trong khóa học
     * @return danh sách bài học theo thứ tự
     */
    public List<LessonDTO> getLessonsByCourseIdAndLessonOrder(Long courseId, int lessonOrder) {
        List<Lesson> lessons = lessonRepository.findByCourseIdAndLessonOrder(courseId, lessonOrder);
        return lessons.stream()
                .map(lesson -> genericMapper.convertToDto(lesson, LessonDTO.class))
                .toList();
    }

    /**
     * Lưu bài học mới hoặc cập nhật bài học nếu đã tồn tại
     * @param lessonDTO đối tượng DTO của bài học
     * @return bài học đã được lưu hoặc cập nhật
     */
    public LessonDTO saveOrUpdateLesson(LessonDTO lessonDTO) {
        Lesson lesson = genericMapper.convertToEntity(lessonDTO, Lesson.class);
        Lesson savedLesson = lessonRepository.save(lesson);
        return genericMapper.convertToDto(savedLesson, LessonDTO.class);
    }

    /**
     * Xóa bài học theo ID
     * @param id ID của bài học
     */
    public void deleteLesson(Long id) {
        lessonRepository.deleteById(id);
    }

    /**
     * Tìm bài học theo ID
     * @param id ID của bài học
     * @return bài học tìm được hoặc Optional.empty() nếu không tồn tại
     */
    public Optional<LessonDTO> getLessonById(Long id) {
        Optional<Lesson> lesson = lessonRepository.findById(id);
        return lesson.map(l -> genericMapper.convertToDto(l, LessonDTO.class));
    }
}
