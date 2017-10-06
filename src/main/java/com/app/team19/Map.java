package com.app.team19;

import java.util.HashMap;
import java.util.List;

/**
 * @author rahul
 *
 */
public class Map {
	private HashMap<String, String> mapData;

	private List<Continent> continents;

	/**
	 * @return the mapData
	 */
	public HashMap<String, String> getMapData() {
		return mapData;
	}

	/**
	 * @param mapData
	 *            the mapData to set
	 */
	public void setMapData(HashMap<String, String> mapData) {
		this.mapData = mapData;
	}

	/**
	 * @return the continents
	 */
	public List<Continent> getContinents() {
		return continents;
	}

	/**
	 * @param continents
	 *            the continents to set
	 */
	public void setContinents(List<Continent> continents) {
		this.continents = continents;
	}

	public String toString() {
		return "Map data: " + mapData.toString() + "List of continents : " + continents.toString();
	}
}
