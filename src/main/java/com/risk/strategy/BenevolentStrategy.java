package com.risk.strategy;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.risk.entity.Player;
import com.risk.entity.Territory;
import com.risk.exception.InvalidGameMoveException;
import com.risk.model.PlayerGamePhase;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class BenevolentStrategy implements PlayerBehaviorStrategy{

	public void reinforcementPhase(ObservableList<Territory> territoryList, Territory territory, TextArea gameConsole,
			Player playerPlaying) {
		// TODO Auto-generated method stub
		List<Territory> sortedList = getMinimumArmyFromTerritory(territoryList);
		territory = sortedList.get(0);
		territory.setArmies(territory.getArmies() + playerPlaying.getArmies());
		playerPlaying.setArmies(0);
		
	}

	public void attackPhase(ListView<Territory> attackingTerritoryList, ListView<Territory> defendingTerritoryList,
			PlayerGamePhase gamePhase, TextArea gameConsole) throws InvalidGameMoveException {
		// No attack move for this player
		
	}

	public boolean fortificationPhase(ListView<Territory> selectedTerritoryList, ListView<Territory> adjTerritory,
			TextArea gameConsole, Player playerPlaying) {
		// TODO Auto-generated method stub
		List<Territory> sortedList = getMinimumArmyFromTerritory(selectedTerritoryList.getItems());
		for (Territory territory : sortedList) {
			if (territory.getArmies() > 1) {
				List<Territory> fortifyingtTerritory = getTerritoryOwnedByCurrentPlayer(territory);
				if (fortifyingtTerritory.size() != 0) {
					Collections.sort(fortifyingtTerritory, new Comparator<Territory>() {
						@Override
						public int compare(Territory o1, Territory o2) {
							return Integer.valueOf(o2.getArmies()).compareTo(Integer.valueOf(o1.getArmies()));
						}
					});
					territory.setArmies(territory.getArmies() + fortifyingtTerritory.get(0).getArmies() - 1);
					fortifyingtTerritory.get(0).setArmies(1);
					return true;
				}
			}
		}
		return false;
	}

	public boolean playerHasAValidAttackMove(ListView<Territory> territories, TextArea gameConsole) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public List<Territory> getTerritoryOwnedByCurrentPlayer(Territory territory) {
		List<Territory> listWithValidAdjacentTerriroty = territory.getAdjacentTerritories().stream()
				.filter(t -> (territory.getPlayer().equals(t.getPlayer()))).collect(Collectors.toList());
		return listWithValidAdjacentTerriroty;
	}

	public List<Territory> getMinimumArmyFromTerritory(List<Territory> list) {
		Collections.sort(list, new Comparator<Territory>() {
			@Override
			public int compare(Territory o1, Territory o2) {
				return Integer.valueOf(getDefendingTerritory(o2).size()).compareTo(getDefendingTerritory(o1).size());
			}
		});

		return list;
	}
}
