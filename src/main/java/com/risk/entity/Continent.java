package com.risk.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This is an entity class for Continent with name, value, territoryMap and territories as its member variables.
 * @author rahul
 * @version 1.0.0
 */
public class Continent implements Serializable{
	
	/**
	 * The @name .
	 */
	private String name;
	
	/**
	 * The @value .
	 */
	private String value;
	
	/**
	 * The @territoryMap.
	 */
	private HashMap<String, Territory> territoryMap;
	
	/**
	 * The @territories.
	 */
	private List<Territory> territories;
	
	/**
	 * Constructor for Continent 
	 */
	public Continent() {
		territoryMap = new HashMap<>();
		territories = new ArrayList<>();
	}

	/**
	 * Get Territory Map
	 * 
	 * @return the territoryMap
	 */
	public HashMap<String, Territory> getTerritoryMap() {
		return territoryMap;
	}

	/**
	 * Set territory Map
	 * 
	 * @param territoryMap
	 *            the territoryMap to set
	 */
	public void setTerritoryMap(HashMap<String, Territory> territoryMap) {
		this.territoryMap = territoryMap;
	}

	/**
	 * Get name
	 * 
	 * @return name 
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set Name
	 * 
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get Value
	 * 
	 * @return value 
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Set Value
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Get Territories
	 * 
	 * @return territories 
	 */
	public List<Territory> getTerritories() {
		return territories;
	}

	/**
	 * Set territories
	 * 
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
