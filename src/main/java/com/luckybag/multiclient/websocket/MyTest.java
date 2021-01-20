package com.luckybag.multiclient.websocket;


import com.luckybag.gui.CONTANTS;
import com.luckybag.gui.ControlPanel;
import org.java_websocket.client.WebSocketClient;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;


/**
 * http://218.78.51.29:18013/luckybag/main/home
 * 测试环境：
 * 机器：172.16.100.18    izkml		1u%VOMW$er
 * 数据库：您的人力资源产品线测试环境的数据库已经创建成功。DBName：zkml_hrssc_test，User：zkmlhrsstestuser，Password: BFIq3fu!jrPp，IP：10.5.4.243，Port：33891。请您及时做连接测试。
 * <p>
 * <p>
 * <p>
 * 生产环境：
 * 电信云机器
 * 61.171.37.10 8g
 * 218.78.51.29 16g
 * 用户名密码都是：administrator / Izkml@123
 * <p>
 * 数据库：您的数据库已经创建成功。DBName：zkml_lucky_bag，User：zkluckybaguser，Password: bh3H!RAw3E43ID，公网IP：121.37.189.223，Port：33917 ，内网IP：192.168.8.150，Port：33891。请您及时做连接测试。
 * <p>
 * <p>
 * [{"Receivers":["99999"],"MessageBody":{"MSGID":"89856FA0-478A-41B1-9E29-82593E5B9D42","MSGCONTENT":"qqqq","USERID":"3","USERNAME":"纪文静","MSGCREATETIME":"2020-12-25 05:44:25","RECEIVER":"99999","RECEIVERTYPEID":"1","RowState":"1"}}]
 * <p>
 * {<15000000001@123@8008>}
 * subscribe:99999@lm.com.100
 */
public class MyTest {
    private static final WebSocketClient client = null;
    private static Properties properties;


    //    https://www.cnblogs.com/jieerma666/p/10342435.html
    public static void main(String[] args) throws URISyntaxException, InterruptedException {

//        init();
        // 此处的WebSocket服务端URI，上面服务端第2点有详细说明
        MyWebSocketClient myClient = new MyWebSocketClient(new URI(CONTANTS.WS_SERVER_DEV));
        myClient.connectBlocking();
        myClient.setConnectionLostTimeout(30000);


        String phone = args[0];
        String name = args[1];
//        String phone = "15021597239";
//        String name = "牛坤";


        if (myClient.isOpen()) {

            myClient.send("{<" + phone + "@123@8008>}");
//            System.out.println("send successful");
        }


//        ControlPanel controlPanel = new ControlPanel(args[0], args[1], myClient);
        ControlPanel controlPanel = new ControlPanel(phone, name, myClient);
        controlPanel.showEventDemo();

    }

    private static void init() {
        properties = new Properties();
        // 使用ClassLoader加载properties配置文件生成对应的输入流
        InputStream in = MyTest.class.getClassLoader().getResourceAsStream("config.properties");
        // 使用properties对象加载输入流
        try {
            properties.load(in);
        } catch (IOException e) {
            System.out.println("read config.properties failed");
        }
        //获取key对应的value值
        System.out.println(properties.getProperty("username"));
    }


}
