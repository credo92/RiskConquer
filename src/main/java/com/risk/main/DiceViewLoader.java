package com.risk.main;

import java.io.IOException;

import com.risk.controller.DiceRollController;
import com.risk.model.DiceModel;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author rahul
 *
 */
public class DiceViewLoader implements EventHandler<ActionEvent> {

	private DiceModel diceModel;
	/**
	 * @param attack
	 * @param defend
	 */
	public DiceViewLoader(DiceModel diceModel) {
		this.diceModel = diceModel;
	}

	/* (non-Javadoc)
	 * @see javafx.event.EventHandler#handle(javafx.event.Event)
	 */
	public void handle(ActionEvent event) {
		final Stage newMapStage = new Stage();
		newMapStage.setTitle("Attack Window");

		DiceRollController diceController = new DiceRollController(diceModel);

		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("DiceView.fxml"));
		loader.setController(diceController);

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
