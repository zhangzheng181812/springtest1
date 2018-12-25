package com.book.two;


import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

/**
 * Created by admin on 2018/12/25.
 */
@Component
public class RedisMessageListener implements MessageListener {
    @Override
    public void onMessage(Message message, byte[] bytes) {
        String body = new String(message.getBody());
        String topic = new String (bytes);
        System.out.println("消息体"+body);
        System.out.println("渠道名"+topic);
    }
}
