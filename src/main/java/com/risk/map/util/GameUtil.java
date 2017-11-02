package com.risk.map.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameUtil {
	/**
	 * Initialize the number of players.
	 */
	public static ChoiceBox<Integer> initializeTotalPlayers(ChoiceBox<Integer> numberOfPlayers) {
		numberOfPlayers.getItems().removeAll(numberOfPlayers.getItems());
		numberOfPlayers.getItems().addAll(3, 4, 5, 6);

		return numberOfPlayers;
	}

	public static void disableControl(Control... controls) {
		for (Control control : controls) {
			control.setDisable(true);
		}
	}

	public static void showControl(Control... controls) {
		for (Control control : controls) {
			control.setVisible(true);
		}
	}

	public static void hideControl(Control... controls) {
		for (Control control : controls) {
			control.setVisible(false);
		}
	}

	public static void closeScreen(Button button) {
		Stage stage = (Stage) button.getScene().getWindow();
		stage.close();
	}
	
	public static void clearCheckBox(CheckBox... checkBoxes) {
		for (CheckBox checkBox: checkBoxes) {
			checkBox.setText("");
			checkBox.setSelected(false);
		}
	}

	/**
	 * This method is used to enable the field on form elements.
	 * 
	 * @param controls
	 *            controls
	 */
	public static void enableControl(Control... controls) {
		for (Control control : controls) {
			control.setDisable(false);
		}
	}

	/**
	 * This method is used to set visible true of pane.
	 * 
	 * @param panes
	 *            panes
	 */
	public static void enableViewPane(Pane... panes) {
		for (Pane pane : panes) {
			pane.setVisible(true);
		}
	}

	/**
	 * This method is used to set visible true of pane.
	 * 
	 * @param panes
	 *            panes
	 */
	public static void disableViewPane(Pane... panes) {
		for (Pane pane : panes) {
			pane.setVisible(false);
		}
	}

	/**
	 * Info box to display message.
	 * 
	 * @param infoMessage
	 *            infomessage box
	 * @param titleBar
	 *            title bar for message
	 * @param headerMessage
	 *            header message
	 */
	public static void infoBox(String infoMessage, String titleBar, String headerMessage) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(titleBar);
		alert.setHeaderText(headerMessage);
		alert.setContentText(infoMessage);
		alert.showAndWait();
	}

}
