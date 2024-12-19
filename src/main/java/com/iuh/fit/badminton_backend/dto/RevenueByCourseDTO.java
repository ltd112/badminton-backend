package com.iuh.fit.badminton_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RevenueByCourseDTO {
    private String courseName;
    private double totalRevenue;
}