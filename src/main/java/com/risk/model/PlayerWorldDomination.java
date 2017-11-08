package com.risk.model;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Observable;

import com.risk.entity.Continent;
import com.risk.entity.Map;
import com.risk.entity.Player;
import com.risk.entity.Territory;

/**
 * @author rahul
 * @version 1.0.0
 */
public class PlayerWorldDomination extends Observable {
	
	/**
	 * Populate World Domination Data according to playerTerritoryCount
	 * 
	 * @param map
	 *            map object
	 * @return playerTerPercent.
	 */
	public HashMap<Player, Double> populateWorldDominationData(Map map) {

		HashMap<Player, Double> playerTerritoryCount = new HashMap<>();
		Double territoryCount = 0.0;
		for (Continent cont : map.getContinents()) {
			for (Territory ter : cont.getTerritories()) {
				territoryCount++;
				Player player = ter.getPlayer();
				if(playerTerritoryCount.containsKey(player)) {
					playerTerritoryCount.put(player, playerTerritoryCount.get(player)+1);
				} else {
					playerTerritoryCount.put(player, Double.valueOf("1"));
				}
			}
		}

		HashMap<Player, Double> playerTerPercent = new HashMap<>();
		for(Entry<Player, Double> entry : playerTerritoryCount.entrySet()) {
			playerTerPercent.put(entry.getKey(), (entry.getValue()/territoryCount * 100));
		}
		return playerTerPercent;
	}

}
