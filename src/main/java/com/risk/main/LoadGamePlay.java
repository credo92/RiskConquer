package com.risk.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import com.risk.controller.GamePlayController;
import com.risk.map.util.MapUtil;
import com.risk.model.CardModel;
import com.risk.model.GameModel;
import com.risk.model.PlayerGamePhase;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LoadGamePlay implements EventHandler<ActionEvent>
	{
		/*
		 * (non-Javadoc)
		 * This method is overridden to create a scene at UI end.
		 * @see javafx.event.EventHandler#handle(javafx.event.Event)
		 */
	@Override
	public void handle(ActionEvent event) {

		File file = MapUtil.savedGameFileChooser();
		GamePlayController controller = null;
		GameModel gameModel = null;
		PlayerGamePhase playerGamePhase = null;
		CardModel cardModel = null;
		try {
	         FileInputStream fileIn = new FileInputStream(file);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         controller = (GamePlayController) in.readObject();
	    //     gameModel = (GameModel) in.readObject();
	         in.close();
	         fileIn.close();
	      } catch (Exception i) {
	         i.printStackTrace();
	      }
		
		GamePlayController newcontroller = new GamePlayController(controller.getMap());
		//System.out.println(controller.getNumberOfPlayersSelected());
		// TODO validation of map file selected before proceeding to select

		final Stage mapSelectorStage = new Stage();
		mapSelectorStage.setTitle("Game Screen");

		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("MapSelectorLayout.fxml"));
		loader.setController(newcontroller);
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
	 * @param scene
	 *            {@link Scene} to depict UI screen.
	 * @return {@link Button} wherein button opens up a new map
	 */
	public static Button loadExistingMapButton(Scene scene) {
		Button loadMapButton = new Button("Load Map");
		loadMapButton.setOnAction(new GamePlay());
		loadMapButton.setMaxWidth(scene.getWidth());

		return loadMapButton;
	}

}
