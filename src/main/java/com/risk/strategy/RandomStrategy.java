package com.risk.strategy;

import java.io.IOException;
import java.util.ArrayList;
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
import javafx.scene.Scene;
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
			PlayerGamePhase gamePhase) throws InvalidGameMoveException {

		int numberOfAttack = randomNumber(5);

		ArrayList<Territory> territoryVisited = new ArrayList<>();
		ObservableList<Territory> attackTerList = attackingTerritoryList.getItems();

	//	while (territoryVisited.size() <= attackTerList.size() || numberOfAttack == 0) {
			Territory attackingTerritory = getRandomAttackingTerritory(attackTerList, territoryVisited);
			List<Territory> defendingTerritories = getDefendingTerritory(attackingTerritory);
			//for (Territory defendingTerritory : defendingTerritories) {
				attack(attackingTerritory, defendingTerritories.get(0));
				//break;
		//	}
	//	}
	}

	public boolean fortificationPhase(ListView<Territory> selectedTerritoryList, ListView<Territory> adjTerritoryList,
			TextArea gameConsole, Player playerPlaying) {
		boolean isFortificationDone = false;
		ObservableList<Territory> selectedTerritory = selectedTerritoryList.getItems();
		int territoryCount = selectedTerritory.size();
		boolean fortifiyingStarted = true;
		while (fortifiyingStarted) {
			Territory fortifyingTerritory = selectedTerritory.get(randomNumber(territoryCount - 1));
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

	public boolean playerHasAValidAttackMove(ListView<Territory> territories, TextArea gameConsole) {
		boolean hasAValidMove = false;
		for (Territory territory : territories.getItems()) {
			if (territory.getArmies() > 1) {
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

	/**
	 * @param attackingTerritoryList
	 * @return
	 */
	private Territory getRandomAttackingTerritory(ObservableList<Territory> attackTerList,
			ArrayList<Territory> territoryVisited) {

		int territoryCount = attackTerList.size();
		Territory attackingTerritory = attackTerList.get(randomNumber(territoryCount - 1));
		if (attackingTerritory.getArmies() <= 1) {
			if (!territoryVisited.contains(attackingTerritory)) {
				territoryVisited.add(attackingTerritory);
			}
			getRandomAttackingTerritory(attackTerList, territoryVisited);
		}
		return attackingTerritory;
	}

	private List<Territory> getDefendingTerritory(Territory attackingTerritory) {
		List<Territory> defendingTerritories = attackingTerritory.getAdjacentTerritories().stream()
				.filter(t -> (attackingTerritory.getPlayer() != t.getPlayer())).collect(Collectors.toList());

		return defendingTerritories;

	}

	private void attack(Territory attacking, Territory defending) {
		DiceModel diceModel = new DiceModel(attacking, defending);

		DiceRollController diceController = new DiceRollController(diceModel, this);

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

		Scene scene = new Scene(root);
		newMapStage.setScene(scene);
		newMapStage.show();
	}

	/**
	 * @return Int randomNumber
	 */
	public int randomNumber(int count) {
		return (int) (Math.random() * count) + 0;
	}

}
