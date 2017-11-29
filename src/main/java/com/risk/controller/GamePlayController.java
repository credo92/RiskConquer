package com.risk.controller;

import java.io.Externalizable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
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
import com.risk.entity.GameState;
import com.risk.entity.Map;
import com.risk.entity.Player;
import com.risk.entity.Territory;
import com.risk.exception.InvalidGameMoveException;
import com.risk.exception.InvalidMapException;
import com.risk.map.util.GameUtil;
import com.risk.map.util.MapUtil;
import com.risk.model.CardModel;
import com.risk.model.GameModel;
import com.risk.model.PlayerGamePhase;
import com.risk.model.PlayerWorldDomination;
import com.risk.strategy.CheaterStrategy;
import com.risk.strategy.HumanStrategy;
import com.risk.validate.MapValidator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Game play controller to control all the
 * 
 * @author rahul
 * @version 1.0.0
 *
 */
public class GamePlayController implements Initializable, Observer, Externalizable {

	/**
	 * The @map reference.
	 */
	private Map map;

	/**
	 * The @gameModel reference.
	 */
	private GameModel gameModel;

	/**
	 * The @attackCount .
	 */
	private int attackCount = 5;

	/**
	 * The @gameModel reference.
	 */
	private CardModel cardModel;

	/**
	 * The @dominationChart.
	 */
	@FXML
	private PieChart dominationChart;

	/**
	 * The @playerModel.
	 */
	private PlayerGamePhase playerGamePhase;

	/**
	 * Get Map
	 * 
	 * @return map
	 */
	public Map getMap() {
		return map;
	}

	/**
	 * Set Map
	 * 
	 * @param map
	 *            the map to set
	 */
	public void setMap(Map map) {
		this.map = map;
	}

	/**
	 * The @worldDomination.
	 */
	private PlayerWorldDomination worldDomination;

	/**
	 * The @numberOfPlayers count of players.
	 */
	@FXML
	private ChoiceBox<Integer> numberOfPlayers;

	/**
	 * The @playerSelectionController playerSelectionController
	 */
	private PlayerSelectionController playerSelectionController;

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
	 * The @cards button.
	 */
	@FXML
	private Button cards;

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
	 * The @playerChosen current player playing.
	 */
	@FXML
	private Label playerChosen;

	/**
	 * The @gamePhase display current phase .
	 */
	@FXML
	private Label gamePhase;

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
	 * The @cardStack.
	 */
	private Stack<Card> cardStack;

	/**
	 * The @numberOfCardSetExchanged.
	 */
	private int numberOfCardSetExchanged;

	/**
	 * The @saveGame button.
	 */
	@FXML
	private Button saveGame;

	public GamePlayController() {
		// this.gameModel = gameModel;
		// this.playerGamePhase = playerGamePhase;
		// this.cardModel = cardModel;
		//numberOfPlayers.setValue(numberOf);

		}

	/**
	 * Constructor for GamePlayController
	 * 
	 * @param map
	 *            reference to the loaded map
	 */
	public GamePlayController(Map map) {
		this.map = map;
		this.gameModel = new GameModel();
		this.playerGamePhase = new PlayerGamePhase();
		this.cardModel = new CardModel();
		playerGamePhase.addObserver(this);
		cardModel.addObserver(this);
		worldDomination = new PlayerWorldDomination();
		worldDomination.addObserver(this);
		this.setNumberOfCardSetExchanged(0);
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
				gamePlayerList = playerGamePhase.createPlayer(getNumberOfPlayersSelected(), gamePlayerList,
						gameConsole);
				numberOfPlayers.setDisable(true);
				playerIterator = gamePlayerList.iterator();
				loadPlayerSelectionWindow();
			}
		});
	}

	/**
	 * Load Startup Phase and assign territories to Players
	 * 
	 */
	public void loadStartUpPhase() {
		MapUtil.appendTextToGameConsole("===Setup Phase started!===\n", gameConsole);
		MapUtil.appendTextToGameConsole("===Players creation complete===\n", gameConsole);
		playerGamePhase.assignArmiesToPlayers(gamePlayerList, gameConsole);
		assignTerritoryToPlayer();
		MapUtil.appendTextToGameConsole("===Terriotry assignation complete===\n", gameConsole);
		loadMapData();
		loadPlayingPlayer();
		populateWorldDominationData();
		MapUtil.enableControl(cards);
		if (!(playerPlaying.getStrategy() instanceof HumanStrategy)) {
			placeArmy(null);
		}
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
		loadGameCard();
		loadMapData();
		gamePhase.setText("Phase: Start Up!");
		MapUtil.disableControl(reinforcement, fortify, attack, cards);

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
	 * Player Selection Window
	 */
	public void loadPlayerSelectionWindow() {
		final Stage newMapStage = new Stage();
		newMapStage.setTitle("Player Selection Window");
		PlayerSelectionController playerSelectionController = new PlayerSelectionController(gamePlayerList);
		playerSelectionController.addObserver(this);
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("PlayerSelection.fxml"));
		loader.setController(playerSelectionController);
		Parent root = null;
		try {
			root = (Parent) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		newMapStage.setScene(scene);
		newMapStage.show();
		newMapStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				gamePlayerList = new ArrayList<>();
				numberOfPlayers.setDisable(false);
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
		if (territory != null) {
			for (Territory adjTerr : territory.getAdjacentTerritories()) {
				this.adjTerritoryList.getItems().add(adjTerr);
			}
		}
	}

	/**
	 * Assign card to each territories.
	 */
	private void loadGameCard() {
		cardStack = gameModel.assignCardToTerritory(map, gameConsole);
		MapUtil.appendTextToGameConsole("======" + cardStack.size() + " Game card loaded===\n", gameConsole);
	}

	/**
	 * No more attack, move to fortification.
	 * 
	 * @param event
	 *            action event
	 */
	@FXML
	private void noMoreAttack(ActionEvent event) {
		adjTerritoryList.setOnMouseClicked(e -> System.out.print(""));
		if (playerGamePhase.getTerritoryWon() > 0) {
			assignCardToPlayer();
			MapUtil.appendTextToGameConsole("===Attack phase ended!===\n", gameConsole);
			isValidFortificationPhase();
		}
	}

	/**
	 * Assign card to player
	 */
	private void assignCardToPlayer() {
		Card cardToBeAdded = cardStack.pop();
		playerPlaying.getPlayerCardList().add(cardToBeAdded);
		playerGamePhase.setTerritoryWon(0);
		MapUtil.appendTextToGameConsole(playerPlaying.getName() + " has been assigned a card with type "
				+ cardToBeAdded.getCardType().toString() + " and territory " + cardToBeAdded.getTerritory().getName()
				+ "\n", gameConsole);
	}

	/**
	 * Attack on the defending territory.
	 */
	private void attack() {
		try {
			playerGamePhase.attackPhase(selectedTerritoryList, adjTerritoryList, gameConsole);
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
		startFortification();
	}

	/**
	 * Action on endTurn event, load another player.
	 * 
	 * @param event
	 *            event
	 */
	@FXML
	private void endTurn(ActionEvent event) {
		adjTerritoryList.setOnMouseClicked(e -> System.out.print(""));
		MapUtil.appendTextToGameConsole(playerPlaying.getName() + " ended his turn.\n", gameConsole);
		if (playerGamePhase.getTerritoryWon() > 0) {
			assignCardToPlayer();
		}
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
		playerGamePhase.placeArmy(playerPlaying, selectedTerritoryList, gamePlayerList, gameConsole);
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
		if (playerPlaying.getPlayerCardList().size() >= 5) {
			MapUtil.infoBox("You have five or more Risk Card, please exchange these cards for army.", "Info", "");
			return;
		}
		startReinforcement(territory);
	}

	/**
	 * Start reinforcement.
	 * 
	 * @param territory
	 *            territory
	 */
	private void startReinforcement(Territory territory) {
		playerGamePhase.reinforcementPhase(selectedTerritoryList.getItems(), territory, gameConsole);
		selectedTerritoryList.refresh();
		loadMapData();
		playerChosen.setText(playerPlaying.getName() + "(" + playerPlaying.getType() + "):- "
				+ playerPlaying.getArmies() + " armies left.");
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
	}

	/**
	 * Load the information of the current player playing.
	 */
	private void loadPlayingPlayer() {
		if (!playerIterator.hasNext()) {
			playerIterator = gamePlayerList.iterator();
		}
		Player newPLayer = playerIterator.next();
		if (newPLayer.equals(playerPlaying)) {
			if (playerIterator.hasNext()) {
				newPLayer = playerIterator.next();
			}
		}
		playerPlaying = newPLayer;
		playerGamePhase.setPlayerPlaying(playerPlaying);
		playerGamePhase.setTerritoryWon(0);
		MapUtil.appendTextToGameConsole("============================ \n", gameConsole);
		MapUtil.appendTextToGameConsole(playerPlaying.getName() + "!....started playing.\n", gameConsole);
		selectedTerritoryList.getItems().clear();
		adjTerritoryList.getItems().clear();
		loadMapData();
		for (Territory territory : playerPlaying.getAssignedTerritory()) {
			selectedTerritoryList.getItems().add(territory);
		}
		playerChosen.setText(playerPlaying.getName() + "(" + playerPlaying.getType() + "):- "
				+ playerPlaying.getArmies() + " armies left.\n");
	}

	/**
	 * Calculate reinforcement armies based on the game rules.
	 */
	public void calculateReinforcementArmies() {
		if (this.playerPlaying != null) {
			playerPlaying = playerGamePhase.calculateReinforcementArmies(map, playerPlaying);
			playerChosen.setText(playerPlaying.getName() + "(" + playerPlaying.getType() + "):- "
					+ playerPlaying.getArmies() + " armies left.");
		} else {
			MapUtil.appendTextToGameConsole("Error!. No player playing.", gameConsole);
		}
	}

	/**
	 * Initialize Card Window for current Player playing.
	 * 
	 * @param event
	 *            event
	 */
	@FXML
	public void initCardWindow(ActionEvent event) {
		cardModel.openCardWindow(playerPlaying, cardModel);
	}

	/**
	 * Initialize reinforcement phase of the game.
	 */
	private void initializeReinforcement() {
		loadPlayingPlayer();

		gamePhase.setText("Phase: Reinforcement");
		MapUtil.disableControl(placeArmy, fortify, attack);
		MapUtil.appendTextToGameConsole("============================ \n", gameConsole);
		MapUtil.appendTextToGameConsole("===Reinforcement phase started! ===\n", gameConsole);
		calculateReinforcementArmies();
		if (!(playerPlaying.getStrategy() instanceof HumanStrategy)) {
			cardModel.autoCardWindow(playerPlaying, cardModel);
			startReinforcement(null);
		} else {
			MapUtil.enableControl(reinforcement);
			reinforcement.requestFocus();
			cardModel.openCardWindow(playerPlaying, cardModel);
		}
	}

	/**
	 * Set Launch Attack Event when a adjacent territory is selected from
	 * adjTerritory List.
	 */
	private void setLaunchAttackEvent() {
		adjTerritoryList.setOnMouseClicked(e -> attack());
	}

	/**
	 * Initialize attack phase of the game.
	 */
	private void initializeAttack() {
		MapUtil.appendTextToGameConsole("============================ \n", gameConsole);
		MapUtil.appendTextToGameConsole("===Attack phase started! === \n", gameConsole);
		if (playerGamePhase.playerHasAValidAttackMove(selectedTerritoryList, gameConsole)) {
			gamePhase.setText("Phase: Attack");
			MapUtil.disableControl(reinforcement, placeArmy);
			MapUtil.enableControl(attack);
			attack.requestFocus();
			if (playerPlaying.getStrategy() instanceof HumanStrategy) {
				setLaunchAttackEvent();
			} else {
				attack();
			}
		}
	}

	/**
	 * Initialize fortification phase of the game.
	 */
	private void initializeFortification() {
		MapUtil.disableControl(reinforcement, attack, placeArmy);
		MapUtil.enableControl(fortify);
		gamePhase.setText("Phase: Fortification");
		fortify.requestFocus();
		MapUtil.appendTextToGameConsole("============================ \n", gameConsole);
		MapUtil.appendTextToGameConsole("====Fortification phase started! ====== \n", gameConsole);
		if (!(playerPlaying.getStrategy() instanceof HumanStrategy)) {
			startFortification();
		}
	}

	/**
	 * Start of Fortification
	 */
	private void startFortification() {
		playerGamePhase.fortificationPhase(selectedTerritoryList, adjTerritoryList, gameConsole);
		selectedTerritoryList.refresh();
		adjTerritoryList.refresh();
		loadMapData();
	}

	/**
	 * Populate World Domination Data.
	 */
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

	/**
	 * Check If Any Player Lost the game.
	 */
	private void checkIfAnyPlayerLostTheGame() {
		System.out.println("Checking if player lost the game");
		Player playerLost = playerGamePhase.checkIfAnyPlayerLostTheGame(gamePlayerList);
		if (playerLost != null) {
			gamePlayerList.remove(playerLost);
			playerIterator = gamePlayerList.iterator();
			MapUtil.infoBox("Player: " + playerLost.getName() + " lost all his territory and is out of the game",
					"Info", "");
			MapUtil.appendTextToGameConsole(playerLost.getName() + " lost all territories and lost the game.\n",
					gameConsole);
			MapUtil.appendTextToGameConsole("==============================================================\n",
					gameConsole);
		}
	}

	/**
	 * Disable game panel after player won.
	 */
	private void disableGamePanel() {
		GameUtil.disableControl(selectedTerritoryList, adjTerritoryList, reinforcement, attack, fortify, cards,
				endTurn);
		gamePhase.setText("GAME OVER");
		playerChosen.setText(playerPlaying.getName().toUpperCase() + " WON THE GAME");
		MapUtil.appendTextToGameConsole("=====================================================\n", gameConsole);
		MapUtil.appendTextToGameConsole(playerPlaying.getName().toUpperCase() + " WON THE GAME\n", gameConsole);
		MapUtil.appendTextToGameConsole("=====================================================\n", gameConsole);

	}

	/**
	 * Check If Any Player Won the game.
	 * 
	 * @param view
	 *            view
	 * @return playerWon playerWon
	 */
	private boolean checkIfPlayerWonTheGame(String view) {
		boolean playerWon = false;
		if (gamePlayerList.size() == 1) {
			MapUtil.infoBox("Player: " + gamePlayerList.get(0).getName() + " won the game!", "Info", "");
			playerWon = true;
			disableGamePanel();
		}

		return playerWon;
	}

	/**
	 * Refresh View
	 */
	private void refreshView() {
		System.out.println("Inside referesh view");
		checkIfAnyPlayerLostTheGame();
		selectedTerritoryList.getItems().clear();
		adjTerritoryList.getItems().clear();
		for (Territory territory : playerPlaying.getAssignedTerritory()) {
			selectedTerritoryList.getItems().add(territory);
		}
		loadMapData();
		populateWorldDominationData();
		playerChosen.setText(playerPlaying.getName() + "(" + playerPlaying.getType() + "):- "
				+ playerPlaying.getArmies() + " armies left.\n");
		if (!checkIfPlayerWonTheGame("reresh")) {
			if (playerGamePhase.playerHasAValidAttackMove(selectedTerritoryList, gameConsole)) {
				if (!(playerPlaying.getStrategy() instanceof HumanStrategy)) {
					if (attackCount > 0) {
						attackCount--;
						attack();
					} else {
						attackCount = 5;
						noMoreAttack(null);
					}
				}
			}
		}
	}

	/**
	 * Skip Attack
	 */
	private void skipAttack() {
		checkIfAnyPlayerLostTheGame();
		selectedTerritoryList.getItems().clear();
		adjTerritoryList.getItems().clear();
		for (Territory territory : playerPlaying.getAssignedTerritory()) {
			selectedTerritoryList.getItems().add(territory);
		}
		loadMapData();
		populateWorldDominationData();
		playerChosen.setText(playerPlaying.getName() + "(" + playerPlaying.getType() + "):- "
				+ playerPlaying.getArmies() + " armies left.\n");
		if (!checkIfPlayerWonTheGame("skipattack")) {
			System.out.println("Player did not won the game.");
			if (playerPlaying.getStrategy() instanceof CheaterStrategy) {
				if (playerGamePhase.playerHasAValidAttackMove(selectedTerritoryList, gameConsole)) {
					attack();
				} else {
					// Cheater always wins a territory so he will be assigned a single card.
					playerGamePhase.setTerritoryWon(1);
					noMoreAttack(null);
				}
			} else {
				noMoreAttack(null);
			}
		}
	}

	/**
	 * Initialize place army view.
	 */
	private void initializePlaceArmy() {
		loadMapData();
		selectedTerritoryList.refresh();
		loadPlayingPlayer();
		if (!(playerPlaying.getStrategy() instanceof HumanStrategy)) {
			placeArmy(null);
		}
	}

	/**
	 * check if there is a valid fortification phase.
	 */
	private void isValidFortificationPhase() {
		playerGamePhase.isFortificationPhaseValid(map, playerPlaying);
	}

	/**
	 * Change view if there is no valid fortification.
	 */
	private void noFortificationPhase() {
		MapUtil.appendTextToGameConsole("====Fortification phase started! ====== \n", gameConsole);
		MapUtil.appendTextToGameConsole(playerPlaying.getName() + " has no armies to be fortified.\n", gameConsole);
		MapUtil.appendTextToGameConsole("====Fortification phase ended! ====== \n", gameConsole);
		initializeReinforcement();
	}

	/**
	 * Get number of players selected
	 * 
	 * @return numberOfPlayersSelected
	 */
	public int getNumberOfPlayersSelected() {
		return numberOfPlayersSelected;
	}

	/**
	 * Set the number of players selected
	 * 
	 * @param numberOfPlayersSelected
	 *            number of player selected
	 */
	public void setNumberOfPlayersSelected(int numberOfPlayersSelected) {
		this.numberOfPlayersSelected = numberOfPlayersSelected;
	}

	/**
	 * Trade Cards for Armies
	 * 
	 * @param cm
	 *            cardModel cm
	 */
	public void tradeCardsForArmy(CardModel cm) {
		List<Card> tradedCards = cm.getCardsToBeExchange();
		setNumberOfCardSetExchanged(getNumberOfCardSetExchanged() + 1);
		playerGamePhase.tradeCardsForArmy(tradedCards, getNumberOfCardSetExchanged(), gameConsole);
		playerPlaying.getPlayerCardList().removeAll(tradedCards);
		cardStack.addAll(tradedCards);
		Collections.shuffle(cardStack);
		selectedTerritoryList.refresh();
		adjTerritoryList.refresh();
		loadMapData();
		populateWorldDominationData();
		playerChosen.setText(playerPlaying.getName() + "(" + playerPlaying.getType() + "):- "
				+ playerPlaying.getArmies() + " armies left.\n");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable o, Object arg) {

		String view = (String) arg;
		MapUtil.appendTextToGameConsole(view + "++++++++++++++++++++++++++++++++++++++++++++++\n", gameConsole);
		System.out.println("+++++++++++++++++++++ " + view + " +++++++++++++++++++++++++++");
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
			MapUtil.appendTextToGameConsole(
					"Roll Dice Control observer!++++++++++++++++++++++++++++++++++++++++++++++\n", gameConsole);
			System.out.println("Roll Dice Control observer!++++++++++++++++++++++++++++++++++++++++++++++");
			refreshView();
		}
		if (view.equals("cardsTrade")) {
			CardModel cm = (CardModel) o;
			tradeCardsForArmy(cm);
		}
		if (view.equals("playersCreated")) {
			loadStartUpPhase();
		}

		if (view.equals("SkipAttack")) {
			skipAttack();
		}
	}

	/**
	 * Get Number of Card Sets Exhanged
	 * 
	 * @return numberOfCardSetExchanged
	 */
	public int getNumberOfCardSetExchanged() {
		return numberOfCardSetExchanged;
	}

	/**
	 * Set Number of Card Sets Exhanged
	 * 
	 * @param numberOfCardSetExchanged
	 *            numberOfCardSetExchanged
	 */
	public void setNumberOfCardSetExchanged(int numberOfCardSetExchanged) {
		this.numberOfCardSetExchanged = numberOfCardSetExchanged;
	}

	/**
	 * Save Game
	 * 
	 * @param event
	 *            event <<<<<<< HEAD
	 * @throws IOException
	 * @throws InvalidJsonException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 * @throws NullPointerException
	 *     
	 */
	@FXML
	private void saveGame(ActionEvent event) throws IOException {
	//	GameState gameState = new GameState();
		FileChooser fileChooser = new FileChooser();

		// Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Serialization  (*.ser)", "*.ser");
		fileChooser.getExtensionFilters().add(extFilter);
		fileChooser.setInitialFileName("player");

		// Show save file dialog
		File file = fileChooser.showSaveDialog(null);

		if (file != null) {
			//SaveFile(this, file);
			SaveFile(this, file);
		}
	}

	private void SaveFile(GamePlayController gamePlayController, File file) throws IOException {
		try {
			FileOutputStream fileOut = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(gamePlayController);
			out.close();
			fileOut.close();
			System.out.printf("Serialized data is saved in player.ser");
		} catch (IOException i) {
			i.printStackTrace();

		}
	}

	/**
	 * Write External
	 * 
	 * @param out
	 *            out
	 * @throws IOException
	 */
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		// TODO Auto-generated method stub
		out.writeObject(map);
		out.writeObject(gameModel);
		out.writeObject(playerPlaying);
		out.writeObject(playerGamePhase);
		out.writeObject(cardStack);
		// out.writeObject(gameConsole);
	}

	/**
	 * Read External
	 * 
	 * @param in
	 *            in
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		map = (Map) in.readObject();
		gameModel = (GameModel) in.readObject();
		playerPlaying = (Player) in.readObject();
		playerGamePhase = (PlayerGamePhase) in.readObject();
		
		// gameConsole = (TextArea) in.readObject();
	}

}