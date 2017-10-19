package com.risk.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.risk.entity.Continent;
import com.risk.entity.Map;
import com.risk.entity.Player;
import com.risk.entity.Territory;

/**
 * Game Model Test class.
 * @author Garvpreet Singh
 * @version 1.0.0
 */
public class GameModelTest {
	
	static GameModel gameModel;
	static Continent continent;
	static Territory territory1;
	static Territory territory2;
	static Map map;
	static Player player;
	
	String continentName = "Asia";
	String controlValue = "7";	
	String territoryName1 = "India";
	String territoryName2 = "China";	
	
	List<Continent> listOfContinents = new ArrayList<>();	
	List<Territory> listOfTerritories = new ArrayList<>();
	
	/**
	 * This method is invoked at the start of the test class.
	 */
	@BeforeClass
	public static void beforeClass() {
		System.out.println("Testing GameModel.java started");
		gameModel = new GameModel();
		continent = new Continent();
		territory1 = new Territory();
		territory2 = new Territory();
		map = new Map();
		player = new Player(1, "Sonu");
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
	}
	
	/**
	 * This method is invoked at the end of the test class.
	 */
	@AfterClass
	public static void afterClass() {
		System.out.println("Testing GameModel.java completed");
	}	
	
	/**
	 * This method tests number of armies for 100 initial armies and 1 territory during each reinforcement phase.
	 */
	@Test
	public void calculateReinforcementArmiesCaseOne() {		
		Player returnedPlayer = gameModel.calculateReinforcementArmies(map, player);
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
		Player returnedPlayer = gameModel.calculateReinforcementArmies(map, player);
		Assert.assertEquals(returnedPlayer.getArmies(), 115);
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
		
		Player returnedPlayer = gameModel.calculateReinforcementArmies(map, player);
		Assert.assertEquals(returnedPlayer.getArmies(), 111);
	}
}