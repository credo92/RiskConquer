package com.risk.model;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.risk.constant.PlayerType;
import com.risk.entity.Continent;
import com.risk.entity.Map;
import com.risk.entity.Player;
import com.risk.entity.Territory;
import com.risk.exception.InvalidGameMoveException;
import com.risk.exception.InvalidMapException;
import com.risk.strategy.BenevolentStrategy;
import com.risk.strategy.CheaterStrategy;
import com.risk.strategy.RandomStrategy;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
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
	 * The @textArea
	 */
	@FXML
	static TextArea textArea;

	/**
	 * The @fxPanel
	 */
	static JFXPanel fxPanel;
	
	/**
	 * This method is invoked at the start of the test class.
	 */
	@BeforeClass
	public static void beforeClass() {
		gameModel = new GameModel();
		playerGamePhase = new PlayerGamePhase();
		tournamentModel = new TournamentModel();
		fxPanel = new JFXPanel();
		textArea = new TextArea();	
		tournamentResult = new HashMap<String, HashMap<String, String>>();
		tournamentModel.setTournamentResult(tournamentResult);
	}

	/**
	 * This method is invoked at the start of all the test methods.
	 */
	@Before
	public void beforeTest() {
		playerGamePhase.setPlayerPlaying(new Player(1));

	}

	/**
	 * Create Map Clone Test.
	 * 
	 * @throws IOException IOException
	 * @throws ClassNotFoundException ClassNotFoundException
	 * @throws InvalidMapException InvalidMapException
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

		HashMap<String, String> mapData = new HashMap<String,String>();
		mapData.put("image", "test");

		map.setMapData(mapData);

		int numberOfTurn = 10; 
		int gameNumber = 1;

		Player player1 = new Player(1); 

		player1.setArmies(15);
		player1.setStrategy(new RandomStrategy());
		player1.setName("playerA");
		player1.setType(PlayerType.RANDOM);


		Player player2 = new Player(2); 

		player2.setArmies(15);
		player2.setName("playerB");
		player2.setStrategy(new CheaterStrategy());
		player2.setType(PlayerType.CHEATER);

		Player player3 = new Player(2); 

		player3.setArmies(15);
		player3.setName("playerC");
		player3.setStrategy(new BenevolentStrategy());
		player3.setType(PlayerType.BENEVOLENT);

		Player player4 = new Player(2); 

		player4.setArmies(15);
		player4.setName("playerD");
		player4.setStrategy(new BenevolentStrategy());
		player4.setType(PlayerType.BENEVOLENT);

		List<Player> players = new ArrayList<Player>() ;
		players.add(player1);
		players.add(player2);
		tournamentModel.startTournamentGame(players, map, numberOfTurn, textArea, gameNumber);
		tournamentModel.getTournamentResult();
		Assert.assertNotNull(tournamentModel.getTournamentResult());
	}
	
	/**
	 * This method tests player type.
	 */
	@Test
	public void returnPlayerType() {
		Player p = tournamentModel.returnPlayerType("RANDOM", new Player(0));
		Assert.assertEquals("RANDOM", p.getType().toString());
	} 
}
