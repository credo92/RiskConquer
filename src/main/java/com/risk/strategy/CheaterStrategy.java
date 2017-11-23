package com.risk.strategy;

import com.risk.entity.Player;
import com.risk.entity.Territory;
import com.risk.exception.InvalidGameMoveException;
import com.risk.map.util.MapUtil;
import com.risk.model.PlayerGamePhase;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class CheaterStrategy implements PlayerBehaviorStrategy {

	@Override
	public void reinforcementPhase(ObservableList<Territory> territoryList, Territory territory, TextArea gameConsole,
			Player playerPlaying) {		
		for (Territory terr : territoryList) {
			terr.setArmies(terr.getArmies() * 2);
			MapUtil.appendTextToGameConsole("Armies have been doubled on territory " + terr.getName() + "\n",
					gameConsole);
		}		
		playerPlaying.setArmies(0);
	}

	@Override
	public void attackPhase(ListView<Territory> attackingTerritoryList, ListView<Territory> defendingTerritoryList,
			PlayerGamePhase gamePhase, TextArea gameConsole) throws InvalidGameMoveException {
		
		
	}


	@Override
	public boolean playerHasAValidAttackMove(ListView<Territory> territories, TextArea gameConsole) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean fortificationPhase(ListView<Territory> selectedTerritory, ListView<Territory> adjTerritory,
			TextArea gameConsole, Player playerPlaying) {
		// TODO Auto-generated method stub
		return false;
	}

}
