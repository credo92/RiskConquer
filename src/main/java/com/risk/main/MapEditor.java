package com.risk.main;

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

/**
 * @author Garvpreet Singh
 * This class is used to handle maps from MapSelectorLayout.fxml
 * @version 1.0.0
 */
public class MapEditor implements EventHandler<ActionEvent> {
	
	/*
	 * (non-Javadoc)
	 * This method is overridden to create a scene at UI end from MapLayout.fxml
	 * @see javafx.event.EventHandler#handle(javafx.event.Event)
	 */
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
