package com.risk.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Set;
import java.util.stream.Collectors;

import com.risk.constant.CardType;
import com.risk.controller.CardController;
import com.risk.entity.Card;
import com.risk.entity.Player;
import com.risk.strategy.HumanStrategy;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

/**
 * Card model to send data
 * 
 * @author Vipul Srivastav
 * @version 1.0.0
 */
public class CardModel extends Observable {

	/**
	 * The @playerPlaying .
	 */
	private Player playerPlaying;

	/**
	 * The @cardsToBeExchange .
	 */
	private List<Card> cardsToBeExchange;

	/**
	 * Get cards to be exchanged
	 * 
	 * @return list of card to exchange
	 */
	public List<Card> getCardsToBeExchange() {
		return cardsToBeExchange;
	}

	/**
	 * Set cards to be exchanged.
	 * 
	 * @param cardsToBeExchange
	 *            cards exchanged
	 */
	public void setCardsToBeExchange(List<Card> cardsToBeExchange) {
		this.cardsToBeExchange = cardsToBeExchange;
	}

	/**
	 * This method is used to open up the Card pop-up for particular player playing
	 * in the game.
	 * 
	 * @param player
	 *            player playing
	 * @param cardModel
	 *            card model
	 */
	public void openCardWindow(Player player, CardModel cardModel) {
		this.playerPlaying = player;
		final Stage newMapStage = new Stage();
		newMapStage.setTitle("Card Window");
		CardController cardController = new CardController(this.playerPlaying, cardModel);
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Cards.fxml"));
		loader.setController(cardController);
		Parent root = null;
		try {
			root = (Parent) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (player.getStrategy() instanceof HumanStrategy) {
			Scene scene = new Scene(root);
			newMapStage.setScene(scene);
			newMapStage.show();
		}
	}

	/**
	 * This method is used to return selected cards on the basis of checkboxes
	 * selected.
	 * 
	 * @param cards
	 *            list of cards held by currently playing player.
	 * @param checkboxes
	 *            array of checkboxes depicting each card.
	 * @return selectedCards list of selected cards which is subset of main list.
	 */
	public List<Card> retrieveSelectedCardsFromCheckbox(List<Card> cards, CheckBox[] checkboxes) {
		int counter = 0;
		List<Card> selectedCards = new ArrayList<>();
		for (CheckBox cb : checkboxes) {
			if (cb.isSelected()) {
				selectedCards.add(cards.get(counter));
			}
			counter++;
		}
		return selectedCards;
	}

	/**
	 * This method is used to check whether selected 3 cards form a valid
	 * combination or not.
	 * 
	 * @param selectedCards
	 *            cards list of cards selected by currently playing player.
	 * @return returnFlag true for valid card combination and false for invalid
	 *         combination.
	 */
	public boolean checkTradePossible(List<Card> selectedCards) {
		boolean returnFlag = false;
		if (selectedCards.size() == 3) {
			int infantry = 0, cavalry = 0, artillery = 0;
			for (Card card : selectedCards) {
				if (card.getCardType().toString().equals(CardType.INFANTRY.toString())) {
					infantry++;
				} else if (card.getCardType().toString().equals(CardType.CAVALRY.toString())) {
					cavalry++;
				} else if (card.getCardType().toString().equals(CardType.ARTILLERY.toString())) {
					artillery++;
				}
			}
			if ((infantry == 1 && cavalry == 1 && artillery == 1) || infantry == 3 || cavalry == 3 || artillery == 3) {
				returnFlag = true;
			}
		}
		return returnFlag;
	}

	public List<Card> getValidCardComibination(List<Card> selectedCards) {
		HashMap<String, Integer> map = new HashMap<>();
		for (Card card : selectedCards) {
			if (map.containsKey(card.getCardType().toString())) {
				map.put(card.getCardType().toString(), map.get(card.getCardType().toString()) + 1);
			} else {
				map.put(card.getCardType().toString(), 1);
			}
			
		}
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (entry.getValue() >= 3) {
				return selectedCards.stream().filter(t -> t.getCardType().toString().equals(entry.getKey()))
						.collect(Collectors.toList());
			}
		}
		return null;
	}

	/**
	 * This method is used to set the Cards Exchangable by passing selectedCards to
	 * setCardsToBeExchange
	 * 
	 * @param selectedCards.
	 */
	public void setCardsExchangable(List<Card> selectedCards) {
		setCardsToBeExchange(selectedCards);
		setChanged();
		notifyObservers("cardsTrade");
	}
}
