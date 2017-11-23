package com.risk.strategy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class AggressiveStrategy implements PlayerBehaviorStrategy {
	
	private Territory territory = null;

	public boolean getTerritoryOwnedByAnotherPlayer(Territory territory) {
		List<Territory> listWithValidAdjacentTerriroty = territory.getAdjacentTerritories().stream()
				.filter(t -> !(territory.getPlayer().equals(t.getPlayer()))).collect(Collectors.toList());
		if (listWithValidAdjacentTerriroty.size() > 0) {
			return true;
		}
		return false;
	}

	public Territory validTerritory(Iterator<Territory> listIterator, List<Territory> visitedTerritory) {
		if (listIterator.hasNext()) {
			Territory t = listIterator.next();
			if (!visitedTerritory.contains(t)) {
				if (getTerritoryOwnedByAnotherPlayer(t)) {
					return t;
				} else {
					validTerritory(listIterator, visitedTerritory);
				}
			} else {
				validTerritory(listIterator, visitedTerritory);
			}
		}
		return null;
	}

	@Override
	public void reinforcementPhase(ObservableList<Territory> territoryList, Territory territory, TextArea gameConsole,
			Player playerPlaying) {

		List<Territory> list = new ArrayList<>(territoryList);
		List<Territory> visitedTerritory = new ArrayList<>();

		Collections.sort(list, new Comparator<Territory>() {
			@Override
			public int compare(Territory o1, Territory o2) {
				return Integer.valueOf(o2.getArmies()).compareTo(Integer.valueOf(o1.getArmies()));
			}
		});
//		Iterator<Territory> listIterator = list.iterator();
//		Territory t = validTerritory(listIterator, visitedTerritory);
//		
//		if(t != null) {
//			t.setArmies(playerPlaying.getArmies());
//		}else {
//			list.get(0).setArmies(playerPlaying.getArmies());
//		}
		territory = list.get(0);
		list.get(0).setArmies(playerPlaying.getArmies());

	}

	public void attackPhase(ListView<Territory> attackingTerritoryList, ListView<Territory> defendingTerritoryList,
			PlayerGamePhase gamePhase, TextArea gameConsole) throws InvalidGameMoveException {
		// TODO Auto-generated method stub
//		ObservableList<Territory> attackTerList = attackingTerritoryList.getItems();
//		Iterator<Territory> terrIterator = attackTerList.iterator();
//		while (terrIterator.hasNext()) {
//			Territory attackingTerritory = terrIterator.next();
//			List<Territory> defendingTerritories = getDefendingTerritory(attackingTerritory);
//			if (defendingTerritories.size() == 0) {
//				continue;
//			}
//			attack(attackingTerritory, defendingTerritories.get(0), gamePhase, gameConsole);
//			break;
//		}
//		
//		System.out.println("NO attacking territory found");
		
		
		List<Territory> defendingTerritories = getDefendingTerritory(territory);
		Iterator<Territory> defendingTerritoriesIterator = defendingTerritories.iterator();
		while(defendingTerritoriesIterator.hasNext()) {
			attack(territory, defendingTerritories.get(0), gamePhase, gameConsole);	
		}

	}
	
	private void attack(Territory attacking, Territory defending, PlayerGamePhase gamePhase, TextArea gameConsole) {
		DiceModel diceModel = new DiceModel(attacking, defending);
		diceModel.addObserver(gamePhase);

		DiceRollController diceController = new DiceRollController(diceModel, this, gameConsole);

		final Stage newMapStage = new Stage();
		newMapStage.setTitle("Attack Window");

		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("DiceView.fxml"));
		loader.setController(diceController);
		Parent root = null;
		try {
			root = (Parent) loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * Scene scene = new Scene(root); newMapStage.setScene(scene);
		 * newMapStage.show();
		 */
	}

	public boolean fortificationPhase(ListView<Territory> selectedTerritoryList, ListView<Territory> adjTerritoryList,
			TextArea gameConsole, Player playerPlaying) {
		boolean isFortificationDone = false;
		ObservableList<Territory> selectedTerritory = selectedTerritoryList.getItems();
		boolean fortifiyingStarted = true;
		Iterator<Territory> iterateTerritory = selectedTerritory.iterator();
		while (fortifiyingStarted && iterateTerritory.hasNext()) {
			Territory fortifyingTerritory = iterateTerritory.next();
			if (fortifyingTerritory.getArmies() > 1) {
				List<Territory> adjTerritory = fortifyingTerritory.getAdjacentTerritories().stream()
						.filter(t -> fortifyingTerritory.getPlayer().equals(t.getPlayer()))
						.collect(Collectors.toList());

				if (adjTerritory.size() > 0) {
					Territory terrToBeFortified = adjTerritory.get(0);
					int armies = fortifyingTerritory.getArmies() - 1;
					terrToBeFortified.setArmies(terrToBeFortified.getArmies() + armies);
					MapUtil.appendTextToGameConsole(
							armies + ": fortified to territory " + terrToBeFortified.getName() + "\n", gameConsole);
					fortifyingTerritory.setArmies(1);
					isFortificationDone = true;
					break;
				} else {
					continue;
				}

			}
		}
		return isFortificationDone;
	}


}
