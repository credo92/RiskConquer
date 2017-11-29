package com.risk.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.risk.strategy.AggressiveStrategy;
import com.risk.strategy.BenevolentStrategy;
import com.risk.strategy.CheaterStrategy;
import com.risk.strategy.PlayerBehaviorStrategy;
import com.risk.strategy.RandomStrategy;

import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class TournamentModel {

	private PlayerGamePhase playerGamePhase;

	private HashMap<String, HashMap<String, String>> tournamentResult;

	private GameModel gameModel;

	/**
	 * This method converts string to enum.
	 * @param playerType String.
	 * @param player Player.
	 * @return player Player.
	 */
	public Player returnPlayerType(String playerType, Player player) {
		PlayerType type = null;
		PlayerBehaviorStrategy strategy = null;
		if (PlayerType.AGGRESSIVE.toString().equals(playerType)) {
			type = PlayerType.AGGRESSIVE;
			strategy = new AggressiveStrategy();
		} else if (PlayerType.BENEVOLENT.toString().equals(playerType)) {
			type = PlayerType.BENEVOLENT;
			strategy = new BenevolentStrategy();
		} else if (PlayerType.CHEATER.toString().equals(playerType)) {
			type = PlayerType.CHEATER;
			strategy = new CheaterStrategy();
		} else if (PlayerType.RANDOM.toString().equals(playerType)) {
			type = PlayerType.RANDOM;
			strategy = new RandomStrategy();
		}

		player.setType(type);
		player.setStrategy(strategy);
		return player;
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

	public Map createMapClone(Map map) throws IOException {
		ObjectOutputStream outPut = null;
		ObjectInputStream inPut = null;
		Map clonedMap = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			outPut = new ObjectOutputStream(bos);
			// serialize and pass the object
			outPut.writeObject(map);
			outPut.flush();
			ByteArrayInputStream bin = new ByteArrayInputStream(bos.toByteArray());
			inPut = new ObjectInputStream(bin);
			clonedMap = (Map) inPut.readObject();
		} catch (Exception e) {
			System.out.println("Exception in ObjectCloner = " + e);
		} finally {
			outPut.close();
			inPut.close();
		}
		return clonedMap;

	}

	public void startTournamentGame(List<Player> players, Map map, int numberOfTurn, TextArea console,
			Integer gameNumber) {
		Player winningPlayer = null;
		List<Player> currentGamePlayer = new ArrayList<>(players);
		playerGamePhase = new PlayerGamePhase();
		gameModel = new GameModel();
		MapUtil.appendTextToGameConsole("===Startup phase===\n", console);
		playerGamePhase.assignArmiesToPlayers(currentGamePlayer, console);

		MapUtil.appendTextToGameConsole("===Assigning territories===\n", console);
		gameModel.assignTerritoryToPlayer(map, currentGamePlayer, console);

		MapUtil.appendTextToGameConsole("===Assigning player armies to territories===\n", console);
		currentGamePlayer.stream().forEach(p -> playerGamePhase.autoAssignPlayerArmiesTournament(p, console));
		MapUtil.appendTextToGameConsole("===Startup phase ended===\n", console);

		outer: while (numberOfTurn > 0) {
			Iterator<Player> playerIterator = currentGamePlayer.iterator();
			while (playerIterator.hasNext()) {
				Player player = playerIterator.next();
				MapUtil.appendTextToGameConsole("===Player: " + player.getName() + " started playing===\n", console);
				playerGamePhase.setPlayerPlaying(player);

				// calculate reinforcemenr
				playerGamePhase.calculateReinforcementArmies(map, player);

				// Reinforcement phase
				if (player.getArmies() > 0) {
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
						// check if any player lost the game
						Player lostPlayer = playerGamePhase.checkIfAnyPlayerLostTheGame(currentGamePlayer);
						if (lostPlayer != null) {
							currentGamePlayer.remove(lostPlayer);
							playerIterator = currentGamePlayer.iterator();
						}
						// check if any player won the game
						if (playerGamePhase.checkIfPlayerWonTheGame(currentGamePlayer)) {
							winningPlayer = currentGamePlayer.get(0);
							break outer;
						}
					} catch (InvalidGameMoveException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				MapUtil.appendTextToGameConsole("===Attack phase Ended===\n", console);

				// Fortification phase
				MapUtil.appendTextToGameConsole("===Fortification phase Started===\n", console);
				if (player.getStrategy().isFortificationPhaseValid(map, player)) {
					player.getStrategy().fortificationPhase(attackingTerritory, null, console, player);
				} else {
					MapUtil.appendTextToGameConsole("===No fortification move avilable to fortify the armies===\n",
							console);
				}
				MapUtil.appendTextToGameConsole("===Fortification phase ended===\n", console);
			}
			MapUtil.appendTextToGameConsole("Turn complete" + numberOfTurn + "\n", console);
			numberOfTurn--;
		}
		String winner = null;
		String mapName = map.getMapData().get("image");
		if (winningPlayer != null) {
			winner = winningPlayer.getType().toString();
		} else {
			winner = "Draw";
		}

		if (tournamentResult.containsKey(mapName)) {
			HashMap<String, String> data = tournamentResult.get(mapName);
			data.put("Game" + gameNumber, winner);
			tournamentResult.put(mapName, data);
		} else {
			HashMap<String, String> data = new HashMap<>();
			data.put("Game" + gameNumber, winner);
			tournamentResult.put(mapName, data);
		}

	}

	/**
	 * @return the tournamentResult
	 */
	public HashMap<String, HashMap<String, String>> getTournamentResult() {
		return tournamentResult;
	}

	/**
	 * @param tournamentResult
	 *            the tournamentResult to set
	 */
	public void setTournamentResult(HashMap<String, HashMap<String, String>> tournamentResult) {
		this.tournamentResult = tournamentResult;
	}
}
