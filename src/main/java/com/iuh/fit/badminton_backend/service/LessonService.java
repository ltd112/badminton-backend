package com.iuh.fit.badminton_backend.service;

import com.iuh.fit.badminton_backend.dto.LessonDTO;
import com.iuh.fit.badminton_backend.mapper.GenericMapper;
import com.iuh.fit.badminton_backend.models.Lesson;
import com.iuh.fit.badminton_backend.repository.CourseRepository;
import com.iuh.fit.badminton_backend.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final GenericMapper genericMapper;
    private final CourseRepository courseRepository;

    @Autowired
    public LessonService(LessonRepository lessonRepository, GenericMapper genericMapper, CourseRepository courseRepository) {
        this.lessonRepository = lessonRepository;
        this.genericMapper = genericMapper;
        this.courseRepository = courseRepository;
    }

    public List<LessonDTO> getLessonsByCourseId(Long courseId) {
        Assert.notNull(courseId, "Course ID must not be null");
        List<Lesson> lessons = lessonRepository.findByCourseId(courseId);
        return lessons.stream()
                .map(lesson -> genericMapper.convertToDto(lesson, LessonDTO.class))
                .toList();
    }

    public List<LessonDTO> getLessonsByCourseIdAndLessonOrder(Long courseId, int lessonOrder) {
        Assert.notNull(courseId, "Course ID must not be null");
        List<Lesson> lessons = lessonRepository.findByCourseIdAndLessonOrder(courseId, lessonOrder);
        return lessons.stream()
                .map(lesson -> genericMapper.convertToDto(lesson, LessonDTO.class))
                .toList();
    }

    public LessonDTO saveOrUpdateLesson(LessonDTO lessonDTO) {
        Assert.notNull(lessonDTO, "LessonDTO must not be null");
        Lesson lesson = genericMapper.convertToEntity(lessonDTO, Lesson.class);
        Lesson savedLesson = lessonRepository.save(lesson);
        return genericMapper.convertToDto(savedLesson, LessonDTO.class);
    }

    public void deleteLesson(Long id) {
        Assert.notNull(id, "Lesson ID must not be null");
        lessonRepository.deleteById(id);
    }

    public Optional<LessonDTO> getLessonById(Long id) {
        Assert.notNull(id, "Lesson ID must not be null");
        Optional<Lesson> lesson = lessonRepository.findById(id);
        return lesson.map(l -> genericMapper.convertToDto(l, LessonDTO.class));
    }
}