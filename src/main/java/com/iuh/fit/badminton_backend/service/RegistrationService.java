package com.iuh.fit.badminton_backend.service;

import com.iuh.fit.badminton_backend.mapper.GenericMapper;
import com.iuh.fit.badminton_backend.models.Registration;
import com.iuh.fit.badminton_backend.dto.RegistrationDTO;
import com.iuh.fit.badminton_backend.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final GenericMapper genericMapper;

    @Autowired
    public RegistrationService(RegistrationRepository registrationRepository, GenericMapper genericMapper) {
        this.registrationRepository = registrationRepository;
        this.genericMapper = genericMapper;
    }

    /**
     * Find all registrations by student ID.
     * @param studentId ID of the student.
     * @return list of registration DTOs.
     */
    public List<RegistrationDTO> getRegistrationsByStudentId(Long studentId) {
        List<Registration> registrations = registrationRepository.findByStudentId(studentId);
        return registrations.stream()
                .map(registration -> genericMapper.convertToDto(registration, RegistrationDTO.class))
                .toList();
    }

    /**
     * Find all registrations by course ID.
     * @param courseId ID of the course.
     * @return list of registration DTOs.
     */
    public List<RegistrationDTO> getRegistrationsByCourseId(Long courseId) {
        List<Registration> registrations = registrationRepository.findByCourseId(courseId);
        return registrations.stream()
                .map(registration -> genericMapper.convertToDto(registration, RegistrationDTO.class))
                .toList();
    }

    /**
     * Find registrations by student ID and course ID.
     * @param studentId ID of the student.
     * @param courseId ID of the course.
     * @return list of registration DTOs.
     */
    public List<RegistrationDTO> getRegistrationsByStudentIdAndCourseId(Long studentId, Long courseId) {
        List<Registration> registrations = registrationRepository.findByStudentIdAndCourseId(studentId, courseId);
        return registrations.stream()
                .map(registration -> genericMapper.convertToDto(registration, RegistrationDTO.class))
                .toList();
    }

    /**
     * Find registrations by payment status.
     * @param paymentStatus true for paid, false for unpaid.
     * @return list of registration DTOs.
     */
    public List<RegistrationDTO> getRegistrationsByPaymentStatus(boolean paymentStatus) {
        List<Registration> registrations = registrationRepository.findByPaymentStatus(paymentStatus);
        return registrations.stream()
                .map(registration -> genericMapper.convertToDto(registration, RegistrationDTO.class))
                .toList();
    }

    /**
     * Find registrations by course ID and registration date.
     * @param courseId ID of the course.
     * @param registrationDate date the registration occurred.
     * @return list of registration DTOs.
     */
    public List<RegistrationDTO> getRegistrationsByCourseIdAndRegistrationDate(Long courseId, LocalDate registrationDate) {
        List<Registration> registrations = registrationRepository.findByCourseIdAndRegistrationDate(courseId, registrationDate);
        return registrations.stream()
                .map(registration -> genericMapper.convertToDto(registration, RegistrationDTO.class))
                .toList();
    }

    /**
     * Save or update a registration.
     * @param registrationDTO DTO of the registration.
     * @return saved or updated registration DTO.
     */
    public RegistrationDTO saveOrUpdateRegistration(RegistrationDTO registrationDTO) {
        Registration registration = genericMapper.convertToEntity(registrationDTO, Registration.class);
        Registration savedRegistration = registrationRepository.save(registration);
        return genericMapper.convertToDto(savedRegistration, RegistrationDTO.class);
    }

    /**
     * Delete a registration by ID.
     * @param id ID of the registration.
     */
    public void deleteRegistration(Long id) {
        registrationRepository.deleteById(id);
    }

    /**
     * Find a registration by ID.
     * @param id ID of the registration.
     * @return an Optional containing the registration DTO if found.
     */
    public Optional<RegistrationDTO> getRegistrationById(Long id) {
        Optional<Registration> registration = registrationRepository.findById(id);
        return registration.map(reg -> genericMapper.convertToDto(reg, RegistrationDTO.class));
    }
}