package com.app.team19;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MapFileWriter {

	public void writeMapToFile(Map map, File file) {
		FileWriter fileWriter = null;
		try {
			if (map == null) {
				System.out.println("Cannot write map data to file. Null map object found");
			}
			String data = parseMapObjectToMapFileFormat(map);
			fileWriter = new FileWriter(file);
			fileWriter.write(data);
			fileWriter.close();
		} catch (IOException ex) {
			System.out.println("Cannot write map data to file. Null map object found" + ex.getMessage());
		}
	}

	private String parseMapObjectToMapFileFormat(Map map) {

		StringBuilder mapFile = new StringBuilder();
		mapFile = proessMapData(map, mapFile);
		return mapFile.toString();
	}

	private StringBuilder proessMapData(Map map, StringBuilder mapFile) {

		mapFile.append("[Map]");
		mapFile.append("\n");
		for (java.util.Map.Entry<String, String> e : map.getMapData().entrySet()) {
			mapFile.append(e.getKey() + "=" + e.getValue());
			mapFile.append("\n");
		}
		mapFile = processContinentData(map, mapFile);
		return mapFile;
	}

	private StringBuilder processContinentData(Map map, StringBuilder mapFile) {
		mapFile.append("\n");
		mapFile.append("[Continents]");
		mapFile.append("\n");
		for (Continent continent : map.getContinents()) {
			mapFile.append(continent.getName() + "=" + continent.getValue());
			mapFile.append("\n");
		}
		mapFile = processTerritoryData(map, mapFile);
		return mapFile;
	}

	private StringBuilder processTerritoryData(Map map, StringBuilder mapFile) {
		mapFile.append("\n");
		mapFile.append("[Territories]");
		mapFile.append("\n");
		for (Continent continent : map.getContinents()) {
			for (Territory territory : continent.getTerritories()) {
				mapFile.append(territory.getName() + "," + territory.getxCoordinate() + "," + territory.getyCoordinate()
						+ "," + territory.getBelongToContinent().getName());
				for (String adjTerritory : territory.getAdjTerritories()) {
					mapFile.append(",");
					mapFile.append(adjTerritory);
				}
				mapFile.append("\n");
			}
			mapFile.append("\n");
		}
		return mapFile;
	}
}
