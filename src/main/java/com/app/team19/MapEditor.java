package com.app.team19;

import java.io.File;
import java.io.IOException;

import com.risk.controller.MapEditorController;
import com.risk.entity.Map;
import com.risk.exception.InvalidMapException;
import com.risk.map.util.MapFileParser;
import com.risk.map.util.MapUtil;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MapEditor implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {

		final Stage mapEditorStage = new Stage();
		mapEditorStage.setTitle("Map Editor");

		File file = MapUtil.showFileChooser();

		MapFileParser fileLoaderAndParser = new MapFileParser();
		Map map = null;
		try {
			map = fileLoaderAndParser.parseAndReadMapFile(file);
		} catch (InvalidMapException ex) {
			MapUtil.infoBox(ex.getMessage(), "Error", "Invalid Map");
			return;
		}
		MapEditorController controller = new MapEditorController(map, file);

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
		mapEditorStage.setScene(scene);
		mapEditorStage.show();
	}
}
