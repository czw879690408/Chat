import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertWindows {
    public static boolean ansver;
    public static boolean display(String title,String mg){
        Stage stage1=new Stage();
        stage1.initModality(Modality.APPLICATION_MODAL);
        Label label=new Label();
        label.setText(mg);
        AnchorPane ap=new AnchorPane();//盒子
        label.setLayoutX(143.0);
        label.setLayoutY(40.0);

        Button button=new Button("是");
        button.setLayoutX(108.0);
        button.setLayoutY(91.0);
        button.setPrefWidth(69);
        button.setPrefHeight(30);
        button.setOnMouseClicked(event -> {                 //鼠标点击后要做的事
                ansver=true;                                  //点击是，返回true
            stage1.close();
        });
        Button button1=new Button("否");
        button1.setLayoutX(201.0);
        button1.setLayoutY(91.0);
        button1.setPrefWidth(69);
        button1.setPrefHeight(30);
        button1.setOnMouseClicked(event -> {                      //点击否，返回false
            ansver=false;
            stage1.close();
        });
//        VBox vBox=new VBox();
//        vBox.getChildren().addAll(label,button,button1);
//        vBox.setAlignment(Pos.CENTER);
        ap.getChildren().addAll(button,button1,label);
        Scene scene=new Scene(ap,361,182);//画布

        stage1.setTitle(title);
        stage1.setScene(scene);
        stage1.showAndWait();  //在函数执行完前不执行下面语句
        return ansver;
    }
}