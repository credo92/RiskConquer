package com.risk.strategy;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.risk.controller.DiceRollController;
import com.risk.entity.Player;
import com.risk.entity.Territory;
import com.risk.exception.InvalidGameMoveException;
import com.risk.model.DiceModel;
import com.risk.model.PlayerGamePhase;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class AggressiveStrategy implements PlayerBehaviorStrategy {

	private Territory territory;

	@Override
	public void reinforcementPhase(ObservableList<Territory> territoryList, Territory territory, TextArea gameConsole,
			Player playerPlaying) {

		List<Territory> sortedList = getMaximumAdjacentAndArmy(territoryList);
		if (!sortedList.isEmpty()) {
			territory = sortedList.get(0);
			territory.setArmies(territory.getArmies() + playerPlaying.getArmies());
			playerPlaying.setArmies(0);
		}

	}

	public boolean playerHasAValidAttackMove(ListView<Territory> territories, TextArea gameConsole) {

		territory = checkIfTerritoryNull(territories);
		List<Territory> defendingTerritory = getDefendingTerritory(territory);
		if (defendingTerritory.size() > 0 && territory.getArmies() > 1) {
			return true;
		}
		return false;
	}

	public boolean fortificationPhase(ListView<Territory> selectedTerritoryList, ListView<Territory> adjTerritoryList,
			TextArea gameConsole, Player playerPlaying) {

		List<Territory> sortedList = getMaximumAdjacentAndArmy(selectedTerritoryList.getItems());
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
					fortifyingtTerritory.get(0)
							.setArmies(fortifyingtTerritory.get(0).getArmies() + territory.getArmies() - 1);
					territory.setArmies(1);
					return true;
				}
			}
		}
		return false;
	}

	private void attack(Territory attacking, Territory defending, PlayerGamePhase gamePhase, TextArea gameConsole) {
		DiceModel diceModel = new DiceModel(attacking, defending);
		diceModel.addObserver(gamePhase);

		DiceRollController diceController = new DiceRollController(diceModel, this, gameConsole);
		diceController.autoStartDiceRollController();
	}

	@Override
	public void attackPhase(ListView<Territory> attackingTerritoryList, ListView<Territory> defendingTerritoryList,
			PlayerGamePhase gamePhase, TextArea gameConsole) throws InvalidGameMoveException {

		territory = checkIfTerritoryNull(attackingTerritoryList);
		List<Territory> defendingTerritory = getDefendingTerritory(territory);
		Iterator<Territory> defendingTerritoryIterator = defendingTerritory.iterator();
		while (defendingTerritoryIterator.hasNext()) {
			if (territory.getArmies() > 1) {
				attack(territory, defendingTerritoryIterator.next(), gamePhase, gameConsole);
				break;
			}

		}

	}

	public Territory checkIfTerritoryNull(ListView<Territory> attackingTerritoryList) {
		if (territory == null || (territory.getArmies() <= 1 || getDefendingTerritory(territory).size() == 0)) {
			List<Territory> sortedList = getMaximumAdjacentAndArmy(attackingTerritoryList.getItems());
			for (Territory t : sortedList) {
				if (t.getArmies() > 1) {
					territory = t;
					break;
				}
			}
		}
		return territory;

	}

	public List<Territory> getTerritoryOwnedByCurrentPlayer(Territory territory) {
		List<Territory> listWithValidAdjacentTerriroty = territory.getAdjacentTerritories().stream()
				.filter(t -> (territory.getPlayer().equals(t.getPlayer()))).collect(Collectors.toList());
		return listWithValidAdjacentTerriroty;
	}

	public List<Territory> getMaximumAdjacentAndArmy(List<Territory> list) {
		Collections.sort(list, new Comparator<Territory>() {
			@Override
			public int compare(Territory o1, Territory o2) {
				return Integer.valueOf(getDefendingTerritory(o2).size()).compareTo(getDefendingTerritory(o1).size());
			}
		});

		return list;
	}

}
