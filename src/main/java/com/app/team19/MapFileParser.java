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
			if (token.nextToken().equalsIgnoreCase("[Map]")) {
				continue;
			} else {
				String[] data = token.nextToken().split("=");
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
		HashMap<String, String> continentDetails = new HashMap<>();

		StringTokenizer token = new StringTokenizer(scan.nextLine(), "|");
		while (token.hasMoreTokens()) {
			if (token.nextToken().equalsIgnoreCase("[Continents]")) {
				continue;
			} else {
				Continent continent = new Continent();
				String[] data = token.nextToken().split("=");
				continent.setName(data[0]);
				continent.setValue(data[1]);
				continents.add(continent);
			}
		}

		/*while (scan.hasNext()) {
			processTerritory(scan.nextLine());
		}*/

		return continents;
	}

	private Map processTerritory(String territoryData) {

		StringTokenizer token = new StringTokenizer(territoryData, "|");
		while (token.hasMoreTokens()) {
			if (token.nextToken().equalsIgnoreCase("[Territories]")) {
				continue;
			} else {
				Territory territory = new Territory();
				String[] data = token.nextToken().split(",");
				for (String s : data) {
					territory.setName(s);
				}
			}
		}

		return null;
	}
}
