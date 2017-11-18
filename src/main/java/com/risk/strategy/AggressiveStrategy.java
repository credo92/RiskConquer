package com.risk.strategy;

import com.risk.controller.DiceRollController;
import com.risk.entity.Player;
import com.risk.entity.Territory;
import com.risk.exception.InvalidGameMoveException;

import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class AggressiveStrategy implements PlayerBehaviorStrategy{

	@Override
	public void reinforcementPhase(Territory territory, TextArea gameConsole, Player playerPlaying) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void attackPhase(Territory attackingTerritory, Territory defendingTerritory,
			DiceRollController diceController) throws InvalidGameMoveException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean fortificationPhase(Territory selectedTerritory, Territory adjTerritory, TextArea gameConsole,
			Player playerPlaying) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean playerHasAValidAttackMove(ListView<Territory> territories, TextArea gameConsole) {
		// TODO Auto-generated method stub
		return false;
	}

}
