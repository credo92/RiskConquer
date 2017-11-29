package com.risk.model;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.risk.entity.Continent;
import com.risk.entity.Map;
import com.risk.entity.Player;
import com.risk.entity.Territory;
import com.risk.exception.InvalidMapException;

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
		playerGamePhase.setPlayerPlaying(new Player(1));

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

}
