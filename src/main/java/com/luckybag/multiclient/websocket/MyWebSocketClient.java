package com.luckybag.multiclient.websocket;

import com.alibaba.fastjson.JSONObject;
import com.luckybag.gui.ControlPanel;
import org.apache.log4j.Logger;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyWebSocketClient extends WebSocketClient {
    String token = "";
    String user = "";
    String phone = "";
    ControlPanel currentClient = null;

    public ControlPanel getCurrentClient() {
        return currentClient;
    }

    public void setCurrentClient(ControlPanel currentClient) {
        this.currentClient = currentClient;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    Logger logger = Logger.getLogger(MyWebSocketClient.class);


    public MyWebSocketClient(URI serverUri) {
        super(serverUri);
    }


    @Override
    public void onOpen(ServerHandshake arg0) {
        logger.info("------ MyWebSocket onOpen ------");
    }


    @Override
    public void onClose(int arg0, String arg1, boolean arg2) {

        logger.info("------ MyWebSocket onClose ------");
    }


    @Override
    public void onError(Exception arg0) {
        logger.info("------ MyWebSocket onError ------" + arg0);
    }


    @Override
    public void onMessage(String msg) {
//        logger.info("-------- onMessage: " + msg + "--------");
        if ("CHB".equalsIgnoreCase(msg)) {
//            logger.info("-------- onMessage: " + msg + "--------");
            return;
        }

        JSONObject jsonObject = JSONObject.parseObject(msg);

        if (jsonObject.getString("Token") != null) {
            this.setToken(jsonObject.getString("Token"));
            this.send("subscribe:99999@18@1813@{USER}");
        } else if (jsonObject.getString("CONTENT") != null) {
            JSONObject content = JSONObject.parseObject(jsonObject.getString("CONTENT"));
            String coustomname = content.getString("COUSTOMNAME");

            if (coustomname != null) {
                String rowState = content.getString("RowState");
                if (rowState.equalsIgnoreCase("1")) {
                    logger.info(coustomname + "login");
                }
                if (rowState.equalsIgnoreCase("3")) {
                    logger.info(coustomname + "logout");
                }
            } else {
                String userName = content.getString("USERNAME");

                if (content.getString("LUCKYBAGID") != null) {//接收的是红包消息
                    String bagAmount = content.getString("LUCKYBAGTOTALAMOUNT");
                    String luckybagnumber = content.getString("LUCKYBAGNUMBER");
                    currentClient.getTextArea().setText(currentClient.getTextArea().getText() + userName + ": send luckybag with " + luckybagnumber + "bags, total amount is : " + bagAmount + "\n");
                    logger.info("I'm " + this.user + ", " + userName + ": luckybag with " + luckybagnumber + " bags, total amount is : " + bagAmount + ", log time: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()));

                } else { //接收的是消息

                    String msgcontent = content.getString("MSGCONTENT");
                    String msgcreatetime = content.getString("MSGCREATETIME");

                    currentClient.getTextArea().setText(currentClient.getTextArea().getText() + userName + ": " + msgcontent + "\n");
                    currentClient.getTextField().setText("");
                    logger.info("I'm " + this.user + ", " + userName + " send msg: " + msgcontent + ", msgtime: " + msgcreatetime + ", log time: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()));
                }

            }
        }
    }
}
