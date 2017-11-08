package com.risk.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.risk.controller.GamePlayController;
import com.risk.entity.Card;
import com.risk.entity.Continent;
import com.risk.entity.Map;
import com.risk.entity.Player;
import com.risk.entity.Territory;

import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 * Game Model Test class.
 * @author Garvpreet Singh
 * @version 1.0.0
 */
public class GameModelTest {
	
	/**
	 * The @gameModel reference
	 */
	static GameModel gameModel;
	
	/**
	 * The @continent reference
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
	 * The @map reference
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
	 * The @fxPanel
	 */
	static JFXPanel fxPanel;
	
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
	 * The @listOfContinents
	 */
	static List<Continent> listOfContinents;
	
	/**
	 * The @listOfTerritories
	 */
	static List<Territory> listOfTerritories;
	
	/**
	 * The @listOfPlayers
	 */
	static List<Player> listOfPlayers;
	
	/**
	 * This method is invoked at the start of the test class.
	 */
	@BeforeClass
	public static void beforeClass() {
		gameModel = new GameModel();
		continent = new Continent();
		territory1 = new Territory();
		territory2 = new Territory();
		map = new Map();
		player = new Player(1, "Sonu");
		listOfContinents = new ArrayList<>();
		listOfTerritories = new ArrayList<>();
		listOfPlayers = new ArrayList<>();
		fxPanel = new JFXPanel();
		textArea = new TextArea();		
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
		territory2.getAdjacentTerritories().add(territory2);
		territory1.setAdjacentTerritories(territory2.getAdjacentTerritories());		
		territory1.getAdjacentTerritories().add(territory1);
		territory2.setAdjacentTerritories(territory1.getAdjacentTerritories());
		
		listOfContinents.add(continent);
		map.setContinents(listOfContinents);
		
		player.setArmies(100);
		
		listOfTerritories.add(territory1);
		player.setAssignedTerritory(listOfTerritories);
		listOfPlayers.add(player);
	}
	
	/**
	 * This method is used to test whether stack of cards has been created or not according to number of territories in the game.
	 */
	@Test
	public void assignCardToTerritory() {
		Stack<Card> stackOfCards = gameModel.assignCardToTerritory(map, textArea);
		Assert.assertNotNull(stackOfCards);		
	}
	
	/**
	 * This method is used to test whether territories are assigne to players or not.
	 */
	@Test
	public void assignTerritoryToPlayer() {
		List<Player> players = gameModel.assignTerritoryToPlayer(map, listOfPlayers, textArea);
		Assert.assertNotNull(players);		

	}
}