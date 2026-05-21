package com.tuoguan.pickup.controller;

import com.tuoguan.pickup.dto.ApiResponse;
import com.tuoguan.pickup.entity.Card;
import com.tuoguan.pickup.entity.Guardian;
import com.tuoguan.pickup.entity.Student;
import com.tuoguan.pickup.repository.CardRepository;
import com.tuoguan.pickup.repository.GuardianRepository;
import com.tuoguan.pickup.repository.StudentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentRepository studentRepository;
    private final GuardianRepository guardianRepository;
    private final CardRepository cardRepository;

    public StudentController(StudentRepository studentRepository,
                             GuardianRepository guardianRepository,
                             CardRepository cardRepository) {
        this.studentRepository = studentRepository;
        this.guardianRepository = guardianRepository;
        this.cardRepository = cardRepository;
    }

    @PostMapping
    public ApiResponse<Student> createStudent(@RequestBody Student student) {
        return ApiResponse.ok("Student created", studentRepository.save(student));
    }

    @GetMapping
    public ApiResponse<List<Student>> getAllStudents() {
        return ApiResponse.ok(studentRepository.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<Student> getStudentById(@PathVariable Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return ApiResponse.ok(student);
    }

    @PostMapping("/{studentId}/guardians/{guardianId}")
    public ApiResponse<Student> bindGuardianToStudent(@PathVariable Long studentId,
                                                      @PathVariable Long guardianId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Guardian guardian = guardianRepository.findById(guardianId)
                .orElseThrow(() -> new RuntimeException("Guardian not found"));

        student.getGuardians().add(guardian);

        return ApiResponse.ok("Guardian bound to student", studentRepository.save(student));
    }

    @PostMapping("/{studentId}/cards/{cardId}")
    public ApiResponse<Card> bindCardToStudent(@PathVariable Long studentId,
                                               @PathVariable Long cardId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        card.setAssignedStudentId(student.getStudentId());

        return ApiResponse.ok("Card bound to student", cardRepository.save(card));
    }
}