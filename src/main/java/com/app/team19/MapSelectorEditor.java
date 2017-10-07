package com.app.team19;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MapSelectorEditor implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		
		final Stage mapSelectorStage = new Stage();
		mapSelectorStage.setTitle("Map Selector");
		Scene scene = null;
		try {
			scene = new Scene(FXMLLoader.load(getClass().getClassLoader().getResource("MapSelectorLayout.fxml")));
		} catch (Exception e) {
			e.printStackTrace();
		}	
		mapSelectorStage.setScene(scene);
		mapSelectorStage.show();
	}
}