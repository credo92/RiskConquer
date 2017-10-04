package gameplay;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameplayView extends Application {

    public void print(){
    System.out.println("testing");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Select Number of Leaders");
        Group root = new Group();
        Scene scene = new Scene(root, 300, 300);

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.BOTTOM_CENTER);
        vbox.getChildren().addAll();


        BorderPane borderPane = new BorderPane();
        borderPane.setBottom(vbox);
        borderPane.prefWidthProperty().bind(scene.widthProperty());
        //borderPane.setCenter(loadImage(scene, classLoader));

        root.getChildren().addAll(borderPane );

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
