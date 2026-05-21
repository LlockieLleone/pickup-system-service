package com.tuoguan.pickup.notification;

public interface SmsSender {

    void send(String phone, String content);
}