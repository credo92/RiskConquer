package com.app.team19;

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
 * @author rahul
 *
 */
public class Main extends Application {

	/*
	 * (non-Javadoc)
	 * 
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
			//borderPane.setCenter(loadImage(scene, classLoader));

			root.getChildren().addAll(borderPane );

			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param scene
	 * @param classLoader
	 * @return
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
	 * @return
	 */
	public static Button exitButton(Scene scene) {
		Button exitButton = new Button("Exit");
		exitButton.setOnAction(e -> Platform.exit());
		exitButton.setMaxWidth(scene.getWidth());
		return exitButton;
	}

	/**
	 * @return
	 */
	public static Button mapEditorButton(Scene scene) {
		Button mapEditorButton = new Button("Map Editor");
		mapEditorButton.setOnAction(new MapEditor());
		mapEditorButton.setMaxWidth(scene.getWidth());

		return mapEditorButton;
	}

	/**
	 * @return
	 */
	public static Button startGameButton(Scene scene) {
		Button startGameButton = new Button("Start Game");
		startGameButton.setOnAction(new MapSelectorEditor());
		//startGameButton.setOnAction(e -> System.out.println("Game started"));
		startGameButton.setMaxWidth(scene.getWidth());

		return startGameButton;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
