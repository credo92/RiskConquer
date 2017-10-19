package com.risk.map.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.risk.entity.Continent;
import com.risk.entity.Map;
import com.risk.entity.Territory;
import com.risk.exception.InvalidMapException;

/**
 * @author Garvpreet Singh
 * This class is used to handle writing map objects into map file.
 * @version 1.0.0
 *
 */
public class MapFileWriter {
	
	/**
	 * This method is use to write map object to map file.
	 * @param map of type {@link Map}
	 * @param file of type {@link File}	 
	 */
	public void writeMapToFile(Map map, File file) {
		FileWriter fileWriter = null;
		try {
			if (map == null) {
				System.out.println("Cannot write map data to file. Null map object found");
			}
			String data = parseMapObjectToMapFileFormat(map);
			fileWriter = new FileWriter(file, false);
			fileWriter.write(data);
			fileWriter.close();
		} catch (IOException ex) {
			System.out.println("Cannot write map data to file. Null map object found" + ex.getMessage());
		}
	}
	
	/**
	 * This method is use to parse map object to map file format.
	 * @param map of type {@link Map}
	 * @param file of type {@link File}	 
	 * @return String map object in string.
	 */
	private String parseMapObjectToMapFileFormat(Map map) {
		StringBuilder mapFile = new StringBuilder();
		mapFile = proessMapData(map, mapFile);
		return mapFile.toString();
	}
	
	/**
	 * This method is use to process map object.
	 * @param map of type {@link Map}
	 * @param mapFile of type {@link StringBuilder}	 
	 * @return {@link StringBuilder} mapFile
	 */
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
	
	/**
	 * This method is use to process continent data.
	 * @param map of type {@link Map}
	 * @param mapFile of type {@link StringBuilder}	 
	 * @return {@link StringBuilder} mapFile
	 */
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
	
	/**
	 * This method is use to process territory data.
	 * @param map of type {@link Map}
	 * @param mapFile of type {@link StringBuilder}	 
	 * @return {@link StringBuilder} mapFile
	 */
	private StringBuilder processTerritoryData(Map map, StringBuilder mapFile) {
		mapFile.append("\n");
		mapFile.append("[Territories]");
		mapFile.append("\n");
		for (Continent continent : map.getContinents()) {
			List<Territory> territories = continent.getTerritories();
			if (territories != null) {
				for (Territory territory : continent.getTerritories()) {
					mapFile.append(territory.getName() + "," + territory.getxCoordinate() + ","
							+ territory.getyCoordinate() + "," + territory.getBelongToContinent().getName());
					for (Territory adjTerritory : territory.getAdjacentTerritories()) {
						mapFile.append(",");
						mapFile.append(adjTerritory.getName());
					}
					mapFile.append("\n");
				}
				mapFile.append("\n");
			} else {
				MapUtil.infoBox("Add a territory to a contient", "Error", "Error");
			}
		}
		return mapFile;
	}
}
