package com.iuh.fit.badminton_backend.controller;

import com.iuh.fit.badminton_backend.dto.ApiResponse;
import com.iuh.fit.badminton_backend.dto.RegistrationDTO;
import com.iuh.fit.badminton_backend.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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
    public ResponseEntity<Optional<RegistrationDTO>> getRegistrationsByStudentIdAndCourseId(@PathVariable Long studentId, @PathVariable Long courseId) {
        Optional<RegistrationDTO> registration = registrationService.getRegistrationsByStudentIdAndCourseId(studentId, courseId);
        return new ResponseEntity<>(registration, HttpStatus.OK);
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
    @PostMapping
    public ResponseEntity<ApiResponse<RegistrationDTO>> saveOrUpdateRegistration(@RequestBody RegistrationDTO registrationDTO) {
        Optional<RegistrationDTO> existingRegistrations = registrationService.getRegistrationsByStudentIdAndCourseId(registrationDTO.getStudentId(), registrationDTO.getCourseId());
        if (existingRegistrations.isPresent()) {
            System.out.println(existingRegistrations.get());
            return ResponseEntity.status(201)
                    .body(ApiResponse.error("Khóa học đã được thanh toán trước đó", null));
        }
        RegistrationDTO savedRegistration = registrationService.saveOrUpdateRegistration(registrationDTO);
        return ResponseEntity.status(200)
                .body(ApiResponse.success("Thanh toán khóa học thành công", savedRegistration));
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

    // Get total fee pay
    @GetMapping("/total-fee-pay")
    public ApiResponse<Double> getTotalFeePay() {
        double totalFeePay = registrationService.getTotalFeePay();
        return ApiResponse.success("Tổng phí đăng ký", totalFeePay);
    }
    // Get monthly revenue
    @GetMapping("/monthly-revenue")
    public ApiResponse<Double> getMonthlyRevenue(@RequestParam int year, @RequestParam int month) {
        double monthlyRevenue = registrationService.getMonthlyRevenue(year, month);
        return ApiResponse.success("Doanh thu hàng tháng", monthlyRevenue);
    }
    @GetMapping("/revenue-between-months")
    public ApiResponse<Map<String, Double>> getRevenueBetweenMonths(@RequestParam int startYear, @RequestParam int startMonth, @RequestParam int endYear, @RequestParam int endMonth) {
        Map<String, Double> revenue = registrationService.getRevenueBetweenMonths(startYear, startMonth, endYear, endMonth);
        return ApiResponse.success("Doanh thu từ tháng " + startMonth + " đến tháng " + endMonth, revenue);
    }


}