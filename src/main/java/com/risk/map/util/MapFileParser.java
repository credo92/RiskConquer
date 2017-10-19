package com.risk.map.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.risk.entity.Continent;
import com.risk.entity.Map;
import com.risk.entity.Territory;
import com.risk.exception.InvalidMapException;
import com.risk.validate.MapValidator;

/**
 * @author rahul
 * This class is used to handle parsing of map files.
 * @version 1.0.0
 *
 */
public class MapFileParser {

	private Map map;

	private HashMap<String, Integer> territoryContinentCount = new HashMap<>();

	/**
	 * @return the map
	 */
	public Map getMap() {
		return map;
	}

	/**
	 * This method is use to parse and read map file before converting into {@link Map} object.
	 * This further validates map object.
	 * @param file of type {@link File}
	 * @return map object of type {@link Map}
	 * @throws InvalidMapException
	 */
	public Map parseAndReadMapFile(File file) throws InvalidMapException {
		this.map = convertMapFileToMapObject(file);
		MapValidator.validateMap(map);
		return map;
	}

	/**
	 * This method is use to convert map file into {@link Map} object
	 * @param file of type {@link File}
	 * @return map object of type {@link Map}
	 * @throws InvalidMapException
	 */
	private Map convertMapFileToMapObject(final File file) throws InvalidMapException {
		StringBuilder stringBuilder = new StringBuilder();

		Scanner mapFileScanner = null;
		try {
			mapFileScanner = new Scanner(new FileInputStream(file));
			while (mapFileScanner.hasNextLine()) {
				String data = mapFileScanner.nextLine();
				if (!data.isEmpty()) {
					stringBuilder.append(data + "|");
				} else {
					stringBuilder.append("\n");
				}
			}
		} catch (IOException e1) {
			System.out.println("No map file selected");
		}

		mapFileScanner = new Scanner(stringBuilder.toString());
		Map map = processMap(mapFileScanner);
		mapFileScanner.close();
		return map;
	}

	/**
	 * This function is used to process map for manipulations.
	 * @param scan object of type {@link Scanner}
	 * @return map object of type {@link Map}
	 * @throws InvalidMapException
	 */
	private Map processMap(Scanner scan) throws InvalidMapException {
		Map map = new Map();

		HashMap<String, String> mapAttributes = new HashMap<>();

		StringTokenizer token = new StringTokenizer(scan.nextLine(), "|");
		while (token.hasMoreTokens()) {
			String element = token.nextToken();
			if (element.equalsIgnoreCase("[Map]")) {
				continue;
			} else {
				String[] data = element.split("=");
				mapAttributes.put(data[0], data[1]);
			}
		}
		map.setMapData(mapAttributes);
		List<Continent> continents = processContinent(scan);
		HashMap<String, Continent> contMap = new HashMap<>();
		for (Continent continent : continents) {
			contMap.put(continent.getName(), continent);
		}
		map.setContinentMap(contMap);
		map.setContinents(continents);

		return map;
	}

	/**
	 * This function is used to process continents for manipulations.
	 * @param scan object of type {@link Scanner}
	 * @return a list of continents after processing
	 * @throws InvalidMapException
	 */
	private List<Continent> processContinent(Scanner scan) throws InvalidMapException {
		List<Continent> continents = new ArrayList<>();
		StringTokenizer token = new StringTokenizer(scan.nextLine(), "|");
		while (token.hasMoreTokens()) {
			String element = token.nextToken();
			if (element.equalsIgnoreCase("[Continents]")) {
				continue;
			} else {
				Continent continent = new Continent();
				String[] data = element.split("=");
				continent.setName(data[0]);
				continent.setValue(data[1]);
				continents.add(continent);
			}
		}

		List<Territory> territories = new ArrayList<>();
		while (scan.hasNext()) {
			String territoryData = scan.nextLine();
			territories.addAll(processTerritory(territoryData, continents));
		}

		HashMap<String, Territory> tMap = new HashMap<>();
		for (Territory t : territories) {
			tMap.put(t.getName(), t);
		}
		
		//Map adjacnet territory object to territory
		for (Territory t : territories) {
			for (String name : t.getAdjTerritories()) {
				if (tMap.containsKey(name)) {
					if (t.getAdjacentTerritories() == null) {
						t.setAdjacentTerritories(new ArrayList<Territory>());
					}
					t.getAdjacentTerritories().add(tMap.get(name));
				} else {
					throw new InvalidMapException("Territory: " + name + " not mapped with any continent.");
				}
			}
		}

		// Add the territories to their continent
		for (Continent continent : continents) {
			HashMap<String, Territory> contTMap = new HashMap<>();
			for (Territory territory : territories) {
				if (territory.getBelongToContinent().equals(continent)) {
					if (continent.getTerritories() == null) {
						continent.setTerritories(new ArrayList<>());
						contTMap.put(territory.getName(), territory);
					}
					continent.getTerritories().add(territory);
					contTMap.put(territory.getName(), territory);
				}
			}
			continent.setTerritoryMap(contTMap);
		}

		return continents;
	}

	/**
	 * This function is used to process territory for manipulations.
	 * @param territoryData
	 * @param continents
	 * @return list of territories after processing
	 * @throws InvalidMapException
	 */
	private List<Territory> processTerritory(String territoryData, List<Continent> continents)
			throws InvalidMapException {

		List<Territory> territories = new ArrayList<>();
		StringTokenizer token = new StringTokenizer(territoryData, "|");
		while (token.hasMoreTokens()) {
			String element = token.nextToken();
			if (element.equalsIgnoreCase("[Territories]")) {
				continue;
			} else {
				Territory territory = new Territory();
				List<String> adjacentTerritories = new ArrayList<>();
				String[] data = element.split(",");
				territory.setName(data[0]);
				territory.setxCoordinate(Integer.parseInt(data[1]));
				territory.setyCoordinate(Integer.parseInt(data[2]));

				for (Continent continent : continents) {
					if (continent.getName().equalsIgnoreCase(data[3])) {
						territory.setBelongToContinent(continent);
						if (territoryContinentCount.get(data[0]) == null) {
							territoryContinentCount.put(data[0], 1);
						} else {
							throw new InvalidMapException("A Territory cannot be assigned to more than one Continent.");
						}
					}
				}
				if (territoryContinentCount.get(data[0]) == null) {
					throw new InvalidMapException("A Territory should be assigned to one Continent.");
				}
				for (int i = 4; i < data.length; i++) {
					adjacentTerritories.add(data[i]);
				}
				territory.setAdjTerritories(adjacentTerritories);
				territories.add(territory);
			}
		}

		return territories;
	}
}
