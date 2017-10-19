package com.risk.main;

import java.io.IOException;

import com.risk.controller.MapEditorController;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Garvpreet Singh
 * This class is used to create map from MapLayout.fxml
 * @version 1.0.0
 *
 */
public class CreateMap implements EventHandler<ActionEvent> {
	
	/*
	 * (non-Javadoc)
	 * This method is overridden to create a scene at UI end.
	 * @see javafx.event.EventHandler#handle(javafx.event.Event)
	 */
	@Override
	public void handle(ActionEvent event) {

		final Stage newMapStage = new Stage();
		newMapStage.setTitle("New Map");

		MapEditorController controller = new MapEditorController();

		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("MapLayout.fxml"));
		loader.setController(controller);

		Parent root = null;
		try {
			root = (Parent) loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Scene scene = new Scene(root);
		newMapStage.setScene(scene);
		newMapStage.show();
	}
}
