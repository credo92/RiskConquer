package com.risk.validate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import com.risk.entity.Continent;
import com.risk.entity.Map;
import com.risk.entity.Territory;
import com.risk.exception.InvalidMapException;

/**
 * Map validator.
 * @author rahul
 * @version 1.0.0
 */
public class MapValidator {

	/**
	 * Validate the map data.
	 * @param map map object
	 * @throws InvalidMapException invalid exception 
	 */
	public static void validateMap(Map map) throws InvalidMapException {
		if (map != null) {
			if (map.getContinents().size() > 0) {
				for (Continent continent : map.getContinents()) {
					if (continent != null) {
						validateContinent(continent, map);
					}
				}
			} else {
				throw new InvalidMapException("Map should contain atleast one continent.");
			}
			isTerritoryUniquelyAssociated(map);
		} else {
			throw new InvalidMapException("Empty file no Map exist.");
		}
	}

	/**
	 * Validate continet. It should have atleast one territory.
	 * @param continent continent object
	 * @param map map object
	 * @throws InvalidMapException invalid map exception
	 */
	public static void validateContinent(Continent continent, Map map) throws InvalidMapException {
		if (continent.getTerritories().size() < 1) {
			throw new InvalidMapException(
					"Continent: " + continent.getName() + " should contain atleast one territory");
		}
		if (!continentIsASubGraph(continent, map)) {
			throw new InvalidMapException("Continent: " + continent.getName().toUpperCase()
					+ " is not a subgraph. The continent should be connected to another continent via territory.");
		}
		for (Territory territory : continent.getTerritories()) {
			if (territory != null) {
				validateTerritory(territory, map);
			}
		}
	}

	/**
	 * Check if graph is a connected graph.
	 * @param continent continent object
	 * @param map map object
	 * @return boolean true or false
	 */
	private static boolean continentIsASubGraph(Continent continent, Map map) {
		boolean isASubGraph = false;
		HashSet<Continent> set = new HashSet<>();
		for (Territory territory : continent.getTerritories()) {
			for (Continent otherContinent : map.getContinents()) {
				if (!otherContinent.equals(continent)) {
					for (Territory otherTerritory : otherContinent.getTerritories()) {
						if (otherTerritory.getAdjacentTerritories().contains(territory)) {
							set.add(otherContinent);
						}
					}
				}
			}
		}
		if (set.size() > 0) {
			isASubGraph = true;
		}
		return isASubGraph;
	}

	/**
	 * Check if the territory is a valid territory. It has atleast one adjacent territory.
	 * @param territory territory to be tested
	 * @throws InvalidMapException invalid map exception.
	 */
	public static void validateTerritory(Territory territory, Map map) throws InvalidMapException {

		List<Territory> adjTerritory = territory.getAdjacentTerritories();

		if (adjTerritory != null && adjTerritory.size() < 1) {
			throw new InvalidMapException(
					"Territory: " + territory.getName() + " should be mapped with atleas one adjacent territory.");
		}
	}

	/**
	 * Check if the territories are uniquely associated with the continent.
	 * @param map map object
	 * @throws InvalidMapException invalid map exception
	 */
	public static void isTerritoryUniquelyAssociated(Map map) throws InvalidMapException {
		HashMap<Territory, Integer> territoryAssociation = new HashMap<>();

		for (Continent continent : map.getContinents()) {
			for (Territory territory : continent.getTerritories()) {
				if (territoryAssociation.containsKey(territory)) {
					territoryAssociation.put(territory, territoryAssociation.get(territory) + 1);
				} else {
					territoryAssociation.put(territory, 1);
				}
			}
		}

		for (Entry<Territory, Integer> set : territoryAssociation.entrySet()) {
			if (set.getValue() > 1) {
				throw new InvalidMapException(
						"Territory: " + set.getKey().getName() + " belongs to multiple continent.");
			}
		}
	}
}
