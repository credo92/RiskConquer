package com.risk.strategy;

import com.risk.entity.Player;
import com.risk.entity.Territory;
import com.risk.exception.InvalidGameMoveException;
import com.risk.model.PlayerGamePhase;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

/**
 * @author rahul
 *
 */
public interface PlayerBehaviorStrategy {
	/**
	 * @param territory
	 * @param gameConsole
	 */
	void reinforcementPhase(ObservableList<Territory> territoryList, Territory territory, TextArea gameConsole, Player playerPlaying);

	/**
	 * @param attackingTerritory
	 * @param defendingTerritory
	 */
	void attackPhase(ListView<Territory> attackingTerritoryList, ListView<Territory> defendingTerritoryList, PlayerGamePhase gamePhase)
			throws InvalidGameMoveException;

	/**
	 * @param selectedTerritory
	 * @param adjTerritory
	 * @param gameConsole
	 */
	boolean fortificationPhase(ListView<Territory> selectedTerritory, ListView<Territory> adjTerritory, TextArea gameConsole,
			Player playerPlaying);

	boolean playerHasAValidAttackMove(ListView<Territory> territories, TextArea gameConsole);
}
