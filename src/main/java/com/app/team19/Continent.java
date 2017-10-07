package com.app.team19;

import java.util.HashMap;
import java.util.List;

/**
 * @author rahul
 *
 */
public class Continent {
	private String name;
	private String value;
	private HashMap<String, Territory> territoryMap;
	/**
	 * @return the territoryMap
	 */
	public HashMap<String, Territory> getTerritoryMap() {
		return territoryMap;
	}

	/**
	 * @param territoryMap the territoryMap to set
	 */
	public void setTerritoryMap(HashMap<String, Territory> territoryMap) {
		this.territoryMap = territoryMap;
	}

	private List<Territory> territories;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the territories
	 */
	public List<Territory> getTerritories() {
		return territories;
	}

	/**
	 * @param territories
	 *            the territories to set
	 */
	public void setTerritories(List<Territory> territories) {
		this.territories = territories;
	}

	public String toString() {
		return "Name: " + name + "value: " + "List of territory :" + territories.toString();
	}
}
