package com.tuoguan.pickup.notification;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class MockSmsSender implements SmsSender {

    @Override
    public void send(String phone, String content) {
        System.out.println("Mock SMS sent to " + phone + ": " + content);
    }
}