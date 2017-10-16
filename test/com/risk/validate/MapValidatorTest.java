package com.risk.validate;

import static org.junit.Assert.*;

import org.junit.Test;
import com.risk.entity.Continent;
import com.risk.entity.Map;
import com.risk.entity.Territory;
import com.risk.validate.MapValidator;
/**
 * Test Class for MapValidator.java 
 * @author Gurpreet Singh
 * 
 *
 */
public class MapValidatorTest {
	// Validating territories Size which validates
	// continent having n number of territories
	@Test
	public void testValidateContinent() {
		MapValidator mapValidator = new MapValidator();
		assertTrue(mapValidator.getTerritorySize() > 1);
	}
	// Validating if territory has adjacent territories
	@Test
	public void testValidateTerritory() {
		MapValidator mapValidator = new MapValidator();
		Territory territory = new Territory();
		assertTrue(territory.getAdjTerritories().size() > 1);
		assertNotNull(territory.getAdjTerritories());
	}
	// Validating if Map is not null and has continents
	@Test
	public void testValidateMap() {
		Map map = new Map();
		assertNotNull(map);
		assertNotNull(map.getContinents());
	}

}
