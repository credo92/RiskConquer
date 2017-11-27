package com.risk.model;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import com.risk.constant.PlayerType;
import com.risk.entity.Map;
import com.risk.entity.Player;
import com.risk.entity.Territory;
import com.risk.exception.InvalidGameMoveException;
import com.risk.exception.InvalidMapException;
import com.risk.map.util.MapFileParser;
import com.risk.map.util.MapUtil;

import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class TournamentModel {

	private PlayerGamePhase playerGamePhase;

	private GameModel gameModel;

	/**
	 * This method converts string to enum.
	 */
	public PlayerType returnPlayerType(String playerType) {
		if (PlayerType.AGGRESSIVE.toString().equals(playerType)) {
			return PlayerType.AGGRESSIVE;
		} else if (PlayerType.BENEVOLENT.toString().equals(playerType)) {
			return PlayerType.BENEVOLENT;
		} else if (PlayerType.CHEATER.toString().equals(playerType)) {
			return PlayerType.CHEATER;
		} else if (PlayerType.RANDOM.toString().equals(playerType)) {
			return PlayerType.RANDOM;
		}
		return null;
	}

	public File uploadMap(List<Map> mapList) {
		File file = MapUtil.showFileChooser();

		MapFileParser fileLoaderAndParser = new MapFileParser();
		Map map = null;
		try {
			map = fileLoaderAndParser.parseAndReadMapFile(file);
			mapList.add(map);
		} catch (InvalidMapException ex) {
			MapUtil.infoBox(ex.getMessage(), "Error", "Invalid Map");
		}
		return file;
	}

	public void startTournamentGame(List<Player> players, Map map, int numberOfTurn, TextArea console) {
		playerGamePhase = new PlayerGamePhase();
		gameModel = new GameModel();
		Iterator<Player> playerIterator = players.iterator();
		MapUtil.appendTextToGameConsole("===Startup phase===\n", console);
		playerGamePhase.assignArmiesToPlayers(players, console);

		MapUtil.appendTextToGameConsole("===Assigning territories===\n", console);
		gameModel.assignTerritoryToPlayer(map, players, console);

		MapUtil.appendTextToGameConsole("===Assigning player armies to territories===\n", console);
		players.stream().forEach(p -> playerGamePhase.autoAssignPlayerArmiesToTerritory(p, console));
		MapUtil.appendTextToGameConsole("===Startup phase ended===\n", console);

		while (numberOfTurn > 0) {
			while (playerIterator.hasNext()) {
				Player player = playerIterator.next();

				// Reinforcement phase
				if (player.getArmies() > 0) {
					MapUtil.appendTextToGameConsole("===Player: " + player.getName() + " started playing===\n",
							console);
					MapUtil.appendTextToGameConsole("===Rienforcement phase started===\n", console);
					player.getStrategy().reinforcementPhase(
							FXCollections.observableArrayList(player.getAssignedTerritory()), null, console, player);
					MapUtil.appendTextToGameConsole("===Rienforcement phase ended===\n", console);
				}

				// AttackPhase
				MapUtil.appendTextToGameConsole("===Attack phase started===\n", console);
				ListView<Territory> attackingTerritory = new ListView<>(
						FXCollections.observableArrayList(player.getAssignedTerritory()));

				while (player.getStrategy().playerHasAValidAttackMove(attackingTerritory, console)) {
					try {
						player.getStrategy().attackPhase(attackingTerritory, null, null, console);
					} catch (InvalidGameMoveException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				// Fortification phase
			}
		}
	}
}
