package com.risk.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.risk.constant.CardType;
import com.risk.constant.MapConstant;
import com.risk.entity.Card;
import com.risk.entity.Continent;
import com.risk.entity.Map;
import com.risk.entity.Player;
import com.risk.entity.Territory;
import com.risk.exception.InvalidGameMoveException;
import com.risk.strategy.BenevolentStrategy;
import com.risk.strategy.CheaterStrategy;
import com.risk.strategy.HumanStrategy;
import com.risk.strategy.RandomStrategy;

import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

/**
* Player Model Test class.
* @author Garvpreet Singh
* @version 1.0.0
*/
public class PlayerGamePhaseTest {

	/**
	 * The @gameModel.
	 */
	static GameModel gameModel;
	
	/**
	 * The @playerModel.
	 */
	static PlayerGamePhase playerModel;
	
	/**
	 * The @continent
	 */
	static Continent continent;
	
	/**
	 * The @territory1
	 */
	static Territory territory1;
	
	/**
	 * The @territory2
	 */
	static Territory territory2;
	
	/**
	 * The @map
	 */
	static Map map;
	
	/**
	 * The @player
	 */
	static Player player;
	
	/**
	 * The @textArea
	 */
	@FXML
	static TextArea textArea;

	/**
	 * The @territoryListView
	 */
	@FXML
	static ListView<Territory> territoryListView;
	
	/**
	 * The @continentName
	 */
	String continentName = "Asia";
	
	/**
	 * The @controlValue
	 */
	String controlValue = "7";	
	
	/**
	 * The @territoryName1
	 */
	String territoryName1 = "India";
	
	/**
	 * The @territoryName2
	 */
	String territoryName2 = "China";	
	
	/**
	 * The @players list
	 */
	static List<Player> players;
	
	/**
	 * The @fxPanel
	 */
	static JFXPanel fxPanel;

	/**
	 * This method is invoked at the start of the test class.
	 */
	@BeforeClass
	public static void beforeClass() {
		fxPanel = new JFXPanel();
		gameModel = new GameModel();
		playerModel = new PlayerGamePhase();
		textArea = new TextArea();
	}

	/**
	 * This method is invoked at the start of all the test methods.
	 */
	@Before
	public void beforeTest() {
		territoryListView = new ListView<>();
		map = new Map();
		player = new Player(1);
		continent = new Continent();
		territory1 = new Territory();
		territory2 = new Territory();

		continent.setName(continentName);
		continent.setValue(controlValue);

		territory1.setName(territoryName1);
		territory1.setBelongToContinent(continent);
		continent.getTerritories().add(territory1);

		territory2.setName(territoryName2);
		territory2.setBelongToContinent(continent);
		continent.getTerritories().add(territory2);

		territory1.getAdjacentTerritories().add(territory2);
		territory2.getAdjacentTerritories().add(territory1);

		map.getContinents().add(continent);

		territoryListView.setEditable(true);
		territoryListView.getItems().add(territory1);
		territoryListView.getItems().add(territory2);

		playerModel.setPlayerPlaying(player);
	}

	/**
	 * This method tests number of armies for 35 initial armies and 1 territory
	 * during each reinforcement phase.
	 */
	@Test
	public void calculateReinforcementArmiesCaseOne() {
		player.setArmies(35);
		player.getAssignedTerritory().add(territory1);
		territory1.setPlayer(player);
		player.getAssignedTerritory().add(territory2);
		territory2.setPlayer(player);
		Player returnedPlayer = playerModel.calculateReinforcementArmies(map, player);
		Assert.assertEquals(returnedPlayer.getArmies(), 45);
	}

	/**
	 * This method tests number of armies for 2 continents during each reinforcement
	 * phase.
	 */
	@Test
	public void calculateReinforcementArmiesCaseTwo() {
		Continent newContinent = new Continent();
		Territory terr = new Territory();

		newContinent.setName("Africa");
		newContinent.setValue("5");

		terr.setName("New Territory");
		terr.setBelongToContinent(newContinent);

		terr.getAdjacentTerritories().add(territory1);
		territory1.getAdjacentTerritories().add(terr);

		newContinent.getTerritories().add(terr);

		map.getContinents().add(newContinent);

		player.setArmies(35);
		player.getAssignedTerritory().add(territory1);
		territory1.setPlayer(player);
		player.getAssignedTerritory().add(territory2);
		territory2.setPlayer(player);

		player.getAssignedTerritory().add(terr);
		terr.setPlayer(player);

		Player returnedPlayer = playerModel.calculateReinforcementArmies(map, player);
		Assert.assertEquals(returnedPlayer.getArmies(), 50);
	}

	/**
	 * This method tests number of continents owned by a player.
	 */
	@Test
	public void getContinentsOwnedByPlayer() {
		List<Continent> returnedContinents = new ArrayList<>();
		territory1.setPlayer(player);
		territory2.setPlayer(player);
		returnedContinents = playerModel.getContinentsOwnedByPlayer(map, player);
		Assert.assertEquals("Asia", returnedContinents.get(0).getName());
		Assert.assertEquals(1, returnedContinents.size());
	}

	/**
	 * This method tests if a player has zero armies.
	 */
	@Test
	public void checkIfPlayersArmiesExhausted() {
		players = new ArrayList<>();
		players.add(new Player(0));
		players.get(0).setArmies(0);
		Assert.assertTrue(playerModel.checkIfPlayersArmiesExhausted(players));
	}

	/**
	 * This method tests if a player does not have zero armies.
	 */
	@Test
	public void checkIfPlayersArmiesExhaustedFalseCase() {
		players = new ArrayList<>();
		players.add(new Player(0));
		players.get(0).setArmies(1);
		Assert.assertFalse(playerModel.checkIfPlayersArmiesExhausted(players));
	}

	/**
	 * This method tests if a player have more than 1 army to attack.
	 */
	@Test
	public void playerHasAValidAttackMove() {
		player.setStrategy(new HumanStrategy());
		territory1.setArmies(5);
		territory2.setArmies(3);
		boolean actualResult = playerModel.playerHasAValidAttackMove(territoryListView, textArea);
		Assert.assertTrue(actualResult);
	}

	/**
	 * This method tests if a player does not have more than 1 army to attack.
	 */
	@Test
	public void playerHasAValidAttackMoveFalseCase() {
		player.setStrategy(new CheaterStrategy());
		territory1.setArmies(1);
		territory2.setArmies(1);
		boolean actualResult = playerModel.playerHasAValidAttackMove(territoryListView, textArea);
		Assert.assertFalse(actualResult);
	}

	/**
	 * This method tests if a player has lost the territory.
	 */
	@Test
	public void checkIfAnyPlayerLostTheGame() {
		players = new ArrayList<>();
		players.add(new Player(0));
		players.get(0).setAssignedTerritory(new ArrayList<>());
		Player playerLost = playerModel.checkIfAnyPlayerLostTheGame(players);
		Assert.assertEquals(0, playerLost.getAssignedTerritory().size());
	}

	/**
	 * This method is used to test creation of players in the system.
	 */
	@Test
	public void createPlayer() {
		List<Player> playerTest = new ArrayList<>();
		players = new ArrayList<>();
		players.add(new Player(0));
		players.add(new Player(1));
		players.add(new Player(2));
		playerModel.createPlayer(players.size(), playerTest, textArea);
		Assert.assertEquals(3, playerTest.size());
	}

	/**
	 * This method tests whether Fortification phase is valid by setting territories
	 * armies to 2
	 */
	@Test
	public void isFortificationPhaseValidTrue() {
		player.setStrategy(new RandomStrategy());
		territory1.setPlayer(player);
		territory1.setArmies(2);
		territory2.setPlayer(player);
		boolean isFortificationPhaseValid = playerModel.isFortificationPhaseValid(map, player);
		Assert.assertEquals(true, isFortificationPhaseValid);
	}

	/**
	 * This method tests whether Fortification phase is valid by setting territories
	 * armies to 0
	 */
	@Test
	public void isFortificationPhaseValidFalse() {
		player.setStrategy(new BenevolentStrategy());
		territory1.setPlayer(player);
		territory1.setArmies(0);
		territory2.setPlayer(player);
		boolean isFortificationPhaseValid = playerModel.isFortificationPhaseValid(map, player);
		Assert.assertEquals(false, isFortificationPhaseValid);
	}

	/**
	 * This method checks whether attack move is valid or not by creating a new
	 * player
	 * 
	 * @throws InvalidGameMoveException
	 *             invalid game exception
	 */
	@Test
	public void isAValidAttackMoveTrue() throws InvalidGameMoveException {
		Player player2 = new Player(2);
		territory1.setPlayer(player);
		territory2.setPlayer(player2);
		territory1.setArmies(3);
		//Assert.assertEquals(true, playerModel.isAValidAttackMove(territory1, territory2));
	}

	/**
	 * This method whether same number of armies have been traded in return of valid
	 * combination of cards or not.
	 */
	@Test
	public void tradeCardsForArmy() {
		List<Card> listOfCards = new ArrayList<>();
		listOfCards.add(new Card(CardType.ARTILLERY));
		listOfCards.add(new Card(CardType.CAVALRY));
		listOfCards.add(new Card(CardType.INFANTRY));
		Player returnedPlayer = playerModel.tradeCardsForArmy(listOfCards, 1, textArea);
		Assert.assertEquals(5, returnedPlayer.getArmies());
	}

	/**
	 * This method tests assign of armies to a player.
	 */
	@Test
	public void assignArmiesToPlayers() {
		players = new ArrayList<>();
		players.add(new Player(1));
		players.add(new Player(2));
		players.add(new Player(3));
		boolean resultPlayerThree = playerModel.assignArmiesToPlayers(players, textArea);
		Assert.assertTrue(resultPlayerThree);
		Assert.assertEquals(MapConstant.ARMIES_THREE_PLAYER, (Integer) players.get(0).getArmies());

		players = new ArrayList<>();
		players.add(new Player(1));
		players.add(new Player(2));
		players.add(new Player(3));
		players.add(new Player(4));
		boolean resultPlayerFour = playerModel.assignArmiesToPlayers(players, textArea);
		Assert.assertTrue(resultPlayerFour);
		Assert.assertEquals(MapConstant.ARMIES_FOUR_PLAYER, (Integer) players.get(0).getArmies());

		players = new ArrayList<>();
		players.add(new Player(1));
		players.add(new Player(2));
		players.add(new Player(3));
		players.add(new Player(4));
		players.add(new Player(5));
		boolean resultPlayerFive = playerModel.assignArmiesToPlayers(players, textArea);
		Assert.assertTrue(resultPlayerFive);
		Assert.assertEquals(MapConstant.ARMIES_FIVE_PLAYER, (Integer) players.get(0).getArmies());

		players = new ArrayList<>();
		players.add(new Player(1));
		players.add(new Player(2));
		players.add(new Player(3));
		players.add(new Player(4));
		players.add(new Player(5));
		players.add(new Player(6));
		boolean resultPlayerSix = playerModel.assignArmiesToPlayers(players, textArea);
		Assert.assertTrue(resultPlayerSix);
		Assert.assertEquals(MapConstant.ARMIES_SIX_PLAYER, (Integer) players.get(0).getArmies());
	}

}
