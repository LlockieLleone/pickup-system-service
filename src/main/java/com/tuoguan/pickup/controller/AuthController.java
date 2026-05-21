package com.tuoguan.pickup.controller;

import com.tuoguan.pickup.dto.ApiResponse;
import com.tuoguan.pickup.dto.LoginRequest;
import com.tuoguan.pickup.dto.LoginResponse;
import com.tuoguan.pickup.entity.Teacher;
import com.tuoguan.pickup.repository.TeacherRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final TeacherRepository teacherRepository;

    public AuthController(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest request) {

        Teacher teacher = teacherRepository.findByPhone(request.getPhone())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        if (!teacher.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        if (!teacher.getActive()) {
            throw new RuntimeException("Teacher account is disabled");
        }

        LoginResponse response = new LoginResponse(
                true,
                teacher.getTeacherId(),
                teacher.getName(),
                teacher.getRole(),
                "Login successful"
        );

        return ApiResponse.ok("Login successful", response);
    }
}