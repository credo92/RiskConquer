package com.risk.strategy;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import com.risk.entity.Continent;
import com.risk.entity.Map;
import com.risk.entity.Player;
import com.risk.entity.Territory;
import com.risk.exception.InvalidGameMoveException;
import com.risk.map.util.MapUtil;
import com.risk.model.PlayerGamePhase;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

/**
 * @author rahul
 *
 */
public interface PlayerBehaviorStrategy extends Serializable {
	/**
	 * @param territoryList observable list.
	 * @param territory Territory
	 * @param gameConsole the Game console.
	 * @param playerPlaying Player.
	 */
	void reinforcementPhase(ObservableList<Territory> territoryList, Territory territory, TextArea gameConsole,
			Player playerPlaying);

	/**
	 * This method is to initiate attack phase.
	 * @param attackingTerritoryList list view of territory.
	 * @param defendingTerritoryList list view of territory.
	 * @param gamePhase PlayerGamePhase
	 * @param gameConsole The game console.
	 * @throws InvalidGameMoveException InvalidGameMoveException.
	 */
	void attackPhase(ListView<Territory> attackingTerritoryList, ListView<Territory> defendingTerritoryList,
			PlayerGamePhase gamePhase, TextArea gameConsole) throws InvalidGameMoveException;

	/**
	 * @param selectedTerritory list view of selected territory list.
	 * @param adjTerritory list view of adjacent territory list.
	 * @param gameConsole The game console.
	 * @param playerPlaying Player.
	 * @return validity endOfForiticatipon phase.
	 */
	boolean fortificationPhase(ListView<Territory> selectedTerritory, ListView<Territory> adjTerritory,
			TextArea gameConsole, Player playerPlaying);
	

	default public List<Territory> getDefendingTerritory(Territory attackingTerritory) {
		List<Territory> defendingTerritories = attackingTerritory.getAdjacentTerritories().stream()
				.filter(t -> (attackingTerritory.getPlayer() != t.getPlayer())).collect(Collectors.toList());

		return defendingTerritories;

	}

	default boolean playerHasAValidAttackMove(ListView<Territory> territories, TextArea gameConsole) {
		boolean hasAValidMove = false;
		for (Territory territory : territories.getItems()) {
			if (territory.getArmies() > 1 && getDefendingTerritory(territory).size() > 0) {
				hasAValidMove = true;
			}
		}

		if (!hasAValidMove) {
			MapUtil.appendTextToGameConsole("No valid attack move avialble move to Fortification phase.\n",
					gameConsole);
			MapUtil.appendTextToGameConsole("===Attack phase ended! === \n", gameConsole);
			return hasAValidMove;
		}
		return hasAValidMove;
	}
	
	default boolean isFortificationPhaseValid(Map map, Player playerPlaying) {
		boolean isFortificationAvaialble = false;
		outer: for (Continent continent : map.getContinents()) {
			for (Territory territory : continent.getTerritories()) {
				if (territory.getPlayer().equals(playerPlaying)) {
					if (territory.getArmies() > 1) {
						for (Territory adjterritory : territory.getAdjacentTerritories()) {
							if (adjterritory.getPlayer().equals(playerPlaying)) {
								isFortificationAvaialble = true;
								break outer;
							}
						}
					}
				}
			}
		}
		
		return isFortificationAvaialble;
	}

}
