package com.risk.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.risk.entity.Continent;
import com.risk.entity.Map;
import com.risk.entity.Player;
import com.risk.entity.Territory;

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

	private List<Player> gamePlayers;

	private StringBuilder gameConsoleOutput;

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
				gamePlayers.clear();
				for (int i = 0; i < getNumberOfPlayersSelected(); i++) {
					String name = "Player" + i;
					gamePlayers.add(new Player(i, name));
					appendTextToGameConsole(name + " created!\n");
				}
				appendTextToGameConsole("=======Players created======\n");
				numberOfPlayersCB.setDisable(true);
				assignTerritoryToPlayer();
			}
		});
	}

	public void appendTextToGameConsole(String valueOf) {
		Platform.runLater(() -> gameConsole.appendText(valueOf));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		gameConsoleOutput = new StringBuilder();
		gamePlayers = new ArrayList<>();
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

	private void loadMapData() {

	}

	private void assignTerritoryToPlayer() {
		appendTextToGameConsole("======Assigning territories======\n");
		List<Territory> allterritories = new ArrayList<>();

		if (map.getContinents() != null) {
			for (Continent continent : map.getContinents()) {
				if (continent != null && continent.getTerritories() != null) {
					for (Territory territory : continent.getTerritories()) {
						allterritories.add(territory);
					}
				}
			}
		}
		int count = 0;
		int totalTerritory = allterritories.size();
		while (count < totalTerritory) {
			for (Player player : gamePlayers) {
				for (Territory territory : allterritories) {
					if (territory.getPlayer() == null) {
						count++;
						territory.setPlayer(player);
						appendTextToGameConsole(territory.getName() + " assigned to " + player.getName() + " ! \n");
						break;
					}
					continue;
				}
			}
		}
		appendTextToGameConsole("======Territories assignation complete======\n");
	}
}