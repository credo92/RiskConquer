package com.risk.validate;

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
import com.risk.entity.Territory;
import com.risk.exception.InvalidMapException;

/**
 * Map Validator Test class.
 * @author Garvpreet Singh
 * @version 1.0.0
 */
public class MapValidatorTest {
	
	static MapValidator mapValidator;
	
	static Continent continent;
	static Territory territory;
	static Map map;
	
	String mapAuthor = "Robert";
	String mapImage = "world.map";
	String mapWrap = "no";
	String mapScroll = "horizontal";
	String mapWarn = "yes";
	
	String continentName = "Asia";
	String controlValue = "7";
	
	static HashMap<String, String> mapData;
	List<Continent> listOfContinents;
	
	/**
	 * This method is invoked at the start of the test class.
	 */
	@BeforeClass
	public static void beforeClass() {
		continent = new Continent();
		territory = new Territory();
		map = new Map();
		mapValidator = new MapValidator();
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
		
		continent.setName(continentName);
		continent.setValue(controlValue);
		
		listOfContinents = new ArrayList<>();
		listOfContinents.add(continent);
	}
		
	/**
	 * This method is used to test if a map is null or not.
	 * @throws InvalidMapException invalid map exception.
	 */
	@Test (expected = InvalidMapException.class)
	public void validateMapForNullMap() throws InvalidMapException {
		MapValidator.validateMap(null);
	}
	
	/**
	 * This method is used to test if a map has atleast one continent or not.
	 * @throws InvalidMapException invalid map exception.
	 */
	@Test (expected = InvalidMapException.class)
	public void validateMapForContinent() throws InvalidMapException {
		MapValidator.validateMap(new Map());
	}
	
	/**
	 * This method is used to test if a continent is null or not.
	 * @throws InvalidMapException invalid map exception.
	 */
	@Test (expected = InvalidMapException.class)
	public void validateContinentForNullTerritory() throws InvalidMapException {
		MapValidator.validateContinent(new Continent(),map);	
	}
	
	/**
	 * This method is used to test if a continent has atleast one territory or not.
	 * @throws InvalidMapException invalid map exception.
	 */
	@Test (expected = InvalidMapException.class)
	public void validateContinentForTerritory() throws InvalidMapException {
		map.setContinents(listOfContinents);
		MapValidator.validateContinent(continent,map);	
	} 
	
	/**
	 * This method is used to test if a continent is a sub-graph or not.
	 * @throws InvalidMapException invalid map exception.
	 */
	@Test
	public void validateContinentForSubGraph(){		
		Assert.assertFalse(MapValidator.continentIsASubGraph(continent, map));
	}
	
	/**
	 * This method is used to test if a territory has adjacent territory or not.
	 * @throws InvalidMapException invalid map exception.
	 */
	@Test (expected = InvalidMapException.class)
	public void validateTerritory() throws InvalidMapException {
		Territory terr = new Territory();
		MapValidator.validateTerritory(terr, map);
	}
	
	/**
	 * This method is used to test if a territory is uniquely associated.
	 * @throws InvalidMapException invalid map exception.
	 */
	@Test (expected = InvalidMapException.class)
	public void isTerritoryUniquelyAssociated() throws InvalidMapException {
		Territory terr = new Territory();
		MapValidator.validateTerritory(terr, map);
	}
}