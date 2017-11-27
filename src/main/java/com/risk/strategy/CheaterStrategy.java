package com.risk.strategy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

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
 * This class is an implementation of Cheater Strategy based on Strategy
 * pattern.
 * 
 * @author Garvpreet Singh
 * @version 1.0.1
 */
public class CheaterStrategy implements PlayerBehaviorStrategy {

	ObservableList<Territory> attackTerList = null;

	/*
	 * (non-Javadoc) This method is an implementation of reinforcement phase in
	 * Cheater Strategy.
	 * 
	 * @see com.risk.strategy.PlayerBehaviorStrategy#reinforcementPhase(javafx.
	 * collections.ObservableList, com.risk.entity.Territory,
	 * javafx.scene.control.TextArea, com.risk.entity.Player)
	 */
	@Override
	public void reinforcementPhase(ObservableList<Territory> territoryList, Territory territory, TextArea gameConsole,
			Player playerPlaying) {
		for (Territory terr : territoryList) {
			terr.setArmies(terr.getArmies() * 2);
			MapUtil.appendTextToGameConsole("Armies have been doubled on territory " + terr.getName() + "\n",
					gameConsole);
		}
		attackTerList = territoryList;
		playerPlaying.setArmies(0);
	}

	/*
	 * (non-Javadoc) This method is an implementation of attack phase in Cheater
	 * Strategy.
	 * 
	 * @see
	 * com.risk.strategy.PlayerBehaviorStrategy#attackPhase(javafx.scene.control.
	 * ListView, javafx.scene.control.ListView, com.risk.model.PlayerGamePhase,
	 * javafx.scene.control.TextArea)
	 */
	@Override
	public void attackPhase(ListView<Territory> attackingTerritoryList, ListView<Territory> defendingTerritoryList,
			PlayerGamePhase gamePhase, TextArea gameConsole) throws InvalidGameMoveException {

		List<Territory> territoryWon = new ArrayList<Territory>();
		ObservableList<Territory> attackTerList = this.attackTerList;
		Iterator<Territory> terrIterator = attackTerList.iterator();
		while (terrIterator.hasNext()) {
			Territory attackingTerritory = terrIterator.next();
			List<Territory> defendingTerritories = getDefendingTerritory(attackingTerritory);
			if (defendingTerritories.size() > 0) {
				Territory defendingTerr = defendingTerritories.get(0);
				defendingTerr.setArmies(1);
				attackingTerritory.setArmies(attackingTerritory.getArmies() - 1);
				
				defendingTerr.getPlayer().getAssignedTerritory().remove(defendingTerr);
				defendingTerr.setPlayer(attackingTerritory.getPlayer());
				
				attackingTerritory.getPlayer().getAssignedTerritory().add(defendingTerr);
				
				territoryWon.add(defendingTerr);
				MapUtil.appendTextToGameConsole(defendingTerr.getName() + " has been conquered by "
						+ attackingTerritory.getPlayer().getName() + "\n", gameConsole);
			} else {
				continue;
			}
		}
		attackingTerritoryList.getItems().addAll(territoryWon);
	}

	/*
	 * (non-Javadoc) This method is an implementation of fortification phase in
	 * Cheater Strategy.
	 * 
	 * @see
	 * com.risk.strategy.PlayerBehaviorStrategy#fortificationPhase(javafx.scene.
	 * control.ListView, javafx.scene.control.ListView,
	 * javafx.scene.control.TextArea, com.risk.entity.Player)
	 */
	public boolean fortificationPhase(ListView<Territory> selectedTerritoryList, ListView<Territory> adjTerritoryList,
			TextArea gameConsole, Player playerPlaying) {

		ObservableList<Territory> selectedTerrList = selectedTerritoryList.getItems();
		Iterator<Territory> iterateTerritory = selectedTerrList.iterator();

		while (iterateTerritory.hasNext()) {
			Territory fortifyingTerritory = iterateTerritory.next();
			List<Territory> particularFortifyingTerritory = fortifyingTerritory.getAdjacentTerritories().stream()
					.filter(t -> (fortifyingTerritory.getPlayer() != t.getPlayer())).collect(Collectors.toList());

			for (Territory t : particularFortifyingTerritory) {
				t.setArmies(t.getArmies() * 2);
				MapUtil.appendTextToGameConsole("Armies have been doubled on territory " + t.getName() + "\n",
						gameConsole);
			}
		}
		return true;
	}

	@Override
	public boolean isFortificationPhaseValid(Map map, Player playerPlaying) {
		return true;
	}

	@Override
	public boolean playerHasAValidAttackMove(ListView<Territory> territories, TextArea gameConsole) {
		boolean hasAValidMove = false;
		if (attackTerList == null) {
			attackTerList = territories.getItems();
		}
		for (Territory territory : attackTerList) {
			if (getDefendingTerritory(territory).size() > 0) {
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

}
