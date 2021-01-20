package com.luckybag.gui;

import com.lgc.LoginResponseDto;
import com.lgc.MessageDto;
import com.lgc.ServiceFactorys;
import com.lgc.StartUpClientModel;
import com.luckybag.entity.ILuckyBagService8008Service;
import com.luckybag.entity.MsgInfoDto;
import com.luckybag.fileserver.FileServerUtils;
import com.luckybag.multiclient.websocket.MyWebSocketClient;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public class ControlPanel {
    private JFrame mainFrame;
    private JLabel headerLabel;
    private JTextArea textArea;
    JScrollPane msgPane;
    private JTextArea rosterArea;
    JScrollPane rosterPane;

    private JPanel controlPanel;
    private JTextField textField;
    private MyWebSocketClient myClient = null;
    static int left = 0;
    static int top = 0;
    StartUpClientModel customObject;
    ILuckyBagService8008Service iLuckyBagService8008Service;
    StringBuilder sb = new StringBuilder();
    StringBuilder roster = new StringBuilder();
    private static Logger logger = Logger.getLogger(FileServerUtils.class);

    public JTextArea getTextArea() {
        return textArea;
    }

    public JTextField getTextField() {
        return textField;
    }

    public ControlPanel(String phone, String name, MyWebSocketClient client) {
        myClient = client;
        myClient.setCurrentClient(this);
        customObject = new StartUpClientModel();
        List<String> list = new ArrayList<String>();
        myClient.setUser(name);
        myClient.setPhone(phone);
        list.add("http://218.78.51.29:18013/dic/AppAddrService");
//        list.add("http://117.71.53.199:40009/AppAddrService");
        customObject.InitModelManager(list);
        if (myClient.getToken().isEmpty()) {
            System.out.println("Login failed");
            try {
                throw new Exception("Login failed, please check out input phone.");
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        } else {
            System.out.println("Login success, token is: " + myClient.getToken());
        }

        prepareGUI(phone + " -- " + name);
    }

    private void prepareGUI(String name) {
        mainFrame = new JFrame("Lucky Bag Chat");

        mainFrame.setSize(650, 500);

        mainFrame.setLayout(new BorderLayout());
        headerLabel = new JLabel(name, JLabel.CENTER);
        textArea = new JTextArea();
        textArea.setLineWrap(true);

        textArea.setSize(350, 100);
        msgPane = new JScrollPane(textArea);
        msgPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        rosterArea = new JTextArea();
        rosterArea.setLineWrap(true);
        rosterArea.setSize(220, 100);
        rosterPane = new JScrollPane(rosterArea);
//        rosterPane.setVerticalScrollBarPolicy(
//                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        mainFrame.add(headerLabel, BorderLayout.NORTH);
        mainFrame.add(msgPane, BorderLayout.CENTER);
        mainFrame.add(rosterPane, BorderLayout.WEST);
        mainFrame.add(controlPanel, BorderLayout.SOUTH);
        mainFrame.setLocation(100, 100);
        mainFrame.setVisible(true);
    }

    public void showEventDemo() {
        JButton listButton = new JButton("Users");
        JButton sendButton = new JButton("Send");
        JButton clearButton = new JButton("Clear All");
        textField = new JTextField(15);


        listButton.addActionListener(new UserListener());
        sendButton.addActionListener(new SendListener());
        clearButton.addActionListener(new ClearListener());
        textField.addActionListener(new TextFieldListener());

        controlPanel.add(listButton);
//        controlPanel.add(sendButton);
        controlPanel.add(clearButton);
        controlPanel.add(textField);
        mainFrame.setLocation(left, top);
        left += 380;
        if (left >= 380 * 5) {
            left = 0;
            top = 400;
        }
        top += 0;
//        System.out.println(left + top);
        mainFrame.setVisible(true);
    }


    private class UserListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            getUsers();

            rosterArea.setText(roster.toString());


            roster.delete(0, roster.length());


        }
    }

    private class SendListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            if (textField.getText().trim().isEmpty()) {
                textField.setText("");
                return;
            }
            sb.append(textField.getText() + "\n");
            textArea.setText(sb.toString());
//            sendMSG(textField.getText());
            for (int i = 0; i < 50; i++) {
                sendMSG(textField.getText() + " " + i + ": " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()));
            }
            textField.setText("");


        }
    }


    private class ClearListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            sb.delete(0, sb.length());
            textArea.setText(sb.toString());
        }
    }

    private class TextFieldListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (textField.getText().trim().isEmpty()) {
                textField.setText("");
                return;
            }
            String content = e.getActionCommand();
            String s = sendMSG(textField.getText());
            textArea.setText(textArea.getText() + "Me: " + content + ", send result: " + s + "\n");
            textField.setText("");
        }
    }

    private String sendMSG(String msg) {
        customObject = new StartUpClientModel(myClient.getToken());
        try {
//            iLuckyBagService8008Service = customObject.GetService("ILuckyBagService8008Service", ILuckyBagService8008Service.class);
            iLuckyBagService8008Service = ServiceFactorys.getInstance().GetProxyObject("http://218.78.51.29:5923/LuckyBagService8008Service", myClient.getToken(), "JSON", ILuckyBagService8008Service.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<MessageDto<List<String>, MsgInfoDto>> ilist = new ArrayList<MessageDto<List<String>, MsgInfoDto>>();
        MessageDto<List<String>, MsgInfoDto> listMsgInfoDtoMessageDto = new MessageDto<>();
        List<String> receive = new ArrayList<String>();
        MsgInfoDto msgInfoDto = new MsgInfoDto();
        receive.add("99999");
        msgInfoDto.MSGID = UUID.randomUUID().toString();
        msgInfoDto.RECEIVER = "99999";
        msgInfoDto.USERNAME = myClient.getUser();
        msgInfoDto.USERID = myClient.getPhone();
        msgInfoDto.USERPHONE = myClient.getPhone();
        msgInfoDto.MSGCONTENT = msg;
        msgInfoDto.MSGCREATETIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        msgInfoDto.THEMETYPEID = "11";
        msgInfoDto.RECEIVERTYPEID = "1";
        listMsgInfoDtoMessageDto.Receivers = receive;
        listMsgInfoDtoMessageDto.MessageBody = msgInfoDto;
        ilist.add(listMsgInfoDtoMessageDto);

        String s = iLuckyBagService8008Service.SaveMsgInfo(ilist);
        System.out.println(msgInfoDto.USERNAME + " send msg: " + msg + "; result: " + s);
        logger.info(msgInfoDto.USERNAME + " send msg: " + msg + "; result: " + s);
        return s;
    }


    private void getUsers() {
        customObject = new StartUpClientModel(myClient.getToken());
        try {
//            iLuckyBagService8008Service = customObject.GetService("ILuckyBagService8008Service", ILuckyBagService8008Service.class);
            iLuckyBagService8008Service = ServiceFactorys.getInstance().GetProxyObject("http://218.78.51.29:5923/LuckyBagService8008Service", myClient.getToken(), "JSON", ILuckyBagService8008Service.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<LoginResponseDto> loginResponseDtos = customObject.GetLoginedDesks("8008");
        roster.append("Online user: ").append(loginResponseDtos.size()).append("\n");
        roster.append("Time: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()));
        logger.info("Online user:" + loginResponseDtos.size());
        for (LoginResponseDto user : loginResponseDtos) {
//            roster.append(user.COUSTOMID).append(" \n");
            try {
                logger.info(user.COUSTOMID + ", " + user.COUSTOMNAME + ", login time: " + user.LOGINTIME);
//                logger.info(user.COUSTOMID + ", " + new String(user.COUSTOMNAME.getBytes("GBK"),"UTF-8") + ", login time: " + user.LOGINTIME);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("当前登录人数：" + loginResponseDtos.size());

    }
}
