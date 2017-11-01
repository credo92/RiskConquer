package com.risk.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import com.risk.constant.MapConstant;
import com.risk.controller.DiceRollController;
import com.risk.entity.Continent;
import com.risk.entity.Map;
import com.risk.entity.Player;
import com.risk.entity.Territory;
import com.risk.exception.InvalidGameMoveException;
import com.risk.main.DiceViewLoader;
import com.risk.map.util.MapUtil;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class PlayerModel extends Observable {

	Player playerPlaying;

	/**
	 * This method is used to assign armies to players and display data in the
	 * textarea in UI.
	 * 
	 * @param players
	 *            number of players input by user.
	 * @param textArea
	 *            to show up data on UI.
	 */
	public void assignArmiesToPlayers(List<Player> players, TextArea textArea) {
		MapUtil.appendTextToGameConsole("===Assigning armies to players.===\n", textArea);

		int armySizePerPlayer = 0;
		int noOfPlayers = players.size();

		if (noOfPlayers == 3)
			armySizePerPlayer = MapConstant.ARMIES_THREE_PLAYER;
		else if (noOfPlayers == 4)
			armySizePerPlayer = MapConstant.ARMIES_FOUR_PLAYER;
		else if (noOfPlayers == 5)
			armySizePerPlayer = MapConstant.ARMIES_FIVE_PLAYER;
		else if (noOfPlayers == 6)
			armySizePerPlayer = MapConstant.ARMIES_SIX_PLAYER;

		for (Player player : players) {
			player.setArmies(armySizePerPlayer);
			MapUtil.appendTextToGameConsole(player.getName() + " assigned: " + armySizePerPlayer + "\n", textArea);
		}
	}

	/**
	 * This method is used to create a number of instances of Player class.
	 * 
	 * @param noOfPlayer
	 *            user input.
	 * @param players
	 *            objects of class {@link Player}
	 * @param textArea
	 *            to show up data on UI.
	 * @return list of players.
	 */
	public List<Player> createPlayer(int noOfPlayer, List<Player> players, TextArea textArea) {
		for (int i = 0; i < noOfPlayer; i++) {
			String name = "Player" + i;
			players.add(new Player(i, name));
			MapUtil.appendTextToGameConsole(name + " created!\n", textArea);
		}
		return players;
	}

	/**
	 * Calculate the number of armies for each reinforcement phase as per the Risk
	 * rules
	 * 
	 * @param map
	 *            map object
	 * @param playerPlaying
	 *            current player playing
	 * @return Player player updated
	 */
	public Player calculateReinforcementArmies(Map map, Player playerPlaying) {
		int currentArmies = playerPlaying.getArmies();
		int territoryCount = playerPlaying.getAssignedTerritory().size();
		if (territoryCount < 9) {
			currentArmies = currentArmies + 3;
		} else {
			currentArmies = currentArmies + (territoryCount / 3);
		}

		List<Continent> continents = getContinentsOwnedByPlayer(map, playerPlaying);
		if (continents.size() > 0) {
			for (Continent continent : continents) {
				currentArmies = currentArmies + Integer.parseInt(continent.getValue());
			}
		}
		playerPlaying.setArmies(currentArmies);

		return playerPlaying;
	}

	/**
	 * Get the list of continents owened by the player.
	 * 
	 * @param map
	 *            map object
	 * @param playerPlaying
	 *            the player currently playing
	 * @return List continents owened by player.
	 */
	public List<Continent> getContinentsOwnedByPlayer(Map map, Player playerPlaying) {
		List<Continent> continents = new ArrayList<>();

		for (Continent continent : map.getContinents()) {
			boolean continentBelongToPlayer = true;
			for (Territory territory : continent.getTerritories()) {
				if (!territory.getPlayer().equals(playerPlaying)) {
					continentBelongToPlayer = false;
					break;
				}
			}
			if (continentBelongToPlayer) {
				System.out.println("Player: " + playerPlaying.getName() + " own continent: " + continent.getName());
				continents.add(continent);
			}
		}

		return continents;
	}

	/**
	 * @param territory
	 * @param gameConsole
	 */
	public void reinforcementPhase(Territory territory, TextArea gameConsole) {
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
		// start attack phase
		if (playerPlaying.getArmies() <= 0) {
			setChanged();
			notifyObservers("Attack");
		}
	}

	/**
	 * Attack phase
	 */
	public void attackPhase(Territory attackingTerritory, Territory defendingTerritory)
			throws InvalidGameMoveException {
		if (attackingTerritory != null && defendingTerritory != null) {
			isAValidAttackMove(attackingTerritory, defendingTerritory);
			DiceModel diceModel = new DiceModel(attackingTerritory, defendingTerritory);
			
			final Stage newMapStage = new Stage();
			newMapStage.setTitle("Attack Window");

			DiceRollController diceController = new DiceRollController(diceModel);

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
			
			
			
			
			
			//new DiceViewLoader(diceModel);
		} else {
			throw new InvalidGameMoveException("Please choose both attacking and defending territory.");
		}
		// attack phase goes here. once done notify for fortification phase
		//setChanged();
		//notifyObservers("checkIfFortificationPhaseValid");
	}

	/**
	 * @param selectedTerritory
	 * @param adjTerritory
	 */
	public void fortificationPhase(Territory selectedTerritory, Territory adjTerritory) {
		if (selectedTerritory == null) {
			MapUtil.infoBox("Please choose Selected Territory as source.", "Message", "");
			return;
		} else if (adjTerritory == null) {
			MapUtil.infoBox("Please choose Adjacent Territory as destination.", "Message", "");
			return;
		} else if (!(adjTerritory.getPlayer().equals(playerPlaying))) {
			MapUtil.infoBox("Adjacent Territory does not belong to you.", "Message", "");
			return;
		}

		Integer armies = Integer.valueOf(MapUtil.inputDialogueBoxForArmiesFortification());
		if (armies > 0) {
			if (selectedTerritory.getArmies() == armies) {
				MapUtil.infoBox("You cannot move all the armies.", "Message", "");
				return;
			} else if (selectedTerritory.getArmies() < armies) {
				MapUtil.infoBox("You don't have " + armies + " armies.", "Message", "");
				return;
			} else {
				selectedTerritory.setArmies(selectedTerritory.getArmies() - armies);
				adjTerritory.setArmies(adjTerritory.getArmies() + armies);
				setChanged();
				notifyObservers("Reinforcement");
			}
		} else {
			MapUtil.infoBox("Invalid entry", "Message", "");
			return;
		}
	}

	/**
	 * Check if there are armies to be fortified.
	 * 
	 * @param map
	 *            map object
	 * @param playerPlaying
	 *            current player playing
	 * @return boolean is fortifcation of armies available.
	 */
	public void isFortificationPhaseValid(Map map, Player playerPlaying) {
		boolean isFortificationAvaialble = false;
		outer: for (Continent continent : map.getContinents()) {
			for (Territory territory : continent.getTerritories()) {
				if (territory.getPlayer().equals(playerPlaying)) {
					if (territory.getArmies() > 1) {
						for (Territory adjterritory : territory.getAdjacentTerritories()) {
							if (adjterritory.getPlayer().equals(playerPlaying)) {
								isFortificationAvaialble = true;
								break outer;
							}
						}
					}
				}
			}
		}
		if (isFortificationAvaialble) {
			setChanged();
			notifyObservers("Fortification");
		} else {
			setChanged();
			notifyObservers("noFortificationMove");
		}
	}

	/**
	 * @param playerPlaying
	 * @param selectedTerritoryList
	 * @param gamePlayerList
	 */
	public void placeArmy(Player playerPlaying, ListView<Territory> selectedTerritoryList,
			List<Player> gamePlayerList) {
		int playerArmies = playerPlaying.getArmies();
		if (playerArmies > 0) {
			Territory territory = selectedTerritoryList.getSelectionModel().getSelectedItem();
			if (territory == null) {
				territory = selectedTerritoryList.getItems().get(0);
			}
			territory.setArmies(territory.getArmies() + 1);
			playerPlaying.setArmies(playerArmies - 1);
		}

		boolean armiesExhausted = checkIfPlayersArmiesExhausted(gamePlayerList);
		if (armiesExhausted) {
			setChanged();
			notifyObservers("FirstAttack");
		} else {
			setChanged();
			notifyObservers("placeArmy");
		}
	}

	/**
	 * CHeck if player armies is exhausted
	 * 
	 * @param players
	 *            player object
	 * @return boolean if player armies is exhausted
	 */
	public boolean checkIfPlayersArmiesExhausted(List<Player> players) {
		int count = 0;

		for (Player player : players) {
			if (player.getArmies() == 0) {
				count++;
			}
		}
		if (count == players.size()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param attacking
	 * @param defending
	 * @return
	 * @throws InvalidGameMoveException
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
	 * @param playerPlaying
	 *            the playerPlaying to set
	 */
	public void setPlayerPlaying(Player playerPlaying) {
		this.playerPlaying = playerPlaying;
	}
}
