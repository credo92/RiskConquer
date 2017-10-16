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
 * @author rahul
 *
 */
public class GameModel {
	/**
	 * @param players
	 * @param textArea
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
	 * @param noOfPlayer
	 * @param players
	 * @param textArea
	 * @return
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
	 * @param playerPlaying
	 * @return
	 */
	public Player calculateReinforcementArmies(Player playerPlaying) {
		int currentArmies = playerPlaying.getArmies();
		int territoryCount = playerPlaying.getAssignedTerritory().size();
		if (territoryCount < 9) {
			playerPlaying.setArmies(currentArmies + 3);
		} else {
			playerPlaying.setArmies((territoryCount / 3) + currentArmies);
		}

		return playerPlaying;
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
