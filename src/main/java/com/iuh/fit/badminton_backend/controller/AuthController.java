package com.iuh.fit.badminton_backend.controller;

import com.iuh.fit.badminton_backend.dto.ApiResponse;
import com.iuh.fit.badminton_backend.dto.UserDTO;
import com.iuh.fit.badminton_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDTO>> register(@RequestBody UserDTO userDTO) {
        // Kiểm tra username đã tồn tại
        if (userService.getUserByUsername(userDTO.getUsername()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Tên đăng nhập đã tồn tại", null));
        }

        // Kiểm tra email đã tồn tại
        if (userService.getUserByEmail(userDTO.getEmail()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Email đã tồn tại", null));
        }

        // Tạo người dùng mới
        UserDTO savedUser = userService.addUsers(userDTO);
        return ResponseEntity.status(201)
                .body(ApiResponse.success("Đăng ký thành công", savedUser));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody UserDTO userDTO) {
        // Kiểm tra username
        Optional<UserDTO> userOptional = userService.getUserByUsername(userDTO.getUsername());
        if (userOptional.isEmpty()) {

            return ResponseEntity.status(404)
                    .body(ApiResponse.error("Tên đăng nhập không tồn tại", null));
        }

        // Kiểm tra mật khẩu
        UserDTO user = userOptional.get();
        if (!userDTO.getPassword().equals(user.getPassword())) {
            return ResponseEntity.status(400)
                    .body(ApiResponse.error("Mật khẩu không chính xác", null));
        }

        return ResponseEntity.ok(ApiResponse.success("Đăng nhập thành công", user.getRole()));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<String>> forgotPassword(@RequestBody UserDTO userDTO) {
        Optional<UserDTO> userOptional = userService.getUserByEmail(userDTO.getEmail());
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.error("Email không tồn tại", null));
        }

        UserDTO user = userOptional.get();
        return ResponseEntity.ok(ApiResponse.success("Mật khẩu của bạn là: " + user.getPassword(), null));
    }
}
