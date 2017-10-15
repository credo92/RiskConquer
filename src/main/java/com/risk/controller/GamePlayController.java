package com.risk.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import com.risk.entity.Continent;
import com.risk.entity.Map;
import com.risk.entity.Player;
import com.risk.entity.Territory;
import com.risk.map.util.MapUtil;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

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
	private VBox dataDisplay;

	@FXML
	private ListView<Territory> selectedTerritory;

	@FXML
	private ListView<Territory> adjTerritory;

	@FXML
	private Label playerChosen;

	@FXML
	private TextArea gameConsole;

	@FXML
	private Button placeArmy;

	private int numberOfPlayersSelected;

	private List<Player> gamePlayers;

	private Player playerPlaying;

	private Iterator<Player> iteratePlayer;

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
		numberOfPlayersCB.getItems().addAll(3, 4, 5, 6);
	}

	public void selectionOfPlayersListener() {
		numberOfPlayersCB.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
				setNumberOfPlayersSelected(numberOfPlayersCB.getSelectionModel().getSelectedItem());
				gamePlayers.clear();
				for (int i = 0; i < getNumberOfPlayersSelected(); i++) {
					String name = "Player" + i;
					gamePlayers.add(new Player(i, name, 20));
					appendTextToGameConsole(name + " created!\n");
				}
				appendTextToGameConsole("=======Players created======\n");
				numberOfPlayersCB.setDisable(true);
				iteratePlayer = gamePlayers.iterator();
				assignArmiesToPlayers();
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
		loadMapData();
		selectedTerritory.setCellFactory(param -> new ListCell<Territory>() {
			@Override
			protected void updateItem(Territory item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null || item.getName() == null) {
					setText(null);
				} else {
					setText(item.getName() + "-" + item.getArmies());
				}
			}
		});
		selectedTerritory.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

			}
		});

		adjTerritory.setCellFactory(param -> new ListCell<Territory>() {
			@Override
			protected void updateItem(Territory item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null || item.getName() == null) {
					setText(null);
				} else {
					setText(item.getName() + "-" + item.getArmies());
				}
			}
		});
		adjTerritory.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

			}
		});

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
	private void placeArmy(ActionEvent event) {
		int playerArmies = playerPlaying.getArmies();
		if (playerArmies > 0) {
			Territory territory = selectedTerritory.getSelectionModel().getSelectedItem();
			territory.setArmies(territory.getArmies() + 1);
			playerPlaying.setArmies(playerArmies - 1);
		}
		loadMapData();
		selectedTerritory.refresh();
		loadPlayerInRoundRobin();
	}

	@FXML
	private void reinforcement(ActionEvent event) {
		Territory territory = selectedTerritory.getSelectionModel().getSelectedItem();
		if (territory == null) {
			MapUtil.infoBox("Select a territory to place army on.", "Message", "");
			return;
		}

		Integer armies = Integer.valueOf(MapUtil.inputDailougeBox());
		territory.setArmies(territory.getArmies() + armies);
		selectedTerritory.refresh();

	}

	private void loadMapData() {
		dataDisplay.getChildren().clear();
		for (Continent continent : map.getContinents()) {
			dataDisplay.autosize();
			dataDisplay.getChildren().add(MapUtil.createNewTitledPane(continent));
		}
	}

	/**
	 * Distribute all territory among the player.
	 */
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
						territory.setArmies(territory.getArmies() + 1);
						player.setArmies(player.getArmies() - 1);
						player.getAssignedTerritory().add(territory);
						appendTextToGameConsole(territory.getName() + " assigned to " + player.getName() + " ! \n");
						break;
					}
					continue;
				}
			}
		}
		appendTextToGameConsole("======Territories assignation complete======\n");
		loadPlayerInRoundRobin();
	}
	
	private void assignArmiesToPlayers() {
		appendTextToGameConsole("======Assigning armies now.======\n");
		int armySizePerPlayer = 0;
		int noOfPlayers = gamePlayers.size();
		if(noOfPlayers==3)
			armySizePerPlayer=35;
		else if(noOfPlayers==4)
			armySizePerPlayer=30;
		else if(noOfPlayers==5)
			armySizePerPlayer=25;
		else if(noOfPlayers==6)
			armySizePerPlayer=20;
		
		for(Player player:gamePlayers) {
			player.setArmies(armySizePerPlayer);
		}
	}

	private void loadPlayerInRoundRobin() {
		if (!iteratePlayer.hasNext()) {
			iteratePlayer = gamePlayers.iterator();
		}
		playerPlaying = iteratePlayer.next();

		selectedTerritory.getItems().clear();
		adjTerritory.getItems().clear();
		for (Territory territory : playerPlaying.getAssignedTerritory()) {
			selectedTerritory.getItems().add(territory);
		}
		playerChosen.setText(playerPlaying.getName() + ":- " + playerPlaying.getArmies() + " armies left.");
	}

}