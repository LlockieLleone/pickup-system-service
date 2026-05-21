package com.tuoguan.pickup.service;

import com.tuoguan.pickup.entity.EventLog;
import com.tuoguan.pickup.entity.Guardian;
import com.tuoguan.pickup.entity.NotificationLog;
import com.tuoguan.pickup.entity.Student;
import com.tuoguan.pickup.enums.EventType;
import com.tuoguan.pickup.enums.NotificationChannel;
import com.tuoguan.pickup.enums.NotificationStatus;
import com.tuoguan.pickup.notification.AliSmsSender;
import com.tuoguan.pickup.notification.MockSmsSender;
import com.tuoguan.pickup.notification.SmsSender;
import com.tuoguan.pickup.repository.NotificationLogRepository;
import com.tuoguan.pickup.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService {

    private final NotificationLogRepository notificationLogRepository;
    private final StudentRepository studentRepository;
    private final MockSmsSender mockSmsSender;
    private final AliSmsSender aliSmsSender;
    private final SystemSettingService systemSettingService;

    public NotificationService(
            NotificationLogRepository notificationLogRepository,
            StudentRepository studentRepository,
            MockSmsSender mockSmsSender,
            AliSmsSender aliSmsSender,
            SystemSettingService systemSettingService
    ) {
        this.notificationLogRepository = notificationLogRepository;
        this.studentRepository = studentRepository;
        this.mockSmsSender = mockSmsSender;
        this.aliSmsSender = aliSmsSender;
        this.systemSettingService = systemSettingService;
    }

    private SmsSender getSmsSender() {

        String provider =
                systemSettingService.getString(
                        "sms.provider",
                        "mock"
                );

        return switch (provider.toLowerCase()) {

            case "aliyun" -> aliSmsSender;

            case "mock" -> mockSmsSender;

            default -> throw new RuntimeException(
                    "Unsupported sms provider: " + provider
            );
        };
    }

    public void notifyGuardians(EventLog eventLog) {

        if (!shouldNotify(eventLog.getEventType())) {
            return;
        }

        Student student = studentRepository.findById(eventLog.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found for notification"));

        for (Guardian guardian : student.getGuardians()) {

            if (guardian.getPhone() == null || guardian.getPhone().isBlank()) {
                continue;
            }

            NotificationLog log = new NotificationLog();
            log.setStudentId(student.getStudentId());
            log.setEventId(eventLog.getEventId());
            log.setChannel(NotificationChannel.SMS);
            log.setRecipientPhone(guardian.getPhone());
            log.setContent(buildMessage(student.getName(), eventLog.getEventType()));

            try {
                getSmsSender().send(
                        guardian.getPhone(),
                        log.getContent()
                );

                log.setStatus(NotificationStatus.SUCCESS);
                log.setSentAt(LocalDateTime.now());

            } catch (Exception e) {
                log.setStatus(NotificationStatus.FAILED);
                log.setErrorMessage(e.getMessage());
            }

            notificationLogRepository.save(log);
        }
    }

    private boolean shouldNotify(EventType eventType) {
        return switch (eventType) {
            case PICKED_UP -> systemSettingService.getBoolean("notification.event.picked_up", true);
            case ARRIVED -> systemSettingService.getBoolean("notification.event.arrived", true);
            case RELEASED -> systemSettingService.getBoolean("notification.event.released", true);
            case EXCEPTION, MANUAL_CORRECTION -> false;
        };
    }

    private String buildMessage(String studentName, EventType eventType) {
        return switch (eventType) {
            case PICKED_UP -> "【托管通知】" + studentName + "已被老师接到，请放心。";
            case ARRIVED -> "【托管通知】" + studentName + "已安全到达托管中心。";
            case RELEASED -> "【托管通知】" + studentName + "已被家长接走。";
            case EXCEPTION -> "【托管通知】" + studentName + "出现异常，请及时联系老师。";
            case MANUAL_CORRECTION -> "【托管通知】" + studentName + "接送状态已修正。";
        };
    }
}