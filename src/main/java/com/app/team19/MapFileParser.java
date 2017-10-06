package com.app.team19;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import javafx.stage.FileChooser;

public class MapFileParser {

	public Map parseAndReadMapFile() {

		File file = showFileChooser();
		Map map = convertMapFileToMapObject(file);

		return map;
	}

	private File showFileChooser() {
		FileChooser fileChooser = new FileChooser();

		FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Map files (*.map)", "*.map");
		fileChooser.getExtensionFilters().add(extensionFilter);
		File file = fileChooser.showOpenDialog(null);

		return file;
	}

	private Map convertMapFileToMapObject(final File file) {
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
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		mapFileScanner = new Scanner(stringBuilder.toString());
		Map map = processMap(mapFileScanner);
		/*
		 * map = processContinent(mapFileScanner.nextLine(), map); while
		 * (mapFileScanner.hasNextLine()) { map =
		 * processTerritory(mapFileScanner.nextLine(), map); }
		 */
		mapFileScanner.close();
		return map;
	}

	private Map processMap(Scanner scan) {
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
		map.setContinents(continents);

		return map;
	}

	private List<Continent> processContinent(Scanner scan) {
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

		for (Territory t : territories) {
			for (String name : t.getAdjTerritories()) {
				if (tMap.containsKey(name)) {
					if (t.getAdjacentTerritories() == null) {
						t.setAdjacentTerritories(new ArrayList<Territory>());
					}
					t.getAdjacentTerritories().add(tMap.get(name));
				} else {
					System.out.println("Unkown territory found.");
				}
			}
		}

		// Add the territories to their continent
		for(Continent continent : continents) {
			for (Territory territory : territories) {
				if (territory.getBelongToContinent().equals(continent)) {
					if (continent.getTerritories() == null) {
						continent.setTerritories(new ArrayList<>());
					}
					continent.getTerritories().add(territory);
				}
			}
		}
		return continents;
	}

	private List<Territory> processTerritory(String territoryData, List<Continent> continents) {

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
				int continentCount = 0;
				for (Continent continent : continents) {
					if (continent.getName().equalsIgnoreCase(data[3])) {
						continentCount++;
						territory.setBelongToContinent(continent);
					}
				}
				if (continentCount > 1) {
					System.out.println("Error in map structure. A continent can be assigned to more than 1 continent.");
				}
				if (continentCount == 0) {
					System.out.println("Error in map structure. A country should be assigned to a continent.");
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
