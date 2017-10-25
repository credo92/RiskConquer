package com.risk.card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.risk.entity.*;

/**
 * @author Vipul 
 * This class is used to assign cards to players.
 * @version 1.0.1
 */
public class Cards {
	Map cardWithTerritories = new HashMap();
	ArrayList territoryList = new ArrayList();
	Continent continent;

	public void populateTerritory() {
		territoryList.clear();
		if (continent != null && continent.getTerritories() != null) {
			for (Territory territory : continent.getTerritories()) {
				territoryList.add(territory);
			}
		}
	}

	public Map cardWithTerritoryMap() {
		populateTerritory();
		List infantryTerritoryList = new ArrayList();
		List cavalryTerritoryList = new ArrayList();
		List artilleryTerritoryList = new ArrayList();

		for(int i=0;i<territoryList.size();i++) // i less than size of Territories List 
		{
			infantryTerritoryList.add(territoryList.get(i)); // add (territory) for each territory in Territory List
			cavalryTerritoryList.add(territoryList.get(i));
			artilleryTerritoryList.add(territoryList.get(i));
		}

		cardWithTerritories.put("infantryCard", infantryTerritoryList);
		cardWithTerritories.put("cavalryCard", cavalryTerritoryList);
		cardWithTerritories.put("artilleryCard", artilleryTerritoryList);

		return cardWithTerritories;	
	}
}
