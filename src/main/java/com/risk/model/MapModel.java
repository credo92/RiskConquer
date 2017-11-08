package com.risk.model;

import java.util.ArrayList;
import java.util.List;

import com.risk.entity.Continent;
import com.risk.entity.Map;
import com.risk.entity.Territory;
import com.risk.exception.InvalidMapException;

/**
 * Model class for the map 
 * @author rahul
 * @version 1.0.0
 */
public class MapModel {

	/**
	 * Create new continent.
	 * 
	 * @param map
	 *            map object
	 * @param name
	 *            name of the continent
	 * @param controlValue
	 *            control value of the continent
	 * @return continent created successfully
	 * @throws InvalidMapException
	 *             invalid map exception
	 */
	public Continent addContinent(Map map, String name, String controlValue) throws InvalidMapException {
		Continent continent = new Continent();

		continent.setName(name);
		continent.setValue(controlValue);

		if (map.getContinents().contains(continent)) {
			throw new InvalidMapException("Continent with name: " + name.toUpperCase() + " already exist.");
		}

		return continent;
	}

	/**
	 * Update continent.
	 * 
	 * @param continent
	 *            continent object to be updated
	 * @param controlValue
	 *            control value to be updated
	 * @return continent update continent.
	 */
	public Continent updateContinent(Continent continent, String controlValue) {

		continent.setValue(controlValue);

		return continent;
	}

	/**
	 * Update territory.
	 * 
	 * @param territory
	 *            territory to be updated
	 * @param xAxis
	 *            x axis
	 * @param yAxis
	 *            y axis
	 * @param adjTerritory
	 *            list of adj territory
	 * @return territory updated territory
	 */
	public Territory updateTerritory(Territory territory, String xAxis, String yAxis, Territory adjTerritory) {

		territory.setxCoordinate(Integer.valueOf(xAxis));
		territory.setyCoordinate(Integer.valueOf(yAxis));
		if (adjTerritory != null) {
			if (!territory.getAdjacentTerritories().contains(adjTerritory)) {
				territory.getAdjacentTerritories().add(adjTerritory);
			}
			if (!adjTerritory.getAdjacentTerritories().contains(territory)) {
				adjTerritory.getAdjacentTerritories().add(territory);
			}
		}

		return territory;
	}


	/**
	 * Add territory
	 * @param map map object
	 * @param name name of the territory
	 * @param xAxis x axis of the territory
	 * @param yAxis y axis of the territory
	 * @param adjTerritory adj territory list
	 * @param continent continent
	 * @return territory new object
	 * @throws InvalidMapException invalid map exception
	 */
	public Territory addTerritory(Map map, String name, String xAxis, String yAxis, Territory adjTerritory,
			Continent continent) throws InvalidMapException {

		Territory territory = new Territory();
		List<Territory> tList = new ArrayList<>();

		territory.setName(name);
		territory.setxCoordinate(Integer.parseInt(xAxis));
		territory.setyCoordinate(Integer.parseInt(yAxis));
		territory.setBelongToContinent(continent);
		if (adjTerritory != null) {
			tList.add(adjTerritory);
		}
		territory.setAdjacentTerritories(tList);

		// check for unique territory
		for (Continent existContinent : map.getContinents()) {
			if (existContinent.getTerritories().contains(territory)) {
				throw new InvalidMapException(
						"Territory: " + name + " already exist in continent " + existContinent.getName());
			}
		}
		if (adjTerritory != null) {
			adjTerritory.getAdjacentTerritories().add(territory);
		}
		return territory;
	}

	/**
	 * Assign territory to a contiennt
	 * 
	 * @param continent
	 *            continent object
	 * @param territory
	 *            territory object
	 * @return continent updated continent.
	 */
	public Continent assignTerrToContinent(Continent continent, Territory territory) {

		if (continent.getTerritories() == null) {
			List<Territory> newTList = new ArrayList<>();
			newTList.add(territory);
			continent.setTerritories(newTList);
		} else {
			continent.getTerritories().add(territory);
		}
		return continent;
	}
}
