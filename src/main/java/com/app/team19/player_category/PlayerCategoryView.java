package com.app.team19.player_category;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PlayerCategoryView extends Application {

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Leader Category");
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
