package com.risk.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
	private ChoiceBox<Integer> numberOfPlayers;

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
	private ListView<Territory> selectedTerritoryList;

	@FXML
	private ListView<Territory> adjTerritoryList;

	@FXML
	private Label playerTime;

	private ScheduledExecutorService executor;

	@FXML
	private Label playerChosen;

	@FXML
	private TextArea gameConsole;

	@FXML
	private Button placeArmy;

	private int numberOfPlayersSelected;

	private List<Player> gamePlayerList;

	private Player playerPlaying;

	private Iterator<Player> playerIterator;

	public GamePlayController(Map map) {
		this.map = map;
	}

	public void initializeTotalPlayers() {
		numberOfPlayers.getItems().removeAll(numberOfPlayers.getItems());
		numberOfPlayers.getItems().addAll(3, 4, 5, 6);
	}

	public void selectionOfPlayersListener() {
		numberOfPlayers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
				setNumberOfPlayersSelected(numberOfPlayers.getSelectionModel().getSelectedItem());
				gamePlayerList.clear();
				for (int i = 0; i < getNumberOfPlayersSelected(); i++) {
					String name = "Player" + i;
					gamePlayerList.add(new Player(i, name));
					MapUtil.appendTextToGameConsole(name + " created!\n", gameConsole);
				}
				MapUtil.appendTextToGameConsole("=========Players created=========\n", gameConsole);
				numberOfPlayers.setDisable(true);
				playerIterator = gamePlayerList.iterator();
				assignArmiesToPlayers();
				assignTerritoryToPlayer();
			}
		});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		gamePlayerList = new ArrayList<>();
		initializeTotalPlayers();
		selectionOfPlayersListener();
		loadMapData();

		selectedTerritoryList.setCellFactory(param -> new ListCell<Territory>() {
			@Override
			protected void updateItem(Territory item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null || item.getName() == null) {
					setText(null);
				} else {
					setText(item.getName() + ":-" + item.getArmies() + "-" + item.getPlayer().getName());
				}
			}
		});
		selectedTerritoryList.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Territory territory = selectedTerritoryList.getSelectionModel().getSelectedItem();
				populateAdjTerritory(territory);
			}
		});

		adjTerritoryList.setCellFactory(param -> new ListCell<Territory>() {
			@Override
			protected void updateItem(Territory item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null || item.getName() == null) {
					setText(null);
				} else {
					setText(item.getName() + "-" + item.getArmies() + "-" + item.getPlayer().getName());
				}
			}
		});
		adjTerritoryList.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

			}
		});
	}

	private void populateAdjTerritory(Territory territory) {
		this.adjTerritoryList.getItems().clear();
		for (Territory adjTerr : territory.getAdjacentTerritories()) {
			this.adjTerritoryList.getItems().add(adjTerr);
		}
	}

	@FXML
	private void attack(ActionEvent event) {
	}

	@FXML
	private void fortify(ActionEvent event) {
		Territory selectedTerr = this.selectedTerritoryList.getSelectionModel().getSelectedItem();
		Territory adjTerr = this.adjTerritoryList.getSelectionModel().getSelectedItem();
		if (selectedTerr == null) {
			MapUtil.infoBox("Please choose Selected Territory as source.", "Message", "");
			return;
		} else if (adjTerr == null) {
			MapUtil.infoBox("Please choose Adjacent Territory as destination.", "Message", "");
			return;
		} else if (!(adjTerr.getPlayer().equals(playerPlaying))) {
			MapUtil.infoBox("Adjacent Territory does not belong to you.", "Message", "");
			return;
		}
		MapUtil.appendTextToGameConsole("======Fortification started======\n", gameConsole);
		Integer armies = Integer.valueOf(MapUtil.inputDialogueBoxForArmiesFortification());
		if (armies > 0) {
			if (selectedTerr.getArmies() == armies) {
				MapUtil.infoBox("You cannot move all the armies.", "Message", "");
				return;
			} else if (selectedTerr.getArmies() < armies) {
				MapUtil.infoBox("You don't have " + armies + " armies.", "Message", "");
				return;
			} else {
				selectedTerr.setArmies(selectedTerr.getArmies() - armies);
				adjTerr.setArmies(adjTerr.getArmies() + armies);
				selectedTerritoryList.refresh();
				adjTerritoryList.refresh();
				MapUtil.appendTextToGameConsole("=======Fortification ended=======\n", gameConsole);
			}
		}
		executor.shutdown();
		// initialize re-inforcement for the next player
		initializeReinforcement();
	}

	@FXML
	private void endTurn(ActionEvent event) {
		if (!executor.isShutdown()) {
			executor.shutdown();
		}
		start();
	}

	@FXML
	private void placeArmy(ActionEvent event) {
		int playerArmies = playerPlaying.getArmies();
		if (playerArmies > 0) {
			Territory territory = selectedTerritoryList.getSelectionModel().getSelectedItem();
			if (territory == null) {
				territory = selectedTerritoryList.getItems().get(0);
			}
			territory.setArmies(territory.getArmies() + 1);
			playerPlaying.setArmies(playerArmies - 1);
		}
		loadMapData();
		selectedTerritoryList.refresh();
		executor.shutdownNow();
		if (executor.isShutdown()) {
			start();
		}
		int count = 0;

		for (Player player : gamePlayerList) {
			if (player.getArmies() == 0) {
				count++;
			}
		}
		if (count == gamePlayerList.size()) {
			MapUtil.appendTextToGameConsole("====================================\n", gameConsole);
			MapUtil.appendTextToGameConsole("=====Armies assignation complete====\n", gameConsole);
			executor.shutdownNow();
			initializeReinforcement();
		}
		// loadPlayerInRoundRobin();
	}

	@FXML
	private void reinforcement(ActionEvent event) {
		if (playerPlaying.getArmies() > 0) {
			Territory territory = selectedTerritoryList.getSelectionModel().getSelectedItem();
			if (territory == null) {
				MapUtil.infoBox("Select a territory to place army on.", "Message", "");
				return;
			}

			Integer armies = Integer.valueOf(MapUtil.inputDailougeBox());
			if (playerPlaying.getArmies() < armies) {
				MapUtil.infoBox("You do not have sufficent armies.", "Message", "");
				return;
			}
			territory.setArmies(territory.getArmies() + armies);
			playerPlaying.setArmies(playerPlaying.getArmies() - armies);
			MapUtil.appendTextToGameConsole(armies + ": assigned to territory " + territory.getName() + "\n",
					gameConsole);
			selectedTerritoryList.refresh();
			playerChosen.setText(playerPlaying.getName() + ":- " + playerPlaying.getArmies() + " armies left.");
		}
		// start attack phase
		if (playerPlaying.getArmies() <= 0) {
			initializeAttack();
		}
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
		MapUtil.appendTextToGameConsole("======Assigning territories========\n", gameConsole);
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
			for (Player player : gamePlayerList) {
				for (Territory territory : allterritories) {
					if (territory.getPlayer() == null) {
						count++;
						territory.setPlayer(player);
						territory.setArmies(territory.getArmies() + 1);
						player.setArmies(player.getArmies() - 1);
						player.getAssignedTerritory().add(territory);
						MapUtil.appendTextToGameConsole(
								territory.getName() + " assigned to " + player.getName() + " ! \n", gameConsole);
						break;
					}
					continue;
				}
			}
		}
		MapUtil.appendTextToGameConsole("===Territories assignation complete===\n", gameConsole);
		loadMapData();
		start();
	}

	private void assignArmiesToPlayers() {
		MapUtil.appendTextToGameConsole("========Assigning armies now.======\n", gameConsole);
		int armySizePerPlayer = 0;
		int noOfPlayers = gamePlayerList.size();
		if (noOfPlayers == 3)
			armySizePerPlayer = 15;
		else if (noOfPlayers == 4)
			armySizePerPlayer = 30;
		else if (noOfPlayers == 5)
			armySizePerPlayer = 25;
		else if (noOfPlayers == 6)
			armySizePerPlayer = 20;

		for (Player player : gamePlayerList) {
			player.setArmies(armySizePerPlayer);
			MapUtil.appendTextToGameConsole(player.getName() + " assigned: " + armySizePerPlayer + "\n", gameConsole);
		}
	}

	private void loadPlayerInRoundRobin() {
		if (!playerIterator.hasNext()) {
			playerIterator = gamePlayerList.iterator();
		}
		playerPlaying = playerIterator.next();
		MapUtil.appendTextToGameConsole("=====================================\n", gameConsole);
		MapUtil.appendTextToGameConsole(playerPlaying.getName() + "!....started playing.\n", gameConsole);
		selectedTerritoryList.getItems().clear();
		adjTerritoryList.getItems().clear();
		for (Territory territory : playerPlaying.getAssignedTerritory()) {
			selectedTerritoryList.getItems().add(territory);
		}
		playerChosen.setText(playerPlaying.getName() + ":- " + playerPlaying.getArmies() + " armies left.");
	}

	private void calculateReinforcementArmies() {
		if (this.playerPlaying != null) {
			int currentArmies = playerPlaying.getArmies();
			int territoryCount = playerPlaying.getAssignedTerritory().size();
			if (territoryCount < 9) {
				playerPlaying.setArmies(currentArmies + 3);
			} else {
				playerPlaying.setArmies((territoryCount / 3) + currentArmies);
			}
			playerChosen.setText(playerPlaying.getName() + ":- " + playerPlaying.getArmies() + " armies left.");
		} else {
			MapUtil.appendTextToGameConsole("Error!. No player playing.", gameConsole);
		}
	}

	private void start() {
		executor = Executors.newSingleThreadScheduledExecutor();
		executor.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				Platform.runLater(() -> loadPlayerInRoundRobin());
			}

		}, 0, 300000, TimeUnit.MILLISECONDS);
	}

	private void initializeReinforcement() {
		start();
		calculateReinforcementArmies();
		placeArmy.setDisable(true);
		reinforcement.requestFocus();
		MapUtil.appendTextToGameConsole("=======================================\n", gameConsole);
		MapUtil.appendTextToGameConsole("======Start Reinforcement! =========== \n", gameConsole);
		MapUtil.appendTextToGameConsole(playerPlaying.getName() + "\n", gameConsole);
	}

	private void initializeAttack() {
		attack.requestFocus();
		MapUtil.appendTextToGameConsole("======================================= \n", gameConsole);
		MapUtil.appendTextToGameConsole("===Attack phase under developement! === \n", gameConsole);
		initializeFortification();
	}

	private void initializeFortification() {
		fortify.requestFocus();
		MapUtil.appendTextToGameConsole("======================================= \n", gameConsole);
		MapUtil.appendTextToGameConsole("====Fortification phase started! ====== \n", gameConsole);
	}

	public int getNumberOfPlayersSelected() {
		return numberOfPlayersSelected;
	}

	public void setNumberOfPlayersSelected(int numberOfPlayersSelected) {
		this.numberOfPlayersSelected = numberOfPlayersSelected;
	}

}