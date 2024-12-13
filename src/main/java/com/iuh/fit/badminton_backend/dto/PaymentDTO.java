package com.iuh.fit.badminton_backend.dto;

import lombok.Builder;
import lombok.Data;

public class PaymentDTO {
    @Data
    @Builder
    public static class VNPayResponse {
        private String code;
        private String message;
        private String paymentUrl;

        public VNPayResponse(String code, String message, String paymentUrl) {
            this.code = code;
            this.message = message;
            this.paymentUrl = paymentUrl;
        }
    }
}