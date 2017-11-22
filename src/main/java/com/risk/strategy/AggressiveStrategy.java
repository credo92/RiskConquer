package com.risk.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.risk.entity.Player;
import com.risk.entity.Territory;
import com.risk.exception.InvalidGameMoveException;
import com.risk.model.PlayerGamePhase;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;


public class AggressiveStrategy implements PlayerBehaviorStrategy{
	

	@Override
	public void reinforcementPhase(ObservableList<Territory> territoryList, Territory territory, TextArea gameConsole,
			Player playerPlaying) {
		List<Territory> list = new ArrayList<>(territoryList);
		
		Collections.sort(list, new Comparator<Territory>() {
			@Override
			public int compare(Territory o1, Territory o2) {
				return Integer.valueOf(o2.getArmies()).compareTo(Integer.valueOf(o1.getArmies()));
			}		
		});
		
		list.get(0);
		// TODO Auto-generated method stub
		
	}

	public void attackPhase(ListView<Territory> attackingTerritoryList, ListView<Territory> defendingTerritoryList,
			PlayerGamePhase gamePhase) throws InvalidGameMoveException {
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
