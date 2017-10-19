package com.risk.model;

import java.util.HashMap;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.risk.entity.Continent;
import com.risk.entity.Map;
import com.risk.entity.Territory;
import com.risk.exception.InvalidMapException;

/**
 * Map Model Test class.
 * @author Garvpreet Singh
 * @version 1.0.0
 */
public class MapModelTest {
	static Map map;
	static Continent continent;
	static Territory territory;
	static Territory adjTerritory;
	static MapModel mapModel;
	String continentName = "Asia";
	String controlValue1 = "7";
	String controlValue2 = "2";
	String territoryName = "India";
	String xAxis1 = "1";
	String xAxis2 = "2";
	String yAxis1 = "1";
	String yAxis2 = "2";
	String mapAuthor = "Robert";
	String mapImage = "world.map";
	String mapWrap = "no";
	String mapScroll = "horizontal";
	String mapWarn = "yes";
	
	static HashMap<String, String> mapData;
	
	/**
	 * This method is invoked at the start of the test class.
	 */
	@BeforeClass
	public static void beforeClass() {
		map = new Map();
		continent = new Continent();
		territory = new Territory();
		adjTerritory = new Territory();
		mapModel = new MapModel();		
	}	
	
	/**
	 * This method is invoked at the start of all the test methods.
	 */
	@Before
	public void beforeTest() {
		mapData = new HashMap<>();
		mapData.put("author", mapAuthor);
		mapData.put("image", mapImage);
		mapData.put("wrap", mapWrap);
		mapData.put("scroll", mapScroll);
		mapData.put("warn", mapWarn);		
		map.setMapData(mapData);
		
	}
	
	/**
	 * This method is to test add Continent functionality.
	 * @throws InvalidMapException invaild map exception
	 */
	@Test
	public void addContinent() throws InvalidMapException {
		continent =  mapModel.addContinent(map, continentName, controlValue1);
		Assert.assertNotNull(continent);
		Assert.assertEquals(continent.getName(), continentName);
		Assert.assertEquals(continent.getValue(), controlValue1);
	}
	
	/**
	 * This method is to test update Continent functionality, like to change its value.
	 */
	@Test
	public void updateContinent() {
		continent =  mapModel.updateContinent(continent, controlValue2);
		Assert.assertNotNull(continent);
		Assert.assertEquals(continent.getValue(), controlValue2);
		Assert.assertNotEquals(continent.getValue(), controlValue1);
	}	
	
	/**
	 * This method is to test add Territory functionality.
	 * @throws InvalidMapException invaild map exception
	 */
	@Test
	public void addTerritory() throws InvalidMapException {
		territory = mapModel.addTerritory(map, territoryName, xAxis1, yAxis1, null, continent);
		Assert.assertNotNull(territory);
		Assert.assertEquals(territory.getName(), territoryName);
		Assert.assertEquals(territory.getxCoordinate(), Integer.parseInt(xAxis1));
		Assert.assertEquals(territory.getyCoordinate(), Integer.parseInt(yAxis1));
		Assert.assertEquals(territory.getBelongToContinent(), continent);
	}
	
	/**
	 * This method is to test update Territory functionality whether territory gets updated or not.
	 */
	@Test
	public void updateTerritory() {
		territory = mapModel.updateTerritory(territory, xAxis2, yAxis2, null);
		Assert.assertNotNull(territory);
		Assert.assertEquals(territory.getxCoordinate(), Integer.parseInt(xAxis2));
		Assert.assertEquals(territory.getyCoordinate(), Integer.parseInt(yAxis2));
		Assert.assertNotEquals(territory.getxCoordinate(), Integer.parseInt(xAxis1));
		Assert.assertNotEquals(territory.getyCoordinate(), Integer.parseInt(yAxis1));
	}
	
	/**
	 * This method is to test assigning territory to a continent.
	 * @throws InvalidMapException invaild map exception
	 */
	@Test
	public void assignTerrToContinent() throws InvalidMapException {
		Territory newTerritory = new Territory();
		newTerritory = mapModel.addTerritory(map, "Canada", "1", "10", null, continent);
		continent = mapModel.assignTerrToContinent(continent, newTerritory);
		Assert.assertNotNull(continent);
		Assert.assertTrue(continent.getTerritories().contains(newTerritory));
	}
}
