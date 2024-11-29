package com.iuh.fit.badminton_backend.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class ApiResponse<T> {
    private String status;
    private String message;
    private T data;
    private Object error;
    private String timestamp;

    public ApiResponse(String status, String message, T data, Object error, String timestamp) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.error = error;
        this.timestamp = timestamp;
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>("success", message, data, null, Instant.now().toString());
    }

    public static <T> ApiResponse<T> error(String message, Object errorDetails) {
        return new ApiResponse<>("error", message, null, errorDetails, Instant.now().toString());
    }

    // Getters and setters
}
