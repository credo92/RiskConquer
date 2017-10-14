package com.app.team19;

import java.io.File;
import java.io.IOException;

import com.risk.controller.MapEditorController;
import com.risk.entity.Map;
import com.risk.map.util.MapFileParser;
import com.risk.map.util.MapUtil;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MapSelectorEditor implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		
		/*Group root = new Group();

		final Stage mapOptionStage = new Stage();
		mapOptionStage.setTitle("Load Map");
		Scene scene = new Scene(root, 300, 300);
		// scene.getStylesheets().add("application.css");

		VBox vbox = new VBox();
		vbox.setAlignment(Pos.BOTTOM_CENTER);
		vbox.getChildren().addAll(loadExistingMapButton(scene));

		BorderPane borderPane = new BorderPane();
		borderPane.setBottom(vbox);
		borderPane.prefWidthProperty().bind(scene.widthProperty());
		//borderPane.setCenter(Main.loadImage(scene, getClass().getClassLoader()));
		root.getChildren().addAll(borderPane);

		mapOptionStage.setScene(scene);
		// mapOptionStage.alwaysOnTopProperty();
		mapOptionStage.show();*/
		
		

		File file = MapUtil.showFileChooser();

		MapFileParser fileLoaderAndParser = new MapFileParser();
		Map map = fileLoaderAndParser.parseAndReadMapFile(file);
		MapEditorController controller = new MapEditorController(map, file);
		
		//TODO validation of map file selected before proceeding to select 
		
		final Stage mapSelectorStage = new Stage();
		mapSelectorStage.setTitle("Game Screen");
		
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("MapSelectorLayout.fxml"));
		//FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("MapLayout.fxml"));
		//loader.setController(controller);

		Parent root = null;
		try {
			root = (Parent) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
		/*try {
			scene = new Scene(FXMLLoader.load(getClass().getClassLoader().getResource("MapSelectorLayout.fxml")));
		} catch (Exception e) {
			e.printStackTrace();
		}*/	
		
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

