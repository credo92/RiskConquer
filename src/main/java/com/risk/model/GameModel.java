package com.risk.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.risk.constant.CardType;
import com.risk.entity.Card;
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
 */
public class GameModel {

	/**
	 * Load card.
	 * @param map map
	 * @param textAres textArea
	 * @return stackOfCards stack of cards
	 */
	public Stack<Card> assignCardToTerritory(Map map, TextArea textAres) {
		Stack<Card> stackOfCards = new Stack<Card>();

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
		for (Territory territory : allterritories) {
			Card card = new Card(CardType.values()[(int) (Math.random() * CardType.values().length)]);
			card.setTerritory(territory);
			stackOfCards.push(card);
		}
		return stackOfCards;
	}

	/**
	 * Assign territory to players
	 * @param map map
	 * @param players players
	 * @param textAres textArea
	 * @return players player list
	 */
	public List<Player> assignTerritoryToPlayer(Map map, List<Player> players, TextArea textAres) {

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
		return players;
	}
}
