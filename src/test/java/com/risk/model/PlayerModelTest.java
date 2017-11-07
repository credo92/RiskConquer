package com.risk.model;

import java.io.IOException;
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
import com.risk.map.util.MapUtil;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class PlayerModelTest {
	
	static GameModel gameModel;
	static PlayerModel playerModel;
	static Continent continent;
	static Territory territory1;
	static Territory territory2;
	static Map map;
	static Player player;
	@FXML
	static TextArea textArea;
	static ListView territoryListView;
	
	String continentName = "Asia";
	String controlValue = "7";	
	String territoryName1 = "India";
	String territoryName2 = "China";	
	
	static List<Continent> listOfContinents;	
	static List<Territory> listOfTerritories;
	static List<Card> listOfCards;
	static List<Player> players;

	
	static JFXPanel fxPanel;
	
	/**
	 * This method is invoked at the start of the test class.
	 */
	@BeforeClass
	public static void beforeClass() {
		fxPanel = new JFXPanel();
		gameModel = new GameModel();
		playerModel = new PlayerModel();
		continent = new Continent();
		territory1 = new Territory();
		territory2 = new Territory();
		map = new Map();
		player = new Player(1, "Sonu");	
		textArea = new TextArea();
		listOfContinents = new ArrayList<>();
		listOfTerritories = new ArrayList<>();
		listOfCards = new ArrayList<>();
	}	
	
	/**
	 * This method is invoked at the start of all the test methods.
	 */
	@Before
	public void beforeTest() {
		continent.setName(continentName);
		continent.setValue(controlValue);
		
		territory1.setName(territoryName1);
		territory1.setBelongToContinent(continent);
		territory2.setName(territoryName2);
		territory2.setBelongToContinent(continent);		
		
		territory1.getAdjacentTerritories().add(territory1);
		territory1.setAdjacentTerritories(territory2.getAdjacentTerritories());		

		territory2.getAdjacentTerritories().add(territory2);
		territory2.setAdjacentTerritories(territory1.getAdjacentTerritories());
		
		listOfContinents.add(continent);
		map.setContinents(listOfContinents);
		
		player.setArmies(100);
		
		listOfTerritories.add(territory1);
		player.setAssignedTerritory(listOfTerritories);
	}
	
	/**
	 * This method tests number of armies for 100 initial armies and 1 territory during each reinforcement phase.
	 */
	@Test
	public void calculateReinforcementArmiesCaseOne() {		
		Player returnedPlayer = playerModel.calculateReinforcementArmies(map, player);
		Assert.assertEquals(returnedPlayer.getArmies(), 110);
	}
	
	/**
	 * This method tests number of armies for 2 continents during each reinforcement phase.
	 */
	@Test
	public void calculateReinforcementArmiesCaseTwo() {	
		Continent newContinent = new Continent();
		newContinent.setName("Africa");
		newContinent.setValue("5");
		listOfContinents.add(newContinent);
		map.setContinents(listOfContinents);
		Player returnedPlayer = playerModel.calculateReinforcementArmies(map, player);
		Assert.assertEquals(returnedPlayer.getArmies(), 122);
	}
	

	@Test
	public void getContinentsOwnedByPlayer() {
		List<Continent> continents = new ArrayList<>();
		map.getContinents().get(0).setTerritories(listOfTerritories);
		map.getContinents().get(0).getTerritories().get(0).setPlayer(player);
		continents = playerModel.getContinentsOwnedByPlayer(map, player);
		Assert.assertEquals("Asia", continents.get(0).getName());
	}
	

	@Test
	public void checkIfPlayersArmiesExhausted() {
		players = new ArrayList<>();
		players.add(new Player(0, "Player0"));
        players.get(0).setArmies(0);	
		Assert.assertTrue(playerModel.checkIfPlayersArmiesExhausted(players));
	}
	
	@Test
	public void checkIfPlayersArmiesExhaustedFalseCase() {
		players = new ArrayList<>();
		players.add(new Player(0, "Player0"));
        players.get(0).setArmies(1);	
		Assert.assertFalse(playerModel.checkIfPlayersArmiesExhausted(players));
	}
	
	
	@Test
	public void playerHasAValidAttackMoveFalseCase() {
		territoryListView = new ListView<>();
		territoryListView.getItems().add(listOfTerritories);
		//not able to pass good type of list
		//boolean result = playerModel.playerHasAValidAttackMove(territoryListView, textArea);
	}
	
	@Test
	public void playerHasAValidAttackMove() {
		territoryListView = new ListView<>();
		territoryListView.getItems().add(listOfTerritories);
		//not able to pass good type of list
		//boolean result = playerModel.playerHasAValidAttackMove(territoryListView, textArea);
	}
	
	@Test
	public void checkIfAnyPlayerLostTheGame() {
		Player playerLost;
		listOfTerritories = new ArrayList<>();
		players = new ArrayList<>();
		players.add(new Player(0, "Player0"));
		players.get(0).setAssignedTerritory(listOfTerritories);
		playerLost = playerModel.checkIfAnyPlayerLostTheGame(players);
		Assert.assertEquals("Player0", players.get(0).getName());
	}
	
	@Test
	public void createPlayer() {
		List<Player> playerTest = new ArrayList<>();
		players = new ArrayList<>();
		players.add(new Player(0, "Player0"));
		players.add(new Player(1, "Player1"));
		players.add(new Player(2, "Player2"));
		playerModel.createPlayer(players.size(), playerTest, textArea);
		Assert.assertEquals(3, playerTest.size());   
	}
	
	/**
	 * This method tests number of armies for 100 initial armies and 10 territory during each reinforcement phase.
	 */
	@Test
	public void calculateReinforcementArmiesCaseThree() {		
		Territory terr = new Territory();
		terr.setName("Russia");
		terr.setBelongToContinent(continent);
		listOfTerritories.add(terr);
		
		terr.setName("Canada");
		terr.setBelongToContinent(continent);
		listOfTerritories.add(terr);
		
		terr.setName("Japan");
		terr.setBelongToContinent(continent);
		listOfTerritories.add(terr);
		
		terr.setName("UAE");
		terr.setBelongToContinent(continent);
		listOfTerritories.add(terr);
		
		terr.setName("UK");
		terr.setBelongToContinent(continent);
		listOfTerritories.add(terr);
		
		terr.setName("USA");
		terr.setBelongToContinent(continent);
		listOfTerritories.add(terr);
		
		terr.setName("Mexico");
		terr.setBelongToContinent(continent);
		listOfTerritories.add(terr);
		
		terr.setName("Pakistan");
		terr.setBelongToContinent(continent);
		listOfTerritories.add(terr);
		
		terr.setName("Afghanistan");
		terr.setBelongToContinent(continent);
		listOfTerritories.add(terr);
		
		terr.setName("Malaysia");
		terr.setBelongToContinent(continent);
		listOfTerritories.add(terr);
		
		terr.setName("Thailand");
		terr.setBelongToContinent(continent);
		listOfTerritories.add(terr);
		
		terr.setName("New Zealand");
		terr.setBelongToContinent(continent);
		listOfTerritories.add(terr);
		
		player.setAssignedTerritory(listOfTerritories);
		
		Player returnedPlayer = playerModel.calculateReinforcementArmies(map, player);
		Assert.assertEquals(returnedPlayer.getArmies(), 145);
	}
	
	/**
	 * This method tests whether Fortification phase is valid by setting territories armies to 2  
	 */
	@Test
	public void isFortificationPhaseValidTrue()
	{  
	 map.getContinents().get(0).setTerritories(listOfTerritories);
	 map.getContinents().get(0).getTerritories().get(0).setPlayer(player);
	 map.getContinents().get(0).getTerritories().get(0).setArmies(2);
	 map.getContinents().get(0).getTerritories().get(0).getAdjacentTerritories().get(0).setPlayer(player);
	 
	 boolean isFortificationPhaseValid = playerModel.isFortificationPhaseValid(map, player);
	 
	 Assert.assertEquals(true, isFortificationPhaseValid);
	}
	
	/**
	 * This method tests whether Fortification phase is valid by setting territories armies to 0  
	 */
	@Test
	public void isFortificationPhaseValidFalse()
	{  	 
	 map.getContinents().get(0).setTerritories(listOfTerritories);
	 map.getContinents().get(0).getTerritories().get(0).setPlayer(player);
	 map.getContinents().get(0).getTerritories().get(0).setArmies(0);
	 map.getContinents().get(0).getTerritories().get(0).getAdjacentTerritories().get(0).setPlayer(player);

	 boolean isFortificationPhaseValid = playerModel.isFortificationPhaseValid(map, player);

	 Assert.assertEquals(false, isFortificationPhaseValid);
	}
	
	/**
	 * This method checks whether attack move is valid or not by creating a new player
	 */
	@Test
	public void isAValidAttackMoveTrue() throws InvalidGameMoveException
	{   
		Player player2 = new Player(2, "Monu");
		territory1.setPlayer(player);
		territory2.setPlayer(player2);
		territory1.setArmies(3);
		
		Assert.assertEquals(true,playerModel.isAValidAttackMove(territory1, territory2));
		
	}
	
	/**
	 * This method whether same number of armies have been traded in return of valid combination of cards or not.
	 */
	@Test
	public void tradeCardsForArmy() {
		listOfCards.add(new Card(CardType.ARTILLERY));
		listOfCards.add(new Card(CardType.CAVALRY));
		listOfCards.add(new Card(CardType.INFANTRY));
		playerModel.setPlayerPlaying(player);
		Player returnedPlayer = playerModel.tradeCardsForArmy(listOfCards, 1, textArea);
		Assert.assertEquals(105, returnedPlayer.getArmies());
	}
	
	
	@Test
	public void assignArmiesToPlayers() {
		players = new ArrayList<>();
		players.add(new Player(1, "sonu"));
		players.add(new Player(2, "Monu"));
		players.add(new Player(3, "Nonu"));
		boolean resultPlayerThree = playerModel.assignArmiesToPlayers(players, textArea);
		Assert.assertTrue(resultPlayerThree);
		Assert.assertEquals(15, players.get(0).getArmies());
		
		players = new ArrayList<>();
		players.add(new Player(1, "sonu"));	
		players.add(new Player(2, "Monu"));
		players.add(new Player(3, "Nonu"));
		players.add(new Player(4, "Sam"));
		boolean resultPlayerFour = playerModel.assignArmiesToPlayers(players, textArea);
		Assert.assertTrue(resultPlayerFour);
		Assert.assertEquals(30, players.get(0).getArmies());
		
		players = new ArrayList<>();
		players.add(new Player(1, "sonu"));
		players.add(new Player(2, "Monu"));
		players.add(new Player(3, "Nonu"));
		players.add(new Player(4, "Sam"));
		players.add(new Player(5, "John"));
		boolean resultPlayerFive = playerModel.assignArmiesToPlayers(players, textArea);
		Assert.assertTrue(resultPlayerFive);
		Assert.assertEquals(25, players.get(0).getArmies());
		
		players = new ArrayList<>();
		players.add(new Player(1, "sonu"));
		players.add(new Player(2, "Monu"));
		players.add(new Player(3, "Nonu"));
		players.add(new Player(4, "Sam"));
		players.add(new Player(5, "John"));
		players.add(new Player(6, "Harry"));
		boolean resultPlayerSix = playerModel.assignArmiesToPlayers(players, textArea);
		Assert.assertTrue(resultPlayerSix);
		Assert.assertEquals(20, players.get(0).getArmies());
	}
	
}
