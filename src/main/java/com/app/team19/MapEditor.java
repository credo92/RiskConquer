package com.app.team19;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MapEditor implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {

		final Stage dialog = new Stage();
		dialog.setTitle("Map Editor");
	//	String text = showSingleFileChooser();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.alwaysOnTopProperty();
		dialog.isResizable();
		VBox dialogVbox = new VBox(20);
		
	 
	      
		Canvas canvas = new Canvas(300, 250);
		dialogVbox.getChildren().add(new Text("Map Editor"));
		
		dialogVbox.getChildren().add(canvas);
		
		dialogVbox.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				System.out.println("X:" + event.getScreenX());
				System.out.println("Y:" + event.getSceneY());
			}
		});
		
		Scene dialogScene = new Scene(dialogVbox, 800, 1000);
		dialogScene.getStylesheets().add("application.css");
		dialog.setScene(dialogScene);
		dialog.show();

	}

	private String showSingleFileChooser() {
		FileChooser filechooser = new FileChooser();
		File file = filechooser.showOpenDialog(null);
		
		Scanner scan = null;
		ArrayList<String> map0 = new ArrayList<>();
		ArrayList<String> map2 = new ArrayList<>();
		ArrayList<String> map3 = new ArrayList<>();
		try {
			scan = new Scanner(new FileInputStream(file));

			int count = 0;
			while (scan.hasNext()) {
				String line = scan.nextLine();
				if (!line.isEmpty() && count == 0) {
					map0.add(line);
				} else if (!line.isEmpty() && count == 1) {
					map2.add(line);
				} else if (!line.isEmpty() && count == 2) {
					map3.add(line);
				} else {
					count++;
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return "File read";
	}
}
