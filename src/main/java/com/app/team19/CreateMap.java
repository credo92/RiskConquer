package com.app.team19;

import java.io.IOException;

import com.risk.controller.MapEditorController;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CreateMap implements EventHandler<ActionEvent> {
	 Parent rootNode = null;
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
		    this.rootNode = root;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Scene scene = new Scene(root);
		newMapStage.setScene(scene);
		newMapStage.show();
	}
	
}
