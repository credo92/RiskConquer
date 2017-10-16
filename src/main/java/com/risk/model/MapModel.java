package com.risk.model;

import java.util.ArrayList;
import java.util.List;

import com.risk.entity.Continent;
import com.risk.entity.Territory;

/**
 * 
 * @author rahul
 *
 */
public class MapModel {

	/**
	 * @param name
	 * @param controlValue
	 * @return
	 */
	public Continent addContinent(String name, String controlValue) {
		Continent continent = new Continent();

		continent.setName(name);
		continent.setValue(controlValue);

		return continent;
	}

	/**
	 * @param continent
	 * @param controlValue
	 * @return
	 */
	public Continent updateContinent(Continent continent, String controlValue) {

		continent.setValue(controlValue);

		return continent;
	}

	/**
	 * @param territory
	 * @param xAxis
	 * @param yAxis
	 * @param adjTerritory
	 * @return
	 */
	public Territory updateTerritory(Territory territory, String xAxis, String yAxis, Territory adjTerritory) {

		territory.setxCoordinate(Integer.valueOf(xAxis));
		territory.setyCoordinate(Integer.valueOf(yAxis));
		if (adjTerritory != null) {
			if (!territory.getAdjacentTerritories().contains(adjTerritory))
				territory.getAdjacentTerritories().add(adjTerritory);
		}

		return territory;
	}

	/**
	 * @param name
	 * @param xAxis
	 * @param yAxis
	 * @param adjTerritory
	 * @param continent
	 * @return
	 */
	public Territory addTerritory(String name, String xAxis, String yAxis, Territory adjTerritory,
			Continent continent) {

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

		return territory;
	}

	/**
	 * @param continent
	 * @param territory
	 * @return
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
