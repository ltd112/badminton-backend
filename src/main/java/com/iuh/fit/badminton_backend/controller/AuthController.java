package com.iuh.fit.badminton_backend.controller;

import com.iuh.fit.badminton_backend.dto.ApiResponse;
import com.iuh.fit.badminton_backend.dto.UserDTO;
import com.iuh.fit.badminton_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * Đăng ký người dùng mới.
     * @param userDTO thông tin người dùng để đăng ký.
     * @return ApiResponse chứa thông báo kết quả đăng ký.
     */
    @PostMapping("/register")
    public ApiResponse<UserDTO> register(@RequestBody UserDTO userDTO) {
        // Kiểm tra nếu username hoặc email đã tồn tại
        if (userService.getUserByUsername(userDTO.getUsername()).isPresent()) {
            return ApiResponse.error("Tên đăng nhập đã tồn tại", null);
        }

        if (userService.getUserByEmail(userDTO.getEmail()).isPresent()) {
            return ApiResponse.error("Email đã tồn tại", null);
        }

        // Lưu người dùng (không mã hóa mật khẩu)
        UserDTO savedUser = userService.saveOrUpdateUser(userDTO);

        return ApiResponse.success("Đăng ký thành công", savedUser);
    }

    /**
     * Đăng nhập người dùng.
     * @param userDTO thông tin người dùng để đăng nhập (username/email và mật khẩu).
     * @return ApiResponse chứa thông báo kết quả đăng nhập.
     */
    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody UserDTO userDTO) {
        // Kiểm tra xem người dùng có tồn tại không
        Optional<UserDTO> userOptional = userService.getUserByUsername(userDTO.getUsername());
        if (userOptional.isEmpty()) {
            return ApiResponse.error("Tên đăng nhập không tồn tại", null);
        }

        // Kiểm tra mật khẩu (so sánh trực tiếp, không mã hóa)
        UserDTO user = userOptional.get();
        if (!userDTO.getPassword().equals(user.getPassword())) {
            return ApiResponse.error("Mật khẩu không chính xác", null);
        }

        return ApiResponse.success("Đăng nhập thành công", null);
    }
}
