package com.tuoguan.pickup.controller;

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
    public Guardian createGuardian(@RequestBody Guardian guardian) {
        return guardianRepository.save(guardian);
    }

    @GetMapping
    public List<Guardian> getAllGuardians() {
        return guardianRepository.findAll();
    }

    @GetMapping("/{id}")
    public Guardian getGuardianById(@PathVariable Long id) {
        return guardianRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Guardian not found"));
    }
}