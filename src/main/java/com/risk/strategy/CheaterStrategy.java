package com.risk.strategy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.risk.controller.DiceRollController;
import com.risk.entity.Player;
import com.risk.entity.Territory;
import com.risk.exception.InvalidGameMoveException;
import com.risk.map.util.MapUtil;
import com.risk.model.DiceModel;
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
		
		List<Territory> territoryWon = new ArrayList<Territory>();
		ObservableList<Territory> attackTerList = attackingTerritoryList.getItems();
		Iterator<Territory> terrIterator = attackTerList.iterator();
		while (terrIterator.hasNext()) {
			Territory attackingTerritory = terrIterator.next();
			List<Territory> defendingTerritories = getDefendingTerritory(attackingTerritory);
			
			for (Territory defendingTerr : defendingTerritories) {
				defendingTerr.setPlayer(attackingTerritory.getPlayer());
				defendingTerr.setArmies(1);
				attackingTerritory.setArmies(attackingTerritory.getArmies()-1);
				attackingTerritory.getPlayer().getAssignedTerritory().add(defendingTerr);
				territoryWon.add(defendingTerr);
				MapUtil.appendTextToGameConsole(defendingTerr.getName() + " has been conquered by " + attackingTerritory.getPlayer().getName() + "\n",
						gameConsole);
			}
		}
		attackingTerritoryList.getItems().addAll(territoryWon);
	}		
	
	/*private void attack(Territory attacking, Territory defending, PlayerGamePhase gamePhase, TextArea gameConsole) {
		MapUtil.appendTextToGameConsole("Attack Cheater started!++++++++++++++++++++++++++++++++++++++++++ \n", gameConsole);
		DiceModel diceModel = new DiceModel(attacking, defending);
		diceModel.addObserver(gamePhase);

		DiceRollController diceController = new DiceRollController(diceModel, this, gameConsole);
		diceController.cheaterAttack();
	}*/
	
	public boolean fortificationPhase(ListView<Territory> selectedTerritoryList, ListView<Territory> adjTerritoryList,
			TextArea gameConsole, Player playerPlaying) {
		
		boolean isFortificationDone = false;
		
		ObservableList<Territory> selectedTerrList = selectedTerritoryList.getItems();		
		Iterator<Territory> iterateTerritory = selectedTerrList.iterator();
		
		while (iterateTerritory.hasNext()) {
			Territory fortifyingTerritory = iterateTerritory.next();
			List<Territory> particularFortifyingTerritory = fortifyingTerritory.getAdjacentTerritories().stream()
					.filter(t -> (fortifyingTerritory.getPlayer() != t.getPlayer()))
					.collect(Collectors.toList());
			
			for (Territory t : particularFortifyingTerritory) {
				t.setArmies(t.getArmies()*2);
				MapUtil.appendTextToGameConsole("Armies have been doubled on territory " + t.getName() + "\n", gameConsole);
				isFortificationDone = true;
			}
		}
		return isFortificationDone;
	}

	@Override
	public boolean playerHasAValidAttackMove(ListView<Territory> territories, TextArea gameConsole) {
		return true;
	}

}
