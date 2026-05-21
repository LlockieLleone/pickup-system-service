package com.tuoguan.pickup.controller;

import com.tuoguan.pickup.dto.ApiResponse;
import com.tuoguan.pickup.entity.Guardian;
import com.tuoguan.pickup.repository.GuardianRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guardians")
public class GuardianController {

    private final GuardianRepository guardianRepository;

    public GuardianController(GuardianRepository guardianRepository) {
        this.guardianRepository = guardianRepository;
    }

    @PostMapping
    public ApiResponse<Guardian> createGuardian(@RequestBody Guardian guardian) {
        return ApiResponse.ok("Guardian created", guardianRepository.save(guardian));
    }

    @GetMapping
    public ApiResponse<List<Guardian>> getAllGuardians() {
        return ApiResponse.ok(guardianRepository.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<Guardian> getGuardianById(@PathVariable Long id) {
        Guardian guardian = guardianRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Guardian not found"));

        return ApiResponse.ok(guardian);
    }
}