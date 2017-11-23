package com.risk.strategy;

import java.io.IOException;
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

public class RandomStrategy implements PlayerBehaviorStrategy {

	public void reinforcementPhase(ObservableList<Territory> territoryList, Territory territory, TextArea gameConsole,
			Player playerPlaying) {
		int count = territoryList.size();
		Territory randomTerritory = territoryList.get(randomNumber(count - 1));
		int armies = playerPlaying.getArmies();

		if (armies > 0) {
			randomTerritory.setArmies(randomTerritory.getArmies() + armies);
			playerPlaying.setArmies(playerPlaying.getArmies() - armies);
			MapUtil.appendTextToGameConsole(armies + ": assigned to territory " + randomTerritory.getName() + "\n",
					gameConsole);
		}

	}

	public void attackPhase(ListView<Territory> attackingTerritoryList, ListView<Territory> defendingTerritoryList,
			PlayerGamePhase gamePhase, TextArea gameConsole) throws InvalidGameMoveException {

		ObservableList<Territory> attackTerList = attackingTerritoryList.getItems();
		Iterator<Territory> terrIterator = attackTerList.iterator();
		while (terrIterator.hasNext()) {
			Territory attackingTerritory = terrIterator.next();
			List<Territory> defendingTerritories = getDefendingTerritory(attackingTerritory);
			if (defendingTerritories.size() == 0) {
				continue;
			}
			attack(attackingTerritory, defendingTerritories.get(0), gamePhase, gameConsole);
			break;
		}
		
		System.out.println("NO attacking territory found");
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
					Territory terrToBeFortified = adjTerritory.get(randomNumber(adjTerritory.size() - 1));
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

	/**
	 * @return Int randomNumber
	 */
	public int randomNumber(int count) {
		return (int) (Math.random() * count) + 0;
	}

}
