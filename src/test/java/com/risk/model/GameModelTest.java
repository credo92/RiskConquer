package com.risk.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.risk.controller.GamePlayController;
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
}