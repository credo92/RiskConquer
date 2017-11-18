package com.risk.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.risk.constant.MapConstant;
import com.risk.controller.DiceRollController;
import com.risk.entity.Card;
import com.risk.entity.Continent;
import com.risk.entity.Map;
import com.risk.entity.Player;
import com.risk.entity.Territory;
import com.risk.exception.InvalidGameMoveException;
import com.risk.map.util.MapUtil;
import com.risk.strategy.PlayerBehaviorStrategy;

import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

/**
 * Model class for the Player
 * 
 * @author rahul
 * @version 1.0.0
 */
public class PlayerGamePhase extends Observable implements Observer {

	/**
	 * the @playerPlaying reference
	 */
	Player playerPlaying;

	PlayerBehaviorStrategy behaviorStrategy;

	/**
	 * @return player playing
	 */
	public Player getPlayerPlaying() {
		return playerPlaying;
	}

	/**
	 * the @territoryWon
	 */
	private int territoryWon;

	/**
	 * This method is used to assign armies to players and display data in the
	 * textarea in UI.
	 * 
	 * @param players
	 *            players list
	 * @param textArea
	 *            textarea
	 * @return isAssignationSuccess boolean true if armies assign.
	 */
	public boolean assignArmiesToPlayers(List<Player> players, TextArea textArea) {
		MapUtil.appendTextToGameConsole("===Assigning armies to players.===\n", textArea);
		boolean isAssignationSuccess = false;
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
			isAssignationSuccess = true;
		}
		return isAssignationSuccess;
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
	 * @return players list of players.
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
	 * @return playerPlaying player updated
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
	 * Get the list of continents owned by the player.
	 * 
	 * @param map
	 *            map object
	 * @param playerPlaying
	 *            the player currently playing
	 * @return continents continents owned by player.
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
				continents.add(continent);
			}
		}

		return continents;
	}

	/**
	 * Reinforcement Phase
	 * 
	 * @param territory
	 *            territory Object
	 * @param gameConsole
	 *            the Game Console
	 */
	public void reinforcementPhase(Territory territory, TextArea gameConsole) {
		behaviorStrategy.reinforcementPhase(territory, gameConsole, playerPlaying);
		// start attack phase
		if (playerPlaying.getArmies() <= 0) {
			MapUtil.appendTextToGameConsole("===Reinforcement phase Ended! ===\n", gameConsole);
			setChanged();
			notifyObservers("Attack");
		}
	}

	/**
	 * Attack phase
	 * 
	 * @param attackingTerritory
	 *            attacking territory
	 * @param defendingTerritory
	 *            defending territory
	 * @throws InvalidGameMoveException
	 *             invalid game exception
	 */
	public void attackPhase(Territory attackingTerritory, Territory defendingTerritory)
			throws InvalidGameMoveException {
		
		DiceModel diceModel = new DiceModel(attackingTerritory, defendingTerritory);
		diceModel.addObserver(this);
		
		DiceRollController diceController = new DiceRollController(diceModel);
		
		behaviorStrategy.attackPhase(attackingTerritory, defendingTerritory, diceController);
	}

	/**
	 * Fortification Phase
	 * 
	 * @param selectedTerritory
	 *            selected Territory object
	 * @param adjTerritory
	 *            adj Territory object
	 * @param gameConsole
	 *            gameConsole
	 */
	public void fortificationPhase(Territory selectedTerritory, Territory adjTerritory, TextArea gameConsole) {
		boolean isFortificationDone = behaviorStrategy.fortificationPhase(selectedTerritory, adjTerritory, gameConsole,
				playerPlaying);

		if (isFortificationDone) {
			setChanged();
			notifyObservers("Reinforcement");
		}

	}

	/**
	 * Check if there are armies to be fortified.
	 * 
	 * @param map
	 *            map object
	 * @param playerPlaying
	 *            current player playing
	 * @return isFortificationAvaialble is fortification of armies available.
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
		if (isFortificationAvaialble) {
			setChanged();
			notifyObservers("Fortification");
		} else {
			setChanged();
			notifyObservers("noFortificationMove");
		}
		return isFortificationAvaialble;
	}

	/**
	 * Place Armies
	 * 
	 * @param playerPlaying
	 *            get current PlayerPlaying object
	 * @param selectedTerritoryList
	 *            get Selected Territory List for List View
	 * @param gamePlayerList
	 *            gamePlayer List
	 * @param gameConsole
	 *            gameConsole
	 */
	public void placeArmy(Player playerPlaying, ListView<Territory> selectedTerritoryList, List<Player> gamePlayerList,
			TextArea gameConsole) {
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
			MapUtil.appendTextToGameConsole("===Setup Phase Completed!===\n", gameConsole);
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
		boolean hasValidAttackMove = behaviorStrategy.playerHasAValidAttackMove(territories, gameConsole);
		if (!hasValidAttackMove) {
			setChanged();
			notifyObservers("checkIfFortificationPhaseValid");
		}

		return hasValidAttackMove;
	}

	/**
	 * Check if any Player Lost the Game
	 * 
	 * @param playersPlaying
	 *            playerPlaying List
	 * 
	 * @return playerLost Player Object who lost the game
	 */
	public Player checkIfAnyPlayerLostTheGame(List<Player> playersPlaying) {
		Player playerLost = null;
		for (Player player : playersPlaying) {
			if (player.getAssignedTerritory().isEmpty()) {
				playerLost = player;
				playerPlaying.getPlayerCardList().addAll(playerLost.getPlayerCardList());
			}
		}
		return playerLost;
	}

	/**
	 * This method is used to trade armies for valid combination of cards.
	 * 
	 * @param selectedCards
	 *            list of cards selected by currently playing player for exchange.
	 * @param numberOfCardSetExchanged
	 *            counter of number of times cards get changed.
	 * @param gameConsole
	 *            Console of the game.
	 * @return playerPlaying player object
	 */
	public Player tradeCardsForArmy(List<Card> selectedCards, int numberOfCardSetExchanged, TextArea gameConsole) {
		playerPlaying.setArmies(playerPlaying.getArmies() + (5 * numberOfCardSetExchanged));
		MapUtil.appendTextToGameConsole(playerPlaying.getName() + " successfully exchanged 3 cards for 1 army! \n",
				gameConsole);
		for (Territory t : playerPlaying.getAssignedTerritory()) {
			for (Card card : selectedCards) {
				if (t.equals(card.getTerritory())) {
					t.setArmies(t.getArmies() + 2);
					MapUtil.appendTextToGameConsole(
							playerPlaying.getName() + " got extra 2 armies on " + t.getName() + "\n", gameConsole);
					break;
				}
			}
		}
		return playerPlaying;
	}

	/**
	 * Set Player Playing
	 * 
	 * @param playerPlaying
	 *            the playerPlaying to set
	 */
	public void setPlayerPlaying(Player playerPlaying) {
		this.playerPlaying = playerPlaying;
	}

	/**
	 * Get Territory Won
	 * 
	 * @return territoryWon
	 */
	public int getTerritoryWon() {
		return territoryWon;
	}

	/**
	 * Set territory Won
	 * 
	 * @param territoryWon
	 *            the territoryWon to set
	 */
	public void setTerritoryWon(int territoryWon) {
		this.territoryWon = territoryWon;
	}

	/**
	 * Update
	 * 
	 * @param o
	 *            Observable object
	 * @param arg
	 *            Object arg
	 */
	public void update(Observable o, Object arg) {
		String view = (String) arg;

		if (view.equals("rollDiceComplete")) {
			DiceModel diceModel = (DiceModel) o;
			setTerritoryWon(diceModel.getNumOfTerritoriesWon());
			setChanged();
			notifyObservers("rollDiceComplete");
		}
	}

	/**
	 * @return the startegy
	 */
	public PlayerBehaviorStrategy getStartegy() {
		return behaviorStrategy;
	}

	/**
	 * @param startegy
	 *            the startegy to set
	 */
	public void setStartegy(PlayerBehaviorStrategy startegy) {
		this.behaviorStrategy = startegy;
	}
}
