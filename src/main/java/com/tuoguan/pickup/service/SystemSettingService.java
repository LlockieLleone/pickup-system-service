package com.tuoguan.pickup.service;

import com.tuoguan.pickup.entity.SystemSetting;
import com.tuoguan.pickup.repository.SystemSettingRepository;
import org.springframework.stereotype.Service;

@Service
public class SystemSettingService {

    private final SystemSettingRepository systemSettingRepository;

    public SystemSettingService(SystemSettingRepository systemSettingRepository) {
        this.systemSettingRepository = systemSettingRepository;
    }

    public String getString(String key, String defaultValue) {
        return systemSettingRepository.findBySettingKey(key)
                .map(SystemSetting::getSettingValue)
                .orElse(defaultValue);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return systemSettingRepository.findBySettingKey(key)
                .map(setting -> Boolean.parseBoolean(setting.getSettingValue()))
                .orElse(defaultValue);
    }
}