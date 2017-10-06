package com.app.team19;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MapEditor implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {

		final Stage mapEditorStage = new Stage();
		mapEditorStage.setTitle("Map Editor");
		Scene scene = null;
		try {
			scene = new Scene(FXMLLoader.load(getClass().getResource("MapLayout.fxml")));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mapEditorStage.setScene(scene);
		mapEditorStage.show();
	}
}
