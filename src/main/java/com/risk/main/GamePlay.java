package com.risk.main;

import java.io.File;
import java.io.IOException;

import com.risk.controller.GamePlayController;
import com.risk.entity.Map;
import com.risk.exception.InvalidMapException;
import com.risk.map.util.MapFileParser;
import com.risk.map.util.MapUtil;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class GamePlay implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {

		File file = MapUtil.showFileChooser();

		MapFileParser fileLoaderAndParser = new MapFileParser();
		Map map = null;
		try {
			map = fileLoaderAndParser.parseAndReadMapFile(file);
		} catch (InvalidMapException ex) {
			MapUtil.infoBox(ex.getMessage(), "Error", "Invalid Map");
			return;
		}
		GamePlayController controller = new GamePlayController(map);

		// TODO validation of map file selected before proceeding to select

		final Stage mapSelectorStage = new Stage();
		mapSelectorStage.setTitle("Game Screen");

		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("MapSelectorLayout.fxml"));
		loader.setController(controller);
		Parent root = null;
		try {
			root = (Parent) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Scene scene = new Scene(root);
		mapSelectorStage.setScene(scene);
		mapSelectorStage.show();
	}

	/**
	 * @return
	 */
	public static Button loadExistingMapButton(Scene scene) {
		Button loadMapButton = new Button("Load Map");
		loadMapButton.setOnAction(new MapEditor());
		loadMapButton.setMaxWidth(scene.getWidth());

		return loadMapButton;
	}
}
