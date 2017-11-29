package com.risk.model;

import static org.junit.Assert.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javafx.scene.control.ListView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.risk.entity.Continent;
import com.risk.entity.Map;
import com.risk.entity.Player;
import com.risk.entity.Territory;
import com.risk.exception.InvalidGameMoveException;
import com.risk.exception.InvalidMapException;
import com.risk.strategy.CheaterStrategy;
import com.risk.strategy.HumanStrategy;
import javafx.collections.FXCollections;

import javafx.scene.control.TextArea;
/**
 * Tournament Model Test class.
 * 
 * @author Vipul Srivastav
 * @version 1.0.0
 */
public class TournamentModelTest {

	/**
	 * The @playerGamePhase.
	 */
	static PlayerGamePhase playerGamePhase;

	/**
	 * The @tournamentModel.
	 */
	static TournamentModel tournamentModel;

	/**
	 * The @tournamentResult.
	 */
	static HashMap<String, HashMap<String, String>> tournamentResult;

	/**
	 * The @gameModel.
	 */
	static GameModel gameModel;

	/**
	 * This method is invoked at the start of the test class.
	 */
	@BeforeClass
	public static void beforeClass() {
		gameModel = new GameModel();
		playerGamePhase = new PlayerGamePhase();
		tournamentModel = new TournamentModel();
	}

	/**
	 * This method is invoked at the start of all the test methods.
	 */
	@Before
	public void beforeTest() {

	}

	/**
	 * Create Map Clone Test.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InvalidMapException
	 */
	@Test
	public void createMapCloneTest() throws IOException, ClassNotFoundException, InvalidMapException {
		Map map = new Map();
		Continent continent = new Continent();
		Territory territory1 = new Territory();
		Territory territory2 = new Territory();
		String controlValue = "7";
		continent.setName("Asia");
		continent.setValue(controlValue);
		territory1.setName("India");
		territory1.setBelongToContinent(continent);
		continent.getTerritories().add(territory1);
		territory2.setName("China");
		territory2.setBelongToContinent(continent);
		continent.getTerritories().add(territory2);
		territory1.getAdjacentTerritories().add(territory2);
		territory2.getAdjacentTerritories().add(territory1);
		Continent continent2 = new Continent();
		Territory terr = new Territory();
		continent2.setName("Africa");
		continent2.setValue("5");
		terr.setName("New Territory");
		terr.setBelongToContinent(continent2);
		terr.getAdjacentTerritories().add(territory1);
		territory1.getAdjacentTerritories().add(terr);
		continent2.getTerritories().add(terr);
		map.getContinents().add(continent);
		map.getContinents().add(continent2);
		Map clonedMap = tournamentModel.createMapClone(map);
		Assert.assertEquals(map.getContinents().get(0).getName(), clonedMap.getContinents().get(0).getName());
		Assert.assertEquals(map.getContinents().get(0).getTerritories().get(0).getName(),
				clonedMap.getContinents().get(0).getTerritories().get(0).getName());
		Assert.assertEquals(map.getContinents().get(1).getName(), clonedMap.getContinents().get(1).getName());
		Assert.assertEquals(map.getContinents().get(1).getTerritories().get(0).getName(),
				clonedMap.getContinents().get(1).getTerritories().get(0).getName());
	}
	
	/**
	 * Tournament Game Test.
	 * @throws InvalidGameMoveException 
	 */
	@Test
	public void startTournamentGameTest() throws InvalidGameMoveException {
		Map map=new Map();
		Continent continent = new Continent();
		Territory territory1 = new Territory();
		Territory territory2 = new Territory();
		String controlValue = "7";

		continent.setName("Asia");
		continent.setValue(controlValue);

		territory1.setName("India");
		territory1.setBelongToContinent(continent);

		continent.getTerritories().add(territory1);

		territory2.setName("China");
		territory2.setBelongToContinent(continent);

		continent.getTerritories().add(territory2);

		territory1.getAdjacentTerritories().add(territory2);
		territory2.getAdjacentTerritories().add(territory1);

		Continent continent2 = new Continent();
		Territory terr = new Territory();
		continent2.setName("Africa");
		continent2.setValue("5");
		terr.setName("New Territory");
		terr.setBelongToContinent(continent2);
		terr.getAdjacentTerritories().add(territory1);
		territory1.getAdjacentTerritories().add(terr);
		continent2.getTerritories().add(terr);
		map.getContinents().add(continent);
		map.getContinents().add(continent2);
		int numberOfTurn = 2; 
		
//		TextArea console = new TextArea("test");
		int gameNumber = 2;

		Player player1 = new Player(1); 
		
		player1.setArmies(5);
		player1.setStrategy(new HumanStrategy());
		player1.getAssignedTerritory().add(territory1);
		
		territory1.setPlayer(player1);
	

		Player player2 = new Player(2); 
		
		player2.setArmies(5);
		player2.setStrategy(new CheaterStrategy());
		player2.getAssignedTerritory().add(territory2);
		
		territory2.setPlayer(player2);
		
	
		
		List<Player> players = new ArrayList<Player>() ;
		players.add(player1);
		players.add(player2);
	

//		tournamentModel.startTournamentGame(players, map, numberOfTurn, console, gameNumber);
//		System.out.println(tournamentModel.getTournamentResult());
		
		
		

	}


	
}
//