package com.risk.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.risk.constant.CardType;
import com.risk.controller.CardController;
import com.risk.entity.Card;
import com.risk.entity.Player;
import com.risk.entity.Territory;
import com.risk.map.util.MapUtil;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * @author Vipul Srivastav Card model to send data
 * @version 1.0.1
 */
public class CardModel  {
	
	private Player playerPlaying;	
	private TextArea gameConsole;
	private Stack<Card> cardStack;	
	
	/**
	 * This method is used to open up the Card pop-up for particular player playing in the game.
	 * @param player currently player playing.
	 */
	public void openCardWindow(Player player, TextArea console, Stack<Card> stack) {
		this.playerPlaying = player;
		this.gameConsole = console;
		this.cardStack = stack;
		final Stage newMapStage = new Stage();
		newMapStage.setTitle("Card Window");	    
		CardController cardController = new CardController(this.playerPlaying);		
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Cards.fxml"));
		loader.setController(cardController);
		Parent root = null;
		try {
			root = (Parent) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		newMapStage.setScene(scene);
		newMapStage.show();
	}	
	
	/**
	 * This method is used to return selected cards on the basis of checkboxes selected.
	 * @param cards list of cards held by currently playing player.
	 * @param checkboxes array of checkboxes depicting each card.
	 * @return list of selected cards which is subset of main list.
	 */
	public List<Card> retrieveSelectedCardsFromCheckbox(List<Card> cards, CheckBox[] checkboxes) {
		int counter=0;
		List<Card> selectedCards = new ArrayList<>();
		for (CheckBox cb : checkboxes) {
			if(cb.isSelected()) {
				selectedCards.add(cards.get(counter));
			}
			counter++;
		} 
		return selectedCards;
	}	
	
	/**
	 * This method is used to check whether selected 3 cards form a valid combination or not.
	 * @param cards list of cards selected by currently playing player.
	 * @return true for valid card combination and false for invalid combination. 
	 */
	public boolean checkTradePossible(List<Card> selectedCards) {
		boolean returnFlag = false;
		if(selectedCards.size()==3) {
			int infantry = 0, cavalry = 0, artillery = 0;
			for (Card card : selectedCards) {
				if(card.getCardType().toString().equals(CardType.INFANTRY.toString())) {
					infantry++;
				}
				else if(card.getCardType().toString().equals(CardType.CAVALRY.toString())) {
					cavalry++;
				}
				else if(card.getCardType().toString().equals(CardType.ARTILLERY.toString())) {
					artillery++;
				}
			}
			if((infantry==1 && cavalry==1 && artillery==1) || infantry==3 || cavalry==3 || artillery==3) {
				returnFlag = true;
			}
		}
		return returnFlag;
	}
	
	/**
	 * This method is used to trade armies for valid combination of cards.
	 * @param playerPlaying currently playing player.
	 * @param cards list of cards selected by currently playing player.
	 */
	public void tradeCardsForArmy(Player playerPlaying, List<Card> selectedCards) {		
		playerPlaying.setArmies(playerPlaying.getArmies()+1);
		//MapUtil.appendTextToGameConsole(playerPlaying.getName() + " successfully exchanged 3 cards for 1 army! \n", gameConsole);
		for (Territory t : playerPlaying.getAssignedTerritory()) {
			for (Card card : selectedCards) {
				if(t.equals(card.getTerritory())) {
					t.setArmies(t.getArmies()+2);
					//cardStack.push(card);
					//MapUtil.appendTextToGameConsole(playerPlaying.getName() + " got extra 2 armies on " +t.getName()+ "\n", gameConsole);
					break;
				}
			}
		}
	}
}
