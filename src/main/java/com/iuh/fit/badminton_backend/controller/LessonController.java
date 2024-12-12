package com.iuh.fit.badminton_backend.controller;

import com.iuh.fit.badminton_backend.dto.ApiResponse;
import com.iuh.fit.badminton_backend.dto.LessonDTO;
import com.iuh.fit.badminton_backend.service.LessonService;
import com.iuh.fit.badminton_backend.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    private final LessonService lessonService;
    private final S3Service s3Service;

    @Autowired
    public LessonController(LessonService lessonService, S3Service s3Service) {
        this.lessonService = lessonService;
        this.s3Service = s3Service;
    }
    //get all lessons
    @GetMapping("/course/{courseId}")
    public ApiResponse<List<LessonDTO>> getLessonsByCourseId(@PathVariable Long courseId) {
        List<LessonDTO> lessonDTOs = lessonService.getLessonsByCourseId(courseId);
        if (lessonDTOs.isEmpty()) {
            return ApiResponse.error("Không tìm thấy bài học cho khóa học với ID " + courseId, null);
        }
        return ApiResponse.success("Lấy danh sách bài học thành công", lessonDTOs);
    }
    //get lesson by course id and lesson order
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
    //upload lesson
    @PostMapping("/upload")
    public ApiResponse<LessonDTO> uploadLesson(@RequestParam("courseId") Long courseId,
                                               @RequestParam("title") String title,
                                               @RequestParam("content") String content,
                                               @RequestParam("lessonOrder") int lessonOrder,
                                               @RequestParam("videoFile") MultipartFile videoFile,
                                               @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            File video = Files.createTempFile("video", videoFile.getOriginalFilename()).toFile();
            videoFile.transferTo(video);
            String videoUrl = s3Service.uploadFile(video, "videos/" + videoFile.getOriginalFilename());

            File image = Files.createTempFile("image", imageFile.getOriginalFilename()).toFile();
            imageFile.transferTo(image);
            String imageUrl = s3Service.uploadFile(image, "images/" + imageFile.getOriginalFilename());

            LessonDTO lessonDTO = new LessonDTO();
            lessonDTO.setCourseId(courseId);
            lessonDTO.setTitle(title);
            lessonDTO.setContent(content);
            lessonDTO.setLessonOrder(lessonOrder);
            lessonDTO.setVideoUrl(videoUrl);
            lessonDTO.setImgUrl(imageUrl);

            LessonDTO savedLesson = lessonService.saveOrUpdateLesson(lessonDTO);
            return ApiResponse.success("Lesson uploaded successfully", savedLesson);
        } catch (Exception e) {
            return ApiResponse.error("Error uploading lesson", null);
        }
    }

    //update lesson(title, content, video, image)
    @PutMapping("/update")
    public ApiResponse<LessonDTO> updateLesson(@RequestBody LessonDTO lessonDTO) {
        LessonDTO updatedLesson = lessonService.saveOrUpdateLesson(lessonDTO);
        return ApiResponse.success("Bài học đã được cập nhật thành công", updatedLesson);
    }
    //delete lesson
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteLesson(@PathVariable Long id) {
        lessonService.deleteLesson(id);
        return ApiResponse.success("Bài học đã được xóa thành công", null);
    }
    //get lesson by id
    @GetMapping("/{id}")
    public ApiResponse<LessonDTO> getLessonById(@PathVariable Long id) {
        Optional<LessonDTO> lessonDTO = lessonService.getLessonById(id);
        return lessonDTO.map(dto -> ApiResponse.success("Lấy thông tin bài học thành công", dto))
                .orElseGet(() -> ApiResponse.error("Không tìm thấy bài học với ID " + id, null));
    }
}