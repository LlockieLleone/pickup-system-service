package com.tuoguan.pickup.controller;

import com.tuoguan.pickup.dto.ApiResponse;
import com.tuoguan.pickup.entity.SystemSetting;
import com.tuoguan.pickup.repository.SystemSettingRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/settings")
public class SystemSettingController {

    private final SystemSettingRepository systemSettingRepository;

    public SystemSettingController(SystemSettingRepository systemSettingRepository) {
        this.systemSettingRepository = systemSettingRepository;
    }

    @PostMapping
    public ApiResponse<SystemSetting> createSetting(@RequestBody SystemSetting setting) {
        return ApiResponse.ok("Setting saved", systemSettingRepository.save(setting));
    }

    @GetMapping
    public ApiResponse<List<SystemSetting>> getAllSettings() {
        return ApiResponse.ok(systemSettingRepository.findAll());
    }

    @GetMapping("/{key}")
    public ApiResponse<SystemSetting> getSettingByKey(@PathVariable String key) {
        SystemSetting setting = systemSettingRepository.findBySettingKey(key)
                .orElseThrow(() -> new RuntimeException("Setting not found"));

        return ApiResponse.ok(setting);
    }

    @PutMapping("/{key}")
    public ApiResponse<SystemSetting> updateSetting(
            @PathVariable String key,
            @RequestBody SystemSetting request
    ) {
        SystemSetting setting = systemSettingRepository.findBySettingKey(key)
                .orElseThrow(() -> new RuntimeException("Setting not found"));

        setting.setSettingValue(request.getSettingValue());

        if (request.getDescription() != null) {
            setting.setDescription(request.getDescription());
        }

        return ApiResponse.ok(
                "Setting updated",
                systemSettingRepository.save(setting)
        );
    }
}