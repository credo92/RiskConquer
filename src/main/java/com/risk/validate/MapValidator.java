package com.risk.validate;

import java.util.List;

import com.risk.entity.Continent;
import com.risk.entity.Map;
import com.risk.entity.Territory;
import com.risk.exception.InvalidMapException;

/**
 * @author rahul
 *
 */
public class MapValidator {
static int territorySize;
static Continent continent = new Continent();
	/**
	 * @param map
	 * @throws InvalidMapException
	 */
	public static void validateMap(Map map) throws InvalidMapException {
		if (map != null) {
			for (Continent continent : map.getContinents()) {
				if (continent != null) {
					validateContinent(continent);
				} else {
					throw new InvalidMapException("Map should contain atleast one continent.");
				}
			}
		} else {
			throw new InvalidMapException("Empty file no Map exist.");
		}
	}
	
	public static int getTerritorySize() {
		territorySize = continent.getTerritories().size();
		return territorySize;
	}

	/**
	 * @param continent
	 * @throws InvalidMapException
	 */
	public static void validateContinent(Continent continent) throws InvalidMapException {
		if (getTerritorySize() < 1) {
			throw new InvalidMapException(
					"Continent: " + continent.getName() + " should contain atleast one territory");
		}

		for (Territory territory : continent.getTerritories()) {
			if (territory != null) {
				validateTerritory(territory);
			}
		}
	}

	/**
	 * @param territory
	 * @throws InvalidMapException
	 */
	public static void validateTerritory(Territory territory) throws InvalidMapException {

		List<Territory> adjTerritory = territory.getAdjacentTerritories();

		if (adjTerritory != null && adjTerritory.size() < 1) {
			throw new InvalidMapException(
					"Territory: " + territory.getName() + " should be mapped with atleas one adjacent territory.");
		}
	}
}