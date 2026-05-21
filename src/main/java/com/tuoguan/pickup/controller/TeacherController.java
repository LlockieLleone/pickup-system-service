package com.tuoguan.pickup.controller;

import com.tuoguan.pickup.dto.ApiResponse;
import com.tuoguan.pickup.entity.Teacher;
import com.tuoguan.pickup.repository.TeacherRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherRepository teacherRepository;

    public TeacherController(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @PostMapping
    public ApiResponse<Teacher> createTeacher(@RequestBody Teacher teacher) {
        return ApiResponse.ok("Teacher created", teacherRepository.save(teacher));
    }

    @GetMapping
    public ApiResponse<List<Teacher>> getAllTeachers() {
        return ApiResponse.ok(teacherRepository.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<Teacher> getTeacherById(@PathVariable Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        return ApiResponse.ok(teacher);
    }
}