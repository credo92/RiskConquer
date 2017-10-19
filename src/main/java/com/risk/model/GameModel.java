package com.risk.model;

import java.util.ArrayList;
import java.util.List;

import com.risk.entity.Continent;
import com.risk.entity.Map;
import com.risk.entity.Player;
import com.risk.entity.Territory;
import com.risk.map.util.MapUtil;

import javafx.scene.control.TextArea;

/**
 * This class is used to represent modeling of Game.
 * @author rahul
 * @version 1.0.0.
 *
 */
public class GameModel {
	
	/**
	 * This method is used to assign armies to players and display data in the textarea in UI.
	 * @param players number of players input by user.
	 * @param textArea to show up data on UI.
	 */
	public void assignArmiesToPlayers(List<Player> players, TextArea textArea) {
		MapUtil.appendTextToGameConsole("===Assigning armies to players.===\n", textArea);

		int armySizePerPlayer = 0;
		int noOfPlayers = players.size();

		if (noOfPlayers == 3)
			armySizePerPlayer = 15;
		else if (noOfPlayers == 4)
			armySizePerPlayer = 30;
		else if (noOfPlayers == 5)
			armySizePerPlayer = 25;
		else if (noOfPlayers == 6)
			armySizePerPlayer = 20;

		for (Player player : players) {
			player.setArmies(armySizePerPlayer);
			MapUtil.appendTextToGameConsole(player.getName() + " assigned: " + armySizePerPlayer + "\n", textArea);
		}
	}

	/**
	 * This method is used to create a number of instances of Player class.
	 * @param noOfPlayer user input.
	 * @param players objects of class {@link Player}
	 * @param textArea to show up data on UI.
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
	 * @param playerPlaying
	 *            current player playing
	 * @return Player the current player object
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
	 * @return List<Continent> continents owened by player.
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
	 * Check if there are armies to be fortified.
	 * @param map map object
	 * @param playerPlaying current player playing
	 * @return boolean is fortifcation of armies available.
	 */
	public boolean isFortificationPhaseValid(Map map, Player playerPlaying) {
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

		return isFortificationAvaialble;
	}

	/**
	 * @param players
	 * @return
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
	 * @param map
	 * @param players
	 * @param textAres
	 */
	public void assignTerritoryToPlayer(Map map, List<Player> players, TextArea textAres) {

		List<Territory> allterritories = new ArrayList<>();

		if (map.getContinents() != null) {
			for (Continent continent : map.getContinents()) {
				if (continent != null && continent.getTerritories() != null) {
					for (Territory territory : continent.getTerritories()) {
						allterritories.add(territory);
					}
				}
			}
		}
		int count = 0;
		int totalTerritory = allterritories.size();
		while (count < totalTerritory) {
			for (Player player : players) {
				for (Territory territory : allterritories) {
					if (territory.getPlayer() == null) {
						count++;
						territory.setPlayer(player);
						territory.setArmies(territory.getArmies() + 1);
						player.setArmies(player.getArmies() - 1);
						player.getAssignedTerritory().add(territory);
						MapUtil.appendTextToGameConsole(
								territory.getName() + " assigned to " + player.getName() + " ! \n", textAres);
						break;
					}
					continue;
				}
			}
		}
	}
}
