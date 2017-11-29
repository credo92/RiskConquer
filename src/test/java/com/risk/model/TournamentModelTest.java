package com.risk.model;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.risk.entity.Continent;
import com.risk.entity.Map;
import com.risk.entity.Player;
import com.risk.entity.Territory;
import com.risk.exception.InvalidMapException;
import com.risk.validate.MapValidator;

import javafx.scene.control.TextArea;

/**
 * Tournament Model Test class.
 * @author Vipul Srivastav
 * @version 1.0.0
 */
public class TournamentModelTest {

	static PlayerGamePhase playerGamePhase;

	static TournamentModel tournamentModel;

	static HashMap<String, HashMap<String, String>> tournamentResult;

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
		playerGamePhase.setPlayerPlaying(new Player(1));

	}

	@Test
	public void createMapCloneTest() throws IOException, ClassNotFoundException, InvalidMapException {
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
		
		MapValidator.validateMap(map);
		
		Map clonedMap = tournamentModel.createMapClone(map);
		
		MapValidator.validateMap(clonedMap);
//		Assert.assertEquals(map, clonedMap);
	}
	@Test
	public void startTournamentGameTest() {
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
//		List<Player> players, 
//		Map map, 
		int numberOfTurn = 10; 
		TextArea console = null;
		Integer gameNumber = 5;
		
		Player player1 = new Player(1); 
		player1.setArmies(35);
		player1.getAssignedTerritory().add(territory1);
		territory1.setPlayer(player1);
		player1.getAssignedTerritory().add(territory2);
		territory2.setPlayer(player1);
		
		Player player2 = new Player(2); 
		player2.setArmies(35);
		player2.getAssignedTerritory().add(territory1);
		territory1.setPlayer(player2);
		player2.getAssignedTerritory().add(territory2);
		territory2.setPlayer(player2);
		
		Player player3 = new Player(2); 
		player3.setArmies(35);
		player3.getAssignedTerritory().add(territory1);
		territory1.setPlayer(player3);
		player3.getAssignedTerritory().add(territory2);
		territory2.setPlayer(player3);
		
		Player player4 = new Player(2); 
		player4.setArmies(35);
		player4.getAssignedTerritory().add(territory1);
		territory1.setPlayer(player4);
		player4.getAssignedTerritory().add(territory2);
		territory2.setPlayer(player4);
		
		List<Player> players = new ArrayList<Player>() ;
		players.add(player1);
		players.add(player2);
		players.add(player3);
		players.add(player4);
		
		tournamentModel.startTournamentGame(players, map, numberOfTurn, console, gameNumber);
		System.out.println(tournamentModel.getTournamentResult());
	}
	
	
}
