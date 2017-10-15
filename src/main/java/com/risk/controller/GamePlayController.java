package com.risk.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.risk.entity.Map;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

public class GamePlayController implements Initializable {

	private Map map;

	@FXML
	private ChoiceBox<Integer> numberOfPlayersCB;

	@FXML
	private Button attack;

	@FXML
	private Button fortify;

	@FXML
	private Button endTurn;

	@FXML
	private Button reinforcement;

	@FXML
	private Pane dataDisplay;

	@FXML
	private ListView selectedTerritory;

	@FXML
	private ListView adjTerritory;

	@FXML
	private TextArea gameConsole;

	private int numberOfPlayersSelected;

	public GamePlayController(Map map) {
		this.map = map;
	}

	public int getNumberOfPlayersSelected() {
		return numberOfPlayersSelected;
	}

	public void setNumberOfPlayersSelected(int numberOfPlayersSelected) {
		this.numberOfPlayersSelected = numberOfPlayersSelected;
	}

	public void initializeTotalPlayers() {
		numberOfPlayersCB.getItems().removeAll(numberOfPlayersCB.getItems());
		numberOfPlayersCB.getItems().addAll(2, 3, 4, 5);
	}

	public void selectionOfPlayersListener() {
		numberOfPlayersCB.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
				setNumberOfPlayersSelected(numberOfPlayersCB.getSelectionModel().getSelectedItem());
			}
		});
	}

	public void appendTextToGameConsole(String valueOf) {
		Platform.runLater(() -> gameConsole.appendText(valueOf));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeTotalPlayers();
		selectionOfPlayersListener();
	}

	@FXML
	private void attack(ActionEvent event) {
	}

	@FXML
	private void fortify(ActionEvent event) {
	}

	@FXML
	private void endTurn(ActionEvent event) {
	}

	@FXML
	private void reinforcement(ActionEvent event) {
	}

}