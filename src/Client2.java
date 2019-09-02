
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;

public class Client2 extends Application
{
//    Button bt;
    TextArea ta=new TextArea(); //对话框
    TextField tf =new TextField(); //输入框
    DataInputStream in = null ;
    DataOutputStream out = null ;
    Socket socket ;  //用户套接字对象，用socket实现局域网内聊天室的小程序
    Stage stage; //场景
    public static void main(String args[]){
        launch(args);//开启画布
    }
    private void closeFunction() {
        boolean b= AlertWindows.display("关闭弹窗","是否关闭");
        if(b){
            stage.close();
        }
    }
    @Override
    public void start(Stage primaryStage) throws IOException {
        //画图
        stage=primaryStage;
        AnchorPane ap=new AnchorPane();
        Button bt= new Button("发送");
        stage.setOnCloseRequest(event -> {
            event.consume();        //消除点击×按钮直接关闭窗口的功能
            closeFunction();
        });
        //------------------------------------
        ta.setEditable(false);
        ta.setLayoutX(14.0);
        ta.setLayoutY(14.0);
        ta.setPrefHeight(302.0);
        ta.setPrefWidth(443.0);
        ta.setPromptText("欢迎来到聊天室 :)");
        //------------------------------------
        tf.setPromptText("请输入要发送的消息");
        tf.setLayoutX(14.0);
        tf.setLayoutY(330.0);
        tf.setPrefHeight(36.0);
        tf.setPrefWidth(443.0);
        //-------------------------------------
        bt.setLayoutX(462.0);
        bt.setLayoutY(330.0);
        bt.setMnemonicParsing(false);
        bt.setPrefHeight(36.0);
        bt.setPrefWidth(125.0);
        //-------------------------------------
        ListView lv=new ListView();
        lv.setLayoutX(461.0);
        lv.setLayoutY(12.0);
        lv.setPrefHeight(302.0);
        lv.setPrefWidth(125.0);
        //-------------------------------------
        ap.getChildren().addAll(lv,bt,ta,tf);
//        Parent root= FXMLLoader.load(getClass().getResource("ClientUI.fxml"));
//        Scene scene=new Scene(root);

        InetAddress ia=null;
        try {
            ia= InetAddress.getLocalHost();//获取本地ip
            String localip=ia.getHostAddress();
            socket = new Socket(localip, 4242);
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            System.out.println("时间:" + new Date());
            System.out.println("\t连接成功。。。");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        bt.setOnAction(event -> {
            try {
                if(tf.getText().length()!=0) {  //获取输入框内容
                out.writeUTF(socket.getLocalPort()+":"+tf.getText());  //以utf-8形式获取内容
                out.flush();   //清空缓冲区
            }
            } catch (Exception ex) {
                ex.printStackTrace() ;
            }finally {
                tf.setText("");  //清空输入框
            }
        });


        Thread readThread = new Thread(() -> {
            String message;
            try {
                while((message = in.readUTF()) != null) {
                    System.out.println("read : " + ta.getText());
                    ta.appendText(new Date() + "\n");
                    ta.appendText( message + "\n");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        readThread.start();

//        Parent root= FXMLLoader.load(getClass().getResource("ClientUI.fxml"));
//        Scene scene=new Scene(root);
////        Scene scene = new Scene(mainPane, 700, 600) ;
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("UsChat");
        Scene scene = new Scene(ap, 600, 400) ;
        primaryStage.setScene(scene);
        primaryStage.setTitle("UsChat");
        primaryStage.show();
    }
//    public void rideMsg(String msg){
//        System.out.println("read : " + ta.getText());
//        ta.appendText(new Date() + "\n");
//        ta.appendText( msg + "\n");
//    }
//    public void sendMsg(){
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    out.writeUTF(tf.getText());
//                    out.flush();
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }finally {
//                    //清空文本域
//                    tf.setText("");
//                }
//            }
//        });
//
//    }
}