import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

public class Server2 {  //服务器
    ArrayList<DataOutputStream> clientOutputStream = null ;   //给每个客户端配置一个数据输出流
    public static void main(String[] args) {
        new Server2().start() ;
    }
    private void start() {
        System.out.println("服务器启动。。。");
        clientOutputStream = new ArrayList<>();//初始化

        try {
            ServerSocket server = new ServerSocket(4242);//新建服务器对象

            while(true) {
                Socket sock = server.accept(); //监听是否有用户接入，有则执行下面语句，没有则保存休眠
                DataOutputStream out = new DataOutputStream(new BufferedOutputStream(sock.getOutputStream()));//为用户分配输出流
                clientOutputStream.add(out); //将输出流加入到用户输出列表里
                new Thread(new ClientHandler(sock)).start() ;//新开一个用户处理线程
                System.out.println(new Date()) ;
                System.out.println("\t建立一个连接") ;
            }
        } catch (Exception ex) {
            ex.printStackTrace() ;//错误处理
        }
    }

    private class ClientHandler implements Runnable {
        Socket socket = null ;  //用户套接字
        DataInputStream read = null ;  //用户输入流
        public ClientHandler(Socket socket) {
            try {
                this.socket = socket ;
                read = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            } catch (Exception ex) {
                ex.printStackTrace() ;
            }
        }
        @Override
        public void run() {
            String msg ;
            try {
                while((msg = read.readUTF()) != null) {
                    System.out.println("read : " + msg) ;
                    sendToEveryOne(msg) ;
                }
            } catch (Exception ex) {
                ex.printStackTrace() ;
            }
        }
    }
    private void sendToEveryOne(String msg) {
        try {
            for (DataOutputStream writer : clientOutputStream) {
                writer.writeUTF(msg);
                writer.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace() ;
        }
    }
}
