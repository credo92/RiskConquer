package com.risk.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This class is used to launch the application.
 * @author rahul
 * @version 1.0.0
 *
 */
public class Main extends Application {

	/*
	 * (non-Javadoc)
	 * This method initiates the startup screen as soon as the application launches.
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Risk Map Editor");
		ClassLoader classLoader = getClass().getClassLoader();
		try {

			Group root = new Group();
			
			Scene scene = new Scene(root, 300, 300);
			scene.getStylesheets().add("application.css");
			
			VBox vbox = new VBox();
			vbox.setAlignment(Pos.BOTTOM_CENTER);
			vbox.getChildren().addAll(startGameButton(scene), mapEditorButton(scene), exitButton(scene));			
			
			BorderPane borderPane = new BorderPane();
			borderPane.setBottom(vbox);
			borderPane.prefWidthProperty().bind(scene.widthProperty());
			borderPane.setCenter(loadImage(scene, classLoader));

			root.getChildren().addAll(borderPane );

			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load image view.
	 * @param scene object of {@link Scene}
	 * @param classLoader object of {@link ClassLoader}
	 * @return imageView object of {@link ImageView}
	 */
	public static ImageView loadImage(Scene scene, ClassLoader classLoader) {
		final ImageView imageView = new ImageView();

		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(new File(classLoader.getResource("risk.jpg").getFile()));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Image image = new Image(inputStream);
		imageView.setImage(image);
		

		imageView.fitWidthProperty().bind(scene.widthProperty());
		imageView.fitHeightProperty().bind(scene.heightProperty());
		imageView.setPreserveRatio(true);

		return imageView;
	}

	/**
	 * Exit button
	 * @param scene {@link Scene} to depict UI screen.
	 * @return exitButton {@link Button} wherein button exits the application and kill its instance
	 */
	public static Button exitButton(Scene scene) {
		Button exitButton = new Button("Exit");
		exitButton.setOnAction(e -> Platform.exit());
		exitButton.setMaxWidth(scene.getWidth());
		return exitButton;
	}

	/**
	 * Map editor button
	 * @param scene {@link Scene} to depict UI screen.
	 * @return {@link Button} wherein button opens up a new map screen for editing.
	 */
	public static Button mapEditorButton(Scene scene) {
		Button mapEditorButton = new Button("Map Editor");
		mapEditorButton.setOnAction(new MapOption());
		mapEditorButton.setMaxWidth(scene.getWidth());
		return mapEditorButton;
	}

	/**
	 * Start game button
	 * @param scene {@link Scene} to depict UI screen.
	 * @return {@link Button} wherein button loads a chosen map and starts the game.
	 */
	public static Button startGameButton(Scene scene) {
		Button startGameButton = new Button("Load Map and Start Game");
		/*startGameButton.setOnAction(new MapEditor());
		startGameButton.setMaxWidth(scene.getWidth());*/
		startGameButton.setOnAction(new GamePlay());
		startGameButton.setMaxWidth(scene.getWidth());
		return startGameButton;
	}

	/**
	 * This is the main method to launch the application.
	 * @param args arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
