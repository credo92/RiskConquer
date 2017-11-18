package com.risk.strategy;

import com.risk.entity.Player;
import com.risk.entity.Territory;
import com.risk.exception.InvalidGameMoveException;
import com.risk.map.util.MapUtil;
import com.risk.model.PlayerGamePhase;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class RandomStrategy implements PlayerBehaviorStrategy {

	public void reinforcementPhase(ObservableList<Territory> territoryList, Territory territory, TextArea gameConsole,
			Player playerPlaying) {
		int count = territoryList.size();
		Territory randomTerritory = territoryList.get(randomNumber(count));
		int armies = playerPlaying.getArmies();

		if (armies > 0) {
			randomTerritory.setArmies(territory.getArmies() + armies);
			playerPlaying.setArmies(playerPlaying.getArmies() - armies);
			MapUtil.appendTextToGameConsole(armies + ": assigned to territory " + territory.getName() + "\n",
					gameConsole);
		}
	}

	public void attackPhase(ListView<Territory> attackingTerritoryList, ListView<Territory> defendingTerritoryList,
			PlayerGamePhase gamePhase) throws InvalidGameMoveException {
		ObservableList<Territory> attackTerList = attackingTerritoryList.getItems();
		int attackTerrcount = attackTerList.size();
		Territory attackingTerritory = attackTerList.get(randomNumber(attackTerrcount));

		ObservableList<Territory> defenderList = defendingTerritoryList.getItems();
	}

	public boolean fortificationPhase(Territory selectedTerritory, Territory adjTerritory, TextArea gameConsole,
			Player playerPlaying) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean playerHasAValidAttackMove(ListView<Territory> territories, TextArea gameConsole) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @return Int randomNumber
	 */
	public int randomNumber(int count) {
		return (int) (Math.random() * count) + 1;
	}

}
