package com.company;

import java.net.*;
import java.io.*;
import java.util.*;

public class Server {
    private int port;
    private List<Socket> clients;
    private ServerSocket server;

    public static void main(String[] args) {
        new Server();
    }

    public Server() {
        try {
            port = 8080;
            clients = new ArrayList<Socket>();
            server = new ServerSocket(port);
            System.out.println("服务器启动成功。。。");

            while (true) {
                Socket socket = server.accept();
                clients.add(socket);
                Mythread mythread = new Mythread(socket);
                mythread.start();
            }
        } catch (Exception ex) {
        }
    }

    class Mythread extends Thread {
        Socket ssocket;
        private BufferedReader br;
        private PrintWriter pw;
        private String msg;
        public Mythread(Socket s) {
            ssocket = s;
        }
        public void run() {
            try {
                br = new BufferedReader(new InputStreamReader(ssocket.getInputStream()));
                msg = "欢迎【" + ssocket.getInetAddress() + "】进入聊天室！当前聊天室有【"
                        + clients.size() + "】人";
                sendMsg();
                while ((msg = br.readLine()) != null) {
                    msg = "【" + ssocket.getInetAddress() + "】说：" + msg;
                    sendMsg();
                }
            } catch (Exception ex) {
            }
        }

        public void sendMsg() {
            try {
                System.out.println(msg);

                for (int i = clients.size() - 1; i >= 0; i--) {
                    pw = new PrintWriter(clients.get(i).getOutputStream(), true);
                    pw.println(msg);
                    pw.flush();
                }
            } catch (Exception ex) {
            }
        }
    }

}
