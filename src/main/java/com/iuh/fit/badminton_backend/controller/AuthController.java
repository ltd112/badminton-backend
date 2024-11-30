package com.iuh.fit.badminton_backend.controller;

import com.iuh.fit.badminton_backend.dto.ApiResponse;
import com.iuh.fit.badminton_backend.dto.UserDTO;
import com.iuh.fit.badminton_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        return ResponseEntity.status(200)
                .body(ApiResponse.success("Đăng ký thành công", savedUser));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserDTO>> login(@RequestBody UserDTO userDTO) {
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

        return ResponseEntity.ok(ApiResponse.success("Đăng nhập thành công", user));
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

    /**
     * Get a user by ID.
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
     * Get all users.
     * @return ApiResponse containing the list of all users.
     */
    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<UserDTO>>> getAllUsers() {
        List<UserDTO> userList = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.success("Danh sách người dùng", userList));
    }

    /**
     * Update a user by ID.
     * @param id the ID of the user.
     * @param userDTO the updated user data.
     * @return ApiResponse containing the updated user data.
     */
    @PutMapping("/users/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUser(id, userDTO);
        if (updatedUser != null) {
            return ResponseEntity.ok(ApiResponse.success("Cập nhật người dùng thành công", updatedUser));
        } else {
            return ResponseEntity.status(404)
                    .body(ApiResponse.error("Người dùng không tồn tại", null));
        }
    }

    /**
     * Delete a user by ID.
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
            return ResponseEntity.status(404)
                    .body(ApiResponse.error("Người dùng không tồn tại", null));
        }
    }
}
