package com.risk.strategy;

import com.risk.controller.DiceRollController;
import com.risk.entity.Player;
import com.risk.entity.Territory;
import com.risk.exception.InvalidGameMoveException;

import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class RandomStrategy implements PlayerBehaviorStrategy {

	public void reinforcementPhase(Territory territory, TextArea gameConsole, Player playerPlaying) {
		// TODO Auto-generated method stub
		
	}

	public void attackPhase(Territory attackingTerritory, Territory defendingTerritory,
			DiceRollController diceController) throws InvalidGameMoveException {
		// TODO Auto-generated method stub
		
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

	
}
