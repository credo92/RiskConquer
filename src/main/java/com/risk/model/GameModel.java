package com.risk.model;

import java.util.ArrayList;
import java.util.List;

import com.risk.entity.Continent;
import com.risk.entity.Map;
import com.risk.entity.Player;
import com.risk.entity.Territory;
import com.risk.map.util.MapUtil;

import javafx.scene.control.TextArea;

/**
 * This class is used to represent modeling of Game.
 * 
 * @author rahul
 * @version 1.0.0.
 *
 */
public class GameModel {

	/**
	 * Assign territory to player
	 * 
	 * @param map
	 *            map object
	 * @param players
	 *            players list
	 * @param textAres
	 *            game console
	 */
	public void assignTerritoryToPlayer(Map map, List<Player> players, TextArea textAres) {

		List<Territory> allterritories = new ArrayList<>();

		if (map.getContinents() != null) {
			for (Continent continent : map.getContinents()) {
				if (continent != null && continent.getTerritories() != null) {
					for (Territory territory : continent.getTerritories()) {
						allterritories.add(territory);
					}
				}
			}
		}
		int count = 0;
		int totalTerritory = allterritories.size();
		while (count < totalTerritory) {
			for (Player player : players) {
				for (Territory territory : allterritories) {
					if (territory.getPlayer() == null) {
						count++;
						territory.setPlayer(player);
						territory.setArmies(territory.getArmies() + 1);
						player.setArmies(player.getArmies() - 1);
						player.getAssignedTerritory().add(territory);
						MapUtil.appendTextToGameConsole(
								territory.getName() + " assigned to " + player.getName() + " ! \n", textAres);
						break;
					}
					continue;
				}
			}
		}
	}
}
