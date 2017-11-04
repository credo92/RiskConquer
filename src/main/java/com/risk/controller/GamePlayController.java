package com.risk.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.Stack;

import com.risk.entity.Card;
import com.risk.entity.Continent;
import com.risk.entity.Map;
import com.risk.entity.Player;
import com.risk.entity.Territory;
import com.risk.exception.InvalidGameMoveException;
import com.risk.map.util.GameUtil;
import com.risk.map.util.MapUtil;
import com.risk.model.CardModel;
import com.risk.model.GameModel;
import com.risk.model.PlayerModel;
import com.risk.model.PlayerWorldDomination;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * Game play controller to control all the
 * 
 * @author rahul
 * @version 1.0.0
 *
 */
public class GamePlayController implements Initializable, Observer {

	private Map map;

	/**
	 * The @gameModel refrence.
	 */
	private GameModel gameModel;
	
	/**
	* The @cardModel refrence.
	*/
	private CardModel cardModel;
	
	@FXML
	private PieChart dominationChart;

	private PlayerModel playerModel;

	private PlayerWorldDomination worldDomination;

	/**
	 * The @numberOfPlayers count of players.
	 */
	@FXML
	private ChoiceBox<Integer> numberOfPlayers;

	/**
	 * The @attack button.
	 */
	@FXML
	private Button attack;

	/**
	 * The @fortify button.
	 */
	@FXML
	private Button fortify;

	/**
	 * The @endTurn button.
	 */
	@FXML
	private Button endTurn;

	/**
	 * The @reinforcement button.
	 */
	@FXML
	private Button reinforcement;

	/**
	 * The @dataDisplay display container.
	 */
	@FXML
	private VBox dataDisplay;

	/**
	 * The @selectedTerritoryList list of territories drop down.
	 */
	@FXML
	private ListView<Territory> selectedTerritoryList;

	/**
	 * The @adjTerritoryList adjacent territory list.
	 */
	@FXML
	private ListView<Territory> adjTerritoryList;

	/**
	 * The @playerTime time remaining for the player.
	 */
	@FXML
	private Label playerTime;

	/**
	 * The @playerChosen current player playing.
	 */
	@FXML
	private Label playerChosen;

	/**
	 * The @gameConsole output console.
	 */
	@FXML
	private TextArea gameConsole;

	/**
	 * The @placeArmy button.
	 */
	@FXML
	private Button placeArmy;

	/**
	 * The @numberOfPlayersSelected .
	 */
	private int numberOfPlayersSelected;

	/**
	 * The @gamePlayerList list of players in the game.
	 */
	private List<Player> gamePlayerList;

	/**
	 * The @playerPlaying current player playing.
	 */
	private Player playerPlaying;

	/**
	 * The @playerIterator.
	 */
	private Iterator<Player> playerIterator;
	/**
	 * The @stackOfCards.
	 */
	private Stack<Card> cardStack;

	/**
	 * Constructor for GamePlayController
	 * 
	 * @param map
	 *            reference to the loaded map
	 */
	public GamePlayController(Map map) {
		this.map = map;
		this.gameModel = new GameModel();
		this.playerModel = new PlayerModel();
		playerModel.addObserver(this);
		worldDomination = new PlayerWorldDomination();
		worldDomination.addObserver(this);
	}

	/**
	 * Create player object on selection of number of players to be played.
	 */
	public void playerSelectionListner() {
		numberOfPlayers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
				setNumberOfPlayersSelected(numberOfPlayers.getSelectionModel().getSelectedItem());

				gamePlayerList.clear();
				gamePlayerList = playerModel.createPlayer(getNumberOfPlayersSelected(), gamePlayerList, gameConsole);
				MapUtil.appendTextToGameConsole("===Players creation complete===\n", gameConsole);

				numberOfPlayers.setDisable(true);
				playerIterator = gamePlayerList.iterator();

				playerModel.assignArmiesToPlayers(gamePlayerList, gameConsole);
				assignTerritoryToPlayer();
			}
		});
	}

	/*
	 * (non-Javadoc) Game controller initializer, loading map data.
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		gamePlayerList = new ArrayList<>();
		GameUtil.initializeTotalPlayers(numberOfPlayers);
		playerSelectionListner();
		assignCardToTerritory();
		loadMapData();
		MapUtil.disableControl(reinforcement, fortify, attack);

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
	}

	/**
	 * Populate adjacent territory of a given territory.
	 * 
	 * @param territory
	 *            territory to be checked
	 */
	private void populateAdjTerritory(Territory territory) {
		this.adjTerritoryList.getItems().clear();
		for (Territory adjTerr : territory.getAdjacentTerritories()) {
			this.adjTerritoryList.getItems().add(adjTerr);
		}
	}

	/**
	 * Assign card to each territories.
	 */
	private void assignCardToTerritory() {
		MapUtil.appendTextToGameConsole("===Assigning Card to territories===\n", gameConsole);
		cardStack = gameModel.assignCardToTerritory(map, gameConsole);
		MapUtil.appendTextToGameConsole("===Card assignation complete===\n", gameConsole);
	}

	@FXML
	private void noMoreAttack(ActionEvent event) {
		adjTerritoryList.setOnMouseClicked(e -> System.out.print(""));
		if (playerModel.getTerritoryWon() > 0) {
			assignCardToPlayer();
		}
		initializeFortification();
	}

	private void assignCardToPlayer() {
		playerPlaying.getPlayerCardList().add(cardStack.pop());
	}

	/**
	 * Attack Phase of the game play.
	 * 
	 * @param event
	 *            event.
	 */
	private void attack() {
		Territory attackingTerritory = selectedTerritoryList.getSelectionModel().getSelectedItem();
		Territory defendingTerritory = adjTerritoryList.getSelectionModel().getSelectedItem();
		try {
			playerModel.attackPhase(attackingTerritory, defendingTerritory);
		} catch (InvalidGameMoveException ex) {
			MapUtil.infoBox(ex.getMessage(), "Message", "");
			return;
		}
	}

	/**
	 * Fortify phase of the game play.
	 * 
	 * @param event
	 *            event
	 */
	@FXML
	private void fortify(ActionEvent event) {
		Territory selectedTerritory = this.selectedTerritoryList.getSelectionModel().getSelectedItem();
		Territory adjTerritory = this.adjTerritoryList.getSelectionModel().getSelectedItem();

		playerModel.fortificationPhase(selectedTerritory, adjTerritory);
		selectedTerritoryList.refresh();
		adjTerritoryList.refresh();
		loadMapData();

		MapUtil.appendTextToGameConsole("=======Fortification ended=======\n", gameConsole);
	}

	/**
	 * Action on endTurn event, load another player.
	 * 
	 * @param event
	 *            event
	 */
	@FXML
	private void endTurn(ActionEvent event) {
		loadPlayingPlayer();
		initializeReinforcement();
	}

	/**
	 * Place armies on territory in round robin manner.
	 * 
	 * @param event
	 *            event
	 */
	@FXML
	private void placeArmy(ActionEvent event) {
		playerModel.placeArmy(playerPlaying, selectedTerritoryList, gamePlayerList);
	}

	/**
	 * Game reinforcement phase. Assinging armies to the player and player assinging
	 * armies to their territory.
	 * 
	 * @param event
	 *            event
	 */
	@FXML
	private void reinforcement(ActionEvent event) {
		Territory territory = selectedTerritoryList.getSelectionModel().getSelectedItem();
		playerModel.reinforcementPhase(territory, gameConsole);
		selectedTerritoryList.refresh();
		loadMapData();
		playerChosen.setText(playerPlaying.getName() + ":- " + playerPlaying.getArmies() + " armies left.");
		//To avoid NullPointerException
		//if(playerPlaying.getPlayerCardList().size() > 0 ) 
		//{
			this.cardModel = new CardModel(playerPlaying,cardStack);
			cardModel.cardWindow();
		//}
		
	}

	/**
	 * Load map data on the game screen.
	 */
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
		MapUtil.appendTextToGameConsole("===Assigning territories===\n", gameConsole);
		gameModel.assignTerritoryToPlayer(map, gamePlayerList, gameConsole);
		MapUtil.appendTextToGameConsole("===Territories assignation complete===\n", gameConsole);
		loadMapData();
		loadPlayingPlayer();
		populateWorldDominationData();
	}

	/**
	 * Load the information of the current player playing.
	 */
	private void loadPlayingPlayer() {
		if (!playerIterator.hasNext()) {
			playerIterator = gamePlayerList.iterator();
		}
		playerPlaying = playerIterator.next();
		playerModel.setPlayerPlaying(playerPlaying);
		playerModel.setTerritoryWon(0);
		MapUtil.appendTextToGameConsole("============================ \n", gameConsole);
		MapUtil.appendTextToGameConsole(playerPlaying.getName() + "!....started playing.\n", gameConsole);
		selectedTerritoryList.getItems().clear();
		adjTerritoryList.getItems().clear();
		loadMapData();
		for (Territory territory : playerPlaying.getAssignedTerritory()) {
			selectedTerritoryList.getItems().add(territory);
		}
		playerChosen.setText(playerPlaying.getName() + ":- " + playerPlaying.getArmies() + " armies left.\n");
	}

	/**
	 * Calculate reinforcement armies based ont the game rules.
	 */
	private void calculateReinforcementArmies() {
		if (this.playerPlaying != null) {
			playerPlaying = playerModel.calculateReinforcementArmies(map, playerPlaying);
			playerChosen.setText(playerPlaying.getName() + ":- " + playerPlaying.getArmies() + " armies left.");
		} else {
			MapUtil.appendTextToGameConsole("Error!. No player playing.", gameConsole);
		}
	}

	/**
	 * Initialize reinforcement phase of the game.
	 */
	private void initializeReinforcement() {
		loadPlayingPlayer();

		MapUtil.disableControl(placeArmy, fortify, attack);
		MapUtil.enableControl(reinforcement);
		reinforcement.requestFocus();
		MapUtil.appendTextToGameConsole("============================ \n", gameConsole);
		MapUtil.appendTextToGameConsole("======Start Reinforcement! =========== \n", gameConsole);
		MapUtil.appendTextToGameConsole(playerPlaying.getName() + "\n", gameConsole);
//		cardModel.cardWindow();
		calculateReinforcementArmies();
	}

	private void setLaunchAttackEvent() {
		adjTerritoryList.setOnMouseClicked(e -> attack());
	}

	/**
	 * Initialize attack phase of the game.
	 */
	private void initializeAttack() {
		MapUtil.appendTextToGameConsole("============================ \n", gameConsole);
		MapUtil.appendTextToGameConsole("===Attack phase started! === \n", gameConsole);
		if (!gameModel.hasAValidAttackMove(selectedTerritoryList)) {
			MapUtil.appendTextToGameConsole("No valid attack move avialble move to Fortification phase.", gameConsole);
			initializeFortification();
		} else {
			MapUtil.disableControl(reinforcement, placeArmy);
			MapUtil.enableControl(attack);
			attack.requestFocus();
			setLaunchAttackEvent();
		}
	}

	/**
	 * Initialize fortification phase of the game.
	 */
	private void initializeFortification() {
		MapUtil.disableControl(reinforcement, attack, placeArmy);
		MapUtil.enableControl(fortify);
		fortify.requestFocus();
		MapUtil.appendTextToGameConsole("============================ \n", gameConsole);
		MapUtil.appendTextToGameConsole("====Fortification phase started! ====== \n", gameConsole);
	}

	private void populateWorldDominationData() {
		HashMap<Player, Double> playerTerPercent = worldDomination.populateWorldDominationData(map);
		ArrayList<Data> chartData = new ArrayList<>();
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
		for (Entry<Player, Double> entry : playerTerPercent.entrySet()) {
			chartData.add(new PieChart.Data(entry.getKey().getName(), entry.getValue()));
		}
		pieChartData.addAll(chartData);
		dominationChart.setData(pieChartData);
	}

	private void checkIfAnyPlayerLostTheGame() {
		List<Player> playerLost = playerModel.checkIfAnyPlayerLostTheGame(gamePlayerList);
		if (!playerLost.isEmpty()) {
			for (Player player : playerLost) {
				gamePlayerList.remove(player);
				MapUtil.infoBox("Player: " + player.getName() + " lost all his territory and is out of the game",
						"Info", "");
			}
		}
	}

	private void checkIfPlayerWonTheGame() {
		if (gamePlayerList.size() == 1) {
			MapUtil.infoBox("Player: " + gamePlayerList.get(0).getName() + " won the game!", "Info", "");
		}
	}

	private void refreshView() {
		checkIfAnyPlayerLostTheGame();
		selectedTerritoryList.getItems().clear();
		adjTerritoryList.getItems().clear();
		for (Territory territory : playerPlaying.getAssignedTerritory()) {
			selectedTerritoryList.getItems().add(territory);
		}
		checkIfAnyPlayerLostTheGame();
		loadMapData();
		populateWorldDominationData();
		playerChosen.setText(playerPlaying.getName() + ":- " + playerPlaying.getArmies() + " armies left.\n");
		checkIfPlayerWonTheGame();
	}

	/**
	 * Initialize place army view.
	 */
	private void initializePlaceArmy() {
		loadMapData();
		selectedTerritoryList.refresh();
		loadPlayingPlayer();
	}

	/**
	 * check if there is a valid fortification phase.
	 */
	private void isValidFortificationPhase() {
		playerModel.isFortificationPhaseValid(map, playerPlaying);
	}

	/**
	 * Change view if there is no valid fortification.
	 */
	private void noFortificationPhase() {
		MapUtil.appendTextToGameConsole("====Fortification phase started! ====== \n", gameConsole);
		MapUtil.appendTextToGameConsole(playerPlaying.getName() + " has no armies to be fortified.", gameConsole);
		loadPlayingPlayer();
		initializeReinforcement();
	}

	/**
	 * @return int number of layer selected
	 */
	public int getNumberOfPlayersSelected() {
		return numberOfPlayersSelected;
	}

	/**
	 * @param numberOfPlayersSelected
	 *            number of player selected
	 */
	public void setNumberOfPlayersSelected(int numberOfPlayersSelected) {
		this.numberOfPlayersSelected = numberOfPlayersSelected;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable o, Object arg) {

		String view = (String) arg;

		if (view.equals("Attack")) {
			initializeAttack();
		}
		if (view.equals("FirstAttack")) {
			loadPlayingPlayer();
			initializeAttack();
		}
		if (view.equals("Reinforcement")) {
			initializeReinforcement();
		}
		if (view.equals("Fortification")) {
			initializeFortification();
		}
		if (view.equals("placeArmy")) {
			initializePlaceArmy();
		}
		if (view.equals("WorldDomination")) {
			populateWorldDominationData();
		}
		if (view.equals("checkIfFortificationPhaseValid")) {
			isValidFortificationPhase();
		}
		if (view.equals("noFortificationMove")) {
			noFortificationPhase();
		}
		if (view.equals("rollDiceComplete")) {
			refreshView();
		}
	}
}