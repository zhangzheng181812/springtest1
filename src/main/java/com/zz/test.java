package com.zz;

import com.util.JsonUtils;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAConfig;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.core.notification.RSANotificationConfig;
import com.wechat.pay.java.service.payments.nativepay.model.Amount;
import com.wechat.pay.java.service.payments.nativepay.model.PrepayRequest;
import com.wechat.pay.java.service.payments.nativepay.model.PrepayResponse;
import com.wechat.pay.java.service.payments.nativepay.NativePayService;

import java.util.HashMap;
import java.util.Map;

public class test {

//    public static void main(String[] args) {
//        Config config =
//                new RSAConfig.Builder()
//                        .merchantId("")
//                        .privateKeyFromPath("privateKeyPath")
//                        .merchantSerialNumber("merchantSerialNumber")
//                        .wechatPayCertificatesFromPath("wechatPayCertificatePath")
//                        .build();
//
//        NativePayService service = new NativePayService.Builder().config(config).build();
//
//        PrepayRequest request = new PrepayRequest();
//        Amount amount = new Amount();
//        amount.setTotal(100);
//        request.setAmount(amount);
//        request.setDescription("测试商品标题");
//        request.setNotifyUrl("https://notify_url");
//        request.setOutTradeNo("out_trade_no_001");
//
//        PrepayResponse response = service.prepay(request);
//        System.out.println(response.getCodeUrl());
//    }

    public static void main(String[] args) {
        Config config =
                new RSAConfig.Builder()
                        .merchantId("")
                        .privateKeyFromPath("privateKeyPath")
                        .merchantSerialNumber("merchantSerialNumber")
                        .wechatPayCertificatesFromPath("wechatPayCertificatePath")
                        .build();
        NotificationParser notificationParser = new NotificationParser(new RSANotificationConfig.Builder().apiV3Key("").certificates("").build());

    }

}