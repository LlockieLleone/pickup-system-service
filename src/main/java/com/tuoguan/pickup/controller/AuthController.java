package com.tuoguan.pickup.controller;

import com.tuoguan.pickup.dto.ApiResponse;
import com.tuoguan.pickup.dto.LoginRequest;
import com.tuoguan.pickup.dto.LoginResponse;
import com.tuoguan.pickup.entity.Teacher;
import com.tuoguan.pickup.repository.TeacherRepository;
import com.tuoguan.pickup.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(TeacherRepository teacherRepository,
                          PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil) {
        this.teacherRepository = teacherRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest request) {

        Teacher teacher = teacherRepository.findByPhone(request.getPhone())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        if (!passwordEncoder.matches(request.getPassword(), teacher.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        if (!teacher.getActive()) {
            throw new RuntimeException("Teacher account is disabled");
        }

        String token = jwtUtil.generateToken(
                teacher.getTeacherId(),
                teacher.getName(),
                teacher.getRole()
        );

        LoginResponse response = new LoginResponse(
                true,
                teacher.getTeacherId(),
                teacher.getName(),
                teacher.getRole(),
                token,
                "Login successful"
        );

        return ApiResponse.ok("Login successful", response);
    }
}