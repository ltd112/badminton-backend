package com.iuh.fit.badminton_backend.controller;

import com.iuh.fit.badminton_backend.dto.ApiResponse;
import com.iuh.fit.badminton_backend.dto.UserDTO;
import com.iuh.fit.badminton_backend.service.EmailService;
import com.iuh.fit.badminton_backend.service.UserService;
import com.iuh.fit.badminton_backend.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final EmailService emailService;
    private final Utils utils;

    @Autowired
    public AuthController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
        this.utils = new Utils();
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@RequestBody UserDTO userDTO) {
        if (userService.getUserByUsername(userDTO.getUsername()).isPresent()) {
            return ResponseEntity.status(201)
                    .body(ApiResponse.error("Tên đăng nhập đã tồn tại", null));
        }

        if (userService.getUserByEmail(userDTO.getEmail()).isPresent()) {
            return ResponseEntity.status(201)
                    .body(ApiResponse.error("Email đã tồn tại", null));
        }

        String otp = utils.generateOtp();
        emailService.sendSimpleMail(userDTO.getEmail(), "Xác thực OTP", "Mã OTP của bạn là: " + otp);

        userDTO.setOtp(otp);
        userDTO.setOtpGenerationTime(LocalDateTime.now()); // Set the OTP generation time
        UserDTO savedUser = userService.addUsers(userDTO);

        return ResponseEntity.status(200)
                .body(ApiResponse.success("OTP đã được gửi đến email của bạn", null));
    }

    // src/main/java/com/iuh/fit/badminton_backend/controller/AuthController.java
    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse<UserDTO>> verifyOtp(@RequestBody UserDTO userDTO) {
        Optional<UserDTO> userOptional = userService.getUserByEmail(userDTO.getEmail());
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(202).body(ApiResponse.error("Người dùng không tồn tại", null));
        }
        UserDTO user = userOptional.get();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime otpGenerationTime = user.getOtpGenerationTime();
        long minutesElapsed = Duration.between(otpGenerationTime, now).toMinutes();

        if (minutesElapsed > 1) {
            return ResponseEntity.status(201).body(ApiResponse.error("OTP đã hết hạn", null));
        }

        if (userDTO.getOtp().equals(user.getOtp())) {
            user.setVerified(true);
            UserDTO updatedUser = userService.updateUser(user.getId(), user);
            return ResponseEntity.ok(ApiResponse.success("OTP xác thực thành công và người dùng đã được tạo", updatedUser));
        } else {
            return ResponseEntity.status(201).body(ApiResponse.error("OTP không hợp lệ", null));
        }
    }
    @PostMapping("/resend-otp")
    public ResponseEntity<ApiResponse<String>> resendOtp(@RequestBody UserDTO userDTO) {
        Optional<UserDTO> userOptional = userService.getUserByEmail(userDTO.getEmail());
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(201).body(ApiResponse.error("Người dùng không tồn tại", null));
        }

        UserDTO user = userOptional.get();
        String newOtp = utils.generateOtp();
        user.setOtp(newOtp);
        user.setOtpGenerationTime(LocalDateTime.now());
        userService.updateUser(user.getId(), user);

        emailService.sendSimpleMail(user.getEmail(), "Xác thực OTP", "Mã OTP mới của bạn là: " + newOtp);

        return ResponseEntity.ok(ApiResponse.success("OTP mới đã được gửi đến email của bạn", null));
    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserDTO>> login(@RequestBody UserDTO userDTO) {
        // Kiểm tra username
        Optional<UserDTO> userOptional = userService.getUserByUsername(userDTO.getUsername());
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(201)
                    .body(ApiResponse.error("Tên đăng nhập không tồn tại", null));
        }

        // Kiểm tra mật khẩu
        UserDTO user = userOptional.get();
        if (!userDTO.getPassword().equals(user.getPassword())) {
            return ResponseEntity.status(202)
                    .body(ApiResponse.error("Mật khẩu không chính xác", null));
        }

        return ResponseEntity.ok(ApiResponse.success("Đăng nhập thành công", user));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<String>> forgotPassword(@RequestBody UserDTO userDTO) {
        Optional<UserDTO> userOptional = userService.getUserByEmail(userDTO.getEmail());
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(201)
                    .body(ApiResponse.error("Email không tồn tại", null));
        }

        UserDTO user = userOptional.get();
        return ResponseEntity.ok(ApiResponse.success("Mật khẩu của bạn là: " + user.getPassword(), null));
    }

    /**
     * Get a user by ID.
     *
     * @param id the ID of the user.
     * @return ApiResponse containing user data.
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserById(@PathVariable Long id) {
        Optional<UserDTO> userDTO = userService.getUserById(id);
        return userDTO.map(dto -> ResponseEntity.ok(ApiResponse.success("Tìm thấy người dùng", dto))).orElseGet(() -> ResponseEntity.status(404)
                .body(ApiResponse.error("Người dùng không tồn tại", null)));
    }

    /**
     * Get a user by username.
     *
     * @return ApiResponse containing user data.
     */
    @GetMapping("/users/username/{username}")
    public ResponseEntity<ApiResponse<UserDTO>> getUserByUsername(@PathVariable String username) {
        Optional<UserDTO> userDTO = userService.getUserByUsername(username);
        return userDTO.map(dto -> ResponseEntity.ok(ApiResponse.success("Tìm thấy người dùng", dto))).orElseGet(() -> ResponseEntity.status(404)
                .body(ApiResponse.error("Người dùng không tồn tại", null)));
    }

    /**
     * Get a user by full name.
     *
     * @return ApiResponse containing user data.
     */
    @GetMapping("/users/search")
    public ResponseEntity<ApiResponse<List<UserDTO>>> getUsersByName(@RequestParam String name) {
        List<UserDTO> users = userService.getUsersByName(name);
        if (users.isEmpty()) {
            return ResponseEntity.status(201)
                    .body(ApiResponse.error("Không tìm thấy người dùng với tên: " + name, null));
        }
        return ResponseEntity.ok(ApiResponse.success("Tìm thấy người dùng", users));
    }

    /**
     * Get all users.
     *
     * @return ApiResponse containing the list of all users.
     */
    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllUsers() {
        List<UserDTO> userList = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.success("Danh sách người dùng", userList));
    }

    /**
     * Update a user by ID.
     *
     * @param id      the ID of the user.
     * @param userDTO the updated user data.
     * @return ApiResponse containing the updated user data.
     */
    @PutMapping("/users/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUser(id, userDTO);
        if (updatedUser != null) {
            return ResponseEntity.ok(ApiResponse.success("Cập nhật người dùng thành công", updatedUser));
        } else {
            return ResponseEntity.status(201)
                    .body(ApiResponse.error("Người dùng không tồn tại", null));
        }
    }

    /**
     * Delete a user by ID.
     *
     * @param id the ID of the user.
     * @return ApiResponse with status of delete operation.
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable Long id) {
        Optional<UserDTO> userDTO = userService.getUserById(id);
        if (userDTO.isPresent()) {
            userService.deleteUser(id);
            return ResponseEntity.ok(ApiResponse.success("Xóa người dùng thành công", null));
        } else {
            return ResponseEntity.status(201)
                    .body(ApiResponse.error("Người dùng không tồn tại", null));
        }
    }

    /**
     * Change password of a user by username.
     *
     * @param username the username of the user.
     * @return ApiResponse with status of delete operation.
     */
    @PutMapping("/users/change-password/{username}")
    public ResponseEntity<ApiResponse<String>> changePassword(@PathVariable String username, @RequestBody UserDTO userDTO) {
        Optional<UserDTO> userOptional = userService.getUserByUsername(username);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(201)
                    .body(ApiResponse.error("Tên đăng nhập không tồn tại", null));
        }

        UserDTO user = userOptional.get();
        user.setPassword(userDTO.getPassword());
        userService.updateUser(user.getId(), user);
        return ResponseEntity.ok(ApiResponse.success("Đổi mật khẩu thành công", null));
    }

    @GetMapping("/count")
    public ApiResponse<Long> countUsers() {
        long count = userService.countUsers();
        return ApiResponse.success("Số lượng người dùng", count);
    }
}
