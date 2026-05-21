package com.tuoguan.pickup.notification;

import org.springframework.stereotype.Component;

@Component
public class AliSmsSender implements SmsSender {

    @Override
    public void send(String phone, String content) {

        /*
         * TODO:
         * 接入阿里云短信 SDK
         *
         * 未来实现：
         *
         * 1. 从数据库读取：
         *    sms.aliyun.accessKeyId
         *    sms.aliyun.accessKeySecret
         *    sms.aliyun.signName
         *    sms.aliyun.templateCode
         *
         * 2. 调用阿里云短信 API
         *
         * 3. 根据返回结果：
         *    成功 -> 正常返回
         *    失败 -> throw RuntimeException
         */

        throw new UnsupportedOperationException(
                "AliSmsSender is not implemented yet"
        );
    }
}