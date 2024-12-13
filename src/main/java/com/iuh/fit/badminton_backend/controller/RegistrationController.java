package com.iuh.fit.badminton_backend.controller;

import com.iuh.fit.badminton_backend.dto.RegistrationDTO;
import com.iuh.fit.badminton_backend.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/registrations")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    // Get all registrations
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<RegistrationDTO>> getRegistrationsByStudentId(@PathVariable Long studentId) {
        List<RegistrationDTO> registrations = registrationService.getRegistrationsByStudentId(studentId);
        return new ResponseEntity<>(registrations, HttpStatus.OK);
    }
    // Get registrations by course ID
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<RegistrationDTO>> getRegistrationsByCourseId(@PathVariable Long courseId) {
        List<RegistrationDTO> registrations = registrationService.getRegistrationsByCourseId(courseId);
        return new ResponseEntity<>(registrations, HttpStatus.OK);
    }
    // Get registrations by student ID and course ID
    @GetMapping("/student/{studentId}/course/{courseId}")
    public ResponseEntity<List<RegistrationDTO>> getRegistrationsByStudentIdAndCourseId(@PathVariable Long studentId, @PathVariable Long courseId) {
        List<RegistrationDTO> registrations = registrationService.getRegistrationsByStudentIdAndCourseId(studentId, courseId);
        return new ResponseEntity<>(registrations, HttpStatus.OK);
    }
    // Get registrations by payment status
    @GetMapping("/payment-status/{paymentStatus}")
    public ResponseEntity<List<RegistrationDTO>> getRegistrationsByPaymentStatus(@PathVariable boolean paymentStatus) {
        List<RegistrationDTO> registrations = registrationService.getRegistrationsByPaymentStatus(paymentStatus);
        return new ResponseEntity<>(registrations, HttpStatus.OK);
    }
    // Get registrations by course ID and registration date
    @GetMapping("/course/{courseId}/date/{registrationDate}")
    public ResponseEntity<List<RegistrationDTO>> getRegistrationsByCourseIdAndRegistrationDate(@PathVariable Long courseId, @PathVariable LocalDate registrationDate) {
        List<RegistrationDTO> registrations = registrationService.getRegistrationsByCourseIdAndRegistrationDate(courseId, registrationDate);
        return new ResponseEntity<>(registrations, HttpStatus.OK);
    }
    // Save or update registration
    @PostMapping
    public ResponseEntity<RegistrationDTO> saveOrUpdateRegistration(@RequestBody RegistrationDTO registrationDTO) {
        RegistrationDTO savedRegistration = registrationService.saveOrUpdateRegistration(registrationDTO);
        return new ResponseEntity<>(savedRegistration, HttpStatus.CREATED);
    }
    // Delete registration
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegistration(@PathVariable Long id) {
        registrationService.deleteRegistration(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    // Get registration by ID
    @GetMapping("/{id}")
    public ResponseEntity<RegistrationDTO> getRegistrationById(@PathVariable Long id) {
        Optional<RegistrationDTO> registration = registrationService.getRegistrationById(id);
        return registration.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}