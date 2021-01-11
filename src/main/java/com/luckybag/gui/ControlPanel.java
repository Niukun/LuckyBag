package com.luckybag.gui;

import com.lgc.LoginResponseDto;
import com.lgc.MessageDto;
import com.lgc.ServiceFactorys;
import com.lgc.StartUpClientModel;
import com.luckybag.entity.ILuckyBagService8008Service;
import com.luckybag.entity.MsgInfoDto;
import com.luckybag.multiclient.websocket.MyWebSocketClient;

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
    JScrollPane jScrollPane;
    private JPanel controlPanel;
    private JTextField textField;
    private MyWebSocketClient myClient = null;
    static int left = 0;
    static int top = 0;
    StartUpClientModel customObject;
    ILuckyBagService8008Service iLuckyBagService8008Service;
    StringBuilder sb = new StringBuilder();

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
        if(myClient.getToken().isEmpty()){
            System.out.println("Login failed");
            try {
                throw new Exception("Login failed, please check out input phone.");
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }else{
            System.out.println("Login success, token is: " + myClient.getToken());
        }

        prepareGUI(phone + " -- " + name);
    }

    private void prepareGUI(String name) {
        mainFrame = new JFrame("Lucky Bag Chat");
        mainFrame.setSize(380, 400);
        mainFrame.setLayout(new BorderLayout());
        headerLabel = new JLabel(name, JLabel.CENTER);
        textArea = new JTextArea();
        textArea.setLineWrap(true);

        textArea.setSize(350, 100);
        jScrollPane =new JScrollPane(textArea);
        jScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        mainFrame.add(headerLabel, BorderLayout.NORTH);
        mainFrame.add(jScrollPane, BorderLayout.CENTER);
        mainFrame.add(controlPanel, BorderLayout.SOUTH);
        mainFrame.setVisible(true);
    }

    public void showEventDemo() {
        JButton sendButton = new JButton("Send");
        JButton clearButton = new JButton("Clear");
        textField = new JTextField(15);
        sendButton.addActionListener(new ButtonClickListener());
        clearButton.addActionListener(new ButtonClearListener());
        textField.addActionListener(new TextFieldListener());

        controlPanel.add(sendButton);
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


    private class ButtonClickListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            if(textField.getText().trim().isEmpty()){
                textField.setText("");
                return;
            }
            sb.append(textField.getText() + "\n");
            textArea.setText(sb.toString());
//            sendMSG(textField.getText());
            for(int i = 0; i < 50; i++){
                sendMSG(textField.getText() +" " + i + ": "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()));
            }
            textField.setText("");


        }
    }


    private class ButtonClearListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
//            sb.delete(0,sb.length());
//            textArea.setText(sb.toString());
            getUsers();

        }
    }

    private class TextFieldListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(textField.getText().trim().isEmpty()){
                textField.setText("");
                return;
            }
            String content = e.getActionCommand();
            textArea.setText(textArea.getText() + content + "\n");
            sendMSG(textField.getText());
            textField.setText("");
        }
    }

    private void sendMSG(String msg){
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
    }



    private void getUsers(){
        customObject = new StartUpClientModel(myClient.getToken());
        try {
//            iLuckyBagService8008Service = customObject.GetService("ILuckyBagService8008Service", ILuckyBagService8008Service.class);
            iLuckyBagService8008Service = ServiceFactorys.getInstance().GetProxyObject("http://218.78.51.29:5923/LuckyBagService8008Service", myClient.getToken(), "JSON", ILuckyBagService8008Service.class);
        } catch (Exception e) {
            e.printStackTrace();
        }


        List<LoginResponseDto> loginResponseDtos = customObject.GetLoginedDesks("8008");
        System.out.println("当前登录人数：" + loginResponseDtos.size());

    }
}
