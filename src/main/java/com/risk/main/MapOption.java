package com.risk.main;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Garvpreet Singh
 * This class is used to provide view to user to choose map options in the game.
 * @version 1.0.0
 */
public class MapOption implements EventHandler<ActionEvent> {
	
	/*
	 * (non-Javadoc)
	 * This method is overridden to create a scene at UI end.
	 * @see javafx.event.EventHandler#handle(javafx.event.Event)
	 */
	@Override
	public void handle(ActionEvent event) {

		Group root = new Group();

		final Stage mapOptionStage = new Stage();
		mapOptionStage.setTitle("Choose option");
		Scene scene = new Scene(root, 300, 300);
		scene.getStylesheets().add("application.css");

		VBox vbox = new VBox();
		vbox.setAlignment(Pos.BOTTOM_CENTER);
		vbox.getChildren().addAll(newMapButton(scene), editMapButton(scene), exitMapButton(scene));

		BorderPane borderPane = new BorderPane();
		borderPane.setBottom(vbox);
		borderPane.prefWidthProperty().bind(scene.widthProperty());
		borderPane.setCenter(Main.loadImage(scene, getClass().getClassLoader()));
		root.getChildren().addAll(borderPane);

		mapOptionStage.setScene(scene);
		mapOptionStage.alwaysOnTopProperty();
		mapOptionStage.show();
	}

	/**
	 * @param scene {@link Scene} to depict UI screen.
	 * @return {@link Button} wherein button opens up a new map
	 */
	public static Button newMapButton(Scene scene) {
		Button mapEditorButton = new Button("New Map");
		mapEditorButton.setOnAction(new CreateMap());
		mapEditorButton.setMaxWidth(scene.getWidth());

		return mapEditorButton;
	}

	/**
	 * @param scene {@link Scene} to depict UI screen.
	 * @return {@link Button} wherein button opens up a screen to load and edit map.
	 */
	public static Button editMapButton(Scene scene) {
		Button mapEditorButton = new Button("Load & Edit Map");
		mapEditorButton.setOnAction(new MapEditor());
		mapEditorButton.setMaxWidth(scene.getWidth());

		return mapEditorButton;
	}
	
	/**
	 * @param scene {@link Scene} to depict UI screen.
	 * @return {@link Button} wherein button exits the window.
	 */
	public static Button exitMapButton(Scene scene) {
		Button mapEditorButton = new Button("Exit");
		mapEditorButton.setOnAction(e -> ((Stage) mapEditorButton.getScene().getWindow()).close());
		mapEditorButton.setMaxWidth(scene.getWidth());

		return mapEditorButton;
	}
}