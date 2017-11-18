package com.risk.strategy;

import java.io.IOException;

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

public class HumanStrategy implements PlayerBehaviorStrategy {

	public void reinforcementPhase(ObservableList<Territory> territoryList, Territory territory, TextArea gameConsole,
			Player playerPlaying) {
		if (playerPlaying.getArmies() > 0) {
			if (territory == null) {
				MapUtil.infoBox("Select a territory to place army on.", "Message", "");
				return;
			}

			Integer armies = Integer.valueOf(MapUtil.inputDailougeBox());
			if (playerPlaying.getArmies() < armies) {
				MapUtil.infoBox("You do not have sufficent armies.", "Message", "");
				return;
			}
			territory.setArmies(territory.getArmies() + armies);
			playerPlaying.setArmies(playerPlaying.getArmies() - armies);
			MapUtil.appendTextToGameConsole(armies + ": assigned to territory " + territory.getName() + "\n",
					gameConsole);
		}
	}

	public void attackPhase(ListView<Territory> attackingTerritoryList, ListView<Territory> defendingTerritoryList,
			PlayerGamePhase gamePhase) throws InvalidGameMoveException {
		Territory attackingTerritory = attackingTerritoryList.getSelectionModel().getSelectedItem();
		Territory defendingTerritory = defendingTerritoryList.getSelectionModel().getSelectedItem();
		DiceModel diceModel = new DiceModel(attackingTerritory, defendingTerritory);
		diceModel.addObserver(gamePhase);

		DiceRollController diceController = new DiceRollController(diceModel);

		if (attackingTerritory != null && defendingTerritory != null) {
			isAValidAttackMove(attackingTerritory, defendingTerritory);
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
		} else {
			throw new InvalidGameMoveException("Please choose both attacking and defending territory.");
		}

	}

	public boolean fortificationPhase(Territory selectedTerritory, Territory adjTerritory, TextArea gameConsole,
			Player playerPlaying) {
		if (selectedTerritory == null) {
			MapUtil.infoBox("Please choose Selected Territory as source.", "Message", "");
			return false;
		} else if (adjTerritory == null) {
			MapUtil.infoBox("Please choose Adjacent Territory as destination.", "Message", "");
			return false;
		} else if (!(adjTerritory.getPlayer().equals(playerPlaying))) {
			MapUtil.infoBox("Adjacent Territory does not belong to you.", "Message", "");
			return false;
		}

		Integer armies = Integer.valueOf(MapUtil.inputDialogueBoxForArmiesFortification());
		if (armies > 0) {
			if (selectedTerritory.getArmies() == armies) {
				MapUtil.infoBox("You cannot move all the armies.", "Message", "");
				return false;
			} else if (selectedTerritory.getArmies() < armies) {
				MapUtil.infoBox("You don't have " + armies + " armies.", "Message", "");
				return false;
			} else {
				selectedTerritory.setArmies(selectedTerritory.getArmies() - armies);
				adjTerritory.setArmies(adjTerritory.getArmies() + armies);
				MapUtil.appendTextToGameConsole(
						armies + " armies fortified on territory: " + adjTerritory.getName() + "\n", gameConsole);
				MapUtil.appendTextToGameConsole("=======Fortification ended=======\n", gameConsole);
				return true;
			}
		} else {
			MapUtil.infoBox("Invalid entry", "Message", "");
			return false;
		}
	}

	/**
	 * Check if Attack Move is Valid
	 * 
	 * @param attacking
	 *            attacking Territory
	 * @param defending
	 *            defending Territory
	 * 
	 * @return isValidAttackMove if the attack move is valid
	 * 
	 * @throws InvalidGameMoveException
	 *             invalid game exception
	 */
	public boolean isAValidAttackMove(Territory attacking, Territory defending) throws InvalidGameMoveException {
		boolean isValidAttackMove = false;
		if (defending.getPlayer() != attacking.getPlayer()) {
			if (attacking.getArmies() > 1) {
				isValidAttackMove = true;
			} else {
				throw new InvalidGameMoveException("Attacking territory should have more than one army to attack.");
			}
		} else {
			throw new InvalidGameMoveException("You cannot attack on your own territory.");
		}
		return isValidAttackMove;
	}

	/**
	 * Check if player has valid attack move
	 * 
	 * @param territories
	 *            territories List View
	 * @param gameConsole
	 *            gameConsole text area
	 * 
	 * @return hasAValidMove true if player has valid move else false
	 */
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

}
