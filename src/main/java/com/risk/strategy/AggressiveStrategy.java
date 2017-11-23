package com.risk.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.risk.entity.Player;
import com.risk.entity.Territory;
import com.risk.exception.InvalidGameMoveException;
import com.risk.model.PlayerGamePhase;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class AggressiveStrategy implements PlayerBehaviorStrategy {

	public boolean getTerritoryOwnedByAnotherPlayer(Territory territory) {
		List<Territory> listWithValidAdjacentTerriroty = territory.getAdjacentTerritories().stream()
				.filter(t -> !(territory.getPlayer().equals(t.getPlayer()))).collect(Collectors.toList());
		if (listWithValidAdjacentTerriroty.size() > 0) {
			return true;
		}
		return false;
	}

	public Territory validTerritory(Iterator<Territory> listIterator, List<Territory> visitedTerritory) {
		if (listIterator.hasNext()) {
			Territory t = listIterator.next();
			if (!visitedTerritory.contains(t)) {
				if (getTerritoryOwnedByAnotherPlayer(t)) {
					return t;
				} else {
					validTerritory(listIterator, visitedTerritory);
				}
			} else {
				validTerritory(listIterator, visitedTerritory);
			}
		}
		return null;
	}

	@Override
	public void reinforcementPhase(ObservableList<Territory> territoryList, Territory territory, TextArea gameConsole,
			Player playerPlaying) {

		List<Territory> list = new ArrayList<>(territoryList);
		List<Territory> visitedTerritory = new ArrayList<>();

		Collections.sort(list, new Comparator<Territory>() {
			@Override
			public int compare(Territory o1, Territory o2) {
				return Integer.valueOf(o2.getArmies()).compareTo(Integer.valueOf(o1.getArmies()));
			}
		});
		Iterator<Territory> listIterator = list.iterator();
		Territory t = validTerritory(listIterator, visitedTerritory);
		
		if(t != null) {
			t.setArmies(playerPlaying.getArmies());
		}else {
			list.get(0).setArmies(playerPlaying.getArmies());
		}

	}

	public void attackPhase(ListView<Territory> attackingTerritoryList, ListView<Territory> defendingTerritoryList,
			PlayerGamePhase gamePhase, TextArea gameConsole) throws InvalidGameMoveException {
		// TODO Auto-generated method stub

	}

	public boolean fortificationPhase(ListView<Territory> selectedTerritory, ListView<Territory> adjTerritory,
			TextArea gameConsole, Player playerPlaying) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean playerHasAValidAttackMove(ListView<Territory> territories, TextArea gameConsole) {
		// TODO Auto-generated method stub
		return false;
	}

}
