package com.risk.map.util;

import javafx.scene.control.ChoiceBox;

public class GameUtil {
	/**
	 * Initialize the number of players.
	 */
	public static ChoiceBox<Integer> initializeTotalPlayers(ChoiceBox<Integer> numberOfPlayers) {
		numberOfPlayers.getItems().removeAll(numberOfPlayers.getItems());
		numberOfPlayers.getItems().addAll(3, 4, 5, 6);

		return numberOfPlayers;
	}

}
