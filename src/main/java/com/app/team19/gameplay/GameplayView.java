package com.app.team19.gameplay;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GameplayView implements EventHandler<ActionEvent> {

    public void handle(ActionEvent event) {

        final Stage dialog = new Stage();
        dialog.setTitle("Select Number of Leaders");
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.alwaysOnTopProperty();
        dialog.isResizable();

        //Defining DialogVbox
        VBox dialogVbox = new VBox(20);

        //Defining Buttons
        Button buttonOne = new Button("One");
        Button buttonTwo = new Button("Two");
        Button buttonThree = new Button("Three");
        Button buttonFour = new Button("Four");
        Button cancel = new Button("Cancel");

        //Adding Buttons to dialogVbox
        dialogVbox.getChildren().add(buttonOne);
        dialogVbox.getChildren().add(buttonTwo);
        dialogVbox.getChildren().add(buttonThree);
        dialogVbox.getChildren().add(buttonFour);
        dialogVbox.getChildren().add(cancel);



        buttonOne.setOnAction(event1 -> {GameplayController gc = new GameplayController();
        gc.selectedNumberOfPlayers(1);
        });
        buttonTwo.setOnAction(event1 -> {GameplayController gc = new GameplayController();
            gc.selectedNumberOfPlayers(2);
        });
        buttonThree.setOnAction(event1 -> {GameplayController gc = new GameplayController();
            gc.selectedNumberOfPlayers(3);
        });
        buttonFour.setOnAction(event1 -> {GameplayController gc = new GameplayController();
            gc.selectedNumberOfPlayers(4);
        });

        Scene dialogScene = new Scene(dialogVbox, 400, 300);
        dialogScene.getStylesheets().add("application.css");
        dialog.setScene(dialogScene);
        dialog.show();

    }

}
