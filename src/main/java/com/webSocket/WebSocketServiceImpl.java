package com.webSocket;


import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/ws")
@Service
public class WebSocketServiceImpl {

    //当前在线的连接数
    private static int onlineCount=0;

    //所有的链接对象
    private static CopyOnWriteArraySet<WebSocketServiceImpl> webSocketServices = new CopyOnWriteArraySet<WebSocketServiceImpl>();

    //与客户端链接的会话
    private Session session;

    public Session getSession(){
        return session;
    }

    private static synchronized int getOnlineCount(){
        return onlineCount;
    }

    private static synchronized void addOnlineCount(){
        onlineCount++;
    }

    private static synchronized void subOnlineCount(){
        onlineCount--;
    }

    private void sendMessage(String message){
        try {
            this.session.getAsyncRemote().sendText(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnOpen
    public void onOpen(Session session){
        this.session=session;
        webSocketServices.add(this);
        addOnlineCount();
        System.out.println("有新链接加进来，现在连接数为"+getOnlineCount());
        sendMessage("新链接加入");
    }

    @OnClose
    public void onClose(){
        webSocketServices.remove(this);
        subOnlineCount();
        System.out.println("有链接关闭，现在连接数为"+getOnlineCount());
    }

    @OnMessage
    public void onMessage(String message,Session session) throws IOException {
        System.out.println("来自客户端的消息："+message);
        for (WebSocketServiceImpl webSocketService : webSocketServices) {
            webSocketService.sendMessage(message);
        }
    }

    @OnError
    public void onError(Session session,Throwable error){
        System.out.println("出错了");
        error.printStackTrace();
    }

}

