package com.risk.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author rahul
 * This is an entity class for Continent with name, value, territoryMap and territories as its member variables.
 * @version 1.0.0
 *
 */
public class Continent {
	private String name;
	private String value;
	private HashMap<String, Territory> territoryMap;
	private List<Territory> territories;

	public Continent() {
		territoryMap = new HashMap<>();
		territories = new ArrayList<>();
	}

	/**
	 * @return the territoryMap
	 */
	public HashMap<String, Territory> getTerritoryMap() {
		return territoryMap;
	}

	/**
	 * @param territoryMap
	 *            the territoryMap to set
	 */
	public void setTerritoryMap(HashMap<String, Territory> territoryMap) {
		this.territoryMap = territoryMap;
	}

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {

		if (obj == this) {
			return true;
		}

		if (!(obj instanceof Continent)) {
			return false;
		}

		Continent continent = (Continent) obj;
		return continent.getName().equalsIgnoreCase(name);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Name: " + name + "value: " + "List of territory :" + territories.toString();
	}
}
