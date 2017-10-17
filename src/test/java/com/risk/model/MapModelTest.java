package com.risk.model;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.risk.entity.Continent;
import com.risk.entity.Territory;

public class MapModelTest {
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
	
	/**
	 * This method is invoked at the start of the test class.
	 */
	@BeforeClass
	public static void beforeClass() {
		System.out.println("Testing MapModel.java started");
		continent = new Continent();
		territory = new Territory();
		adjTerritory = new Territory();
		mapModel = new MapModel();
	}	
	
	/**
	 * This method is invoked at the end of the test class.
	 */
	@AfterClass
	public static void afterClass() {
		System.out.println("Testing MapModel.java completed");
	}	
	
	/**
	 * This method is to test add Continent functionality.
	 */
	@Test
	public void addContinent() {
		continent =  mapModel.addContinent(continentName, controlValue1);
		Assert.assertNotNull(continent);
		Assert.assertEquals(continent.getName(), continentName);
		Assert.assertEquals(continent.getValue(), controlValue1);
	}
	
	/**
	 * This method is to test update Continent functionality.
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
	 */
	@Test
	public void addTerritory() {
		territory = mapModel.addTerritory(territoryName, xAxis1, yAxis1, null, continent);
		Assert.assertNotNull(territory);
		Assert.assertEquals(territory.getName(), territoryName);
		Assert.assertEquals(territory.getxCoordinate(), Integer.parseInt(xAxis1));
		Assert.assertEquals(territory.getyCoordinate(), Integer.parseInt(yAxis1));
		Assert.assertEquals(territory.getBelongToContinent(), continent);
	}
	
	/**
	 * This method is to test update Territory functionality.
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
	 */
	@Test
	public void assignTerrToContinent() {
		Territory newTerritory = new Territory();
		newTerritory = mapModel.addTerritory("Canada", "1", "10", null, continent);
		continent = mapModel.assignTerrToContinent(continent, newTerritory);
		Assert.assertNotNull(continent);
		Assert.assertTrue(continent.getTerritories().contains(newTerritory));
	}
}
