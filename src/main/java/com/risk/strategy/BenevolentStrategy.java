package com.risk.strategy;

import com.risk.entity.Player;
import com.risk.entity.Territory;
import com.risk.exception.InvalidGameMoveException;
import com.risk.model.PlayerGamePhase;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class BenevolentStrategy implements PlayerBehaviorStrategy{

	@Override
	public void reinforcementPhase(ObservableList<Territory> territoryList, Territory territory, TextArea gameConsole,
			Player playerPlaying) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void attackPhase(ListView<Territory> attackingTerritoryList, ListView<Territory> defendingTerritoryList,
			PlayerGamePhase gamePhase) throws InvalidGameMoveException {
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
