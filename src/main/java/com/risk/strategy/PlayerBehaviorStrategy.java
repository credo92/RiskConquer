package com.risk.strategy;

import com.risk.controller.DiceRollController;
import com.risk.entity.Player;
import com.risk.entity.Territory;
import com.risk.exception.InvalidGameMoveException;

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
	void reinforcementPhase(Territory territory, TextArea gameConsole, Player playerPlaying);

	/**
	 * @param attackingTerritory
	 * @param defendingTerritory
	 */
	void attackPhase(Territory attackingTerritory, Territory defendingTerritory, DiceRollController diceController)
			throws InvalidGameMoveException;

	/**
	 * @param selectedTerritory
	 * @param adjTerritory
	 * @param gameConsole
	 */
	boolean fortificationPhase(Territory selectedTerritory, Territory adjTerritory, TextArea gameConsole,
			Player playerPlaying);

	boolean playerHasAValidAttackMove(ListView<Territory> territories, TextArea gameConsole);
}
