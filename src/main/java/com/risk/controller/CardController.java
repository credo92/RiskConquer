package com.risk.controller;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.risk.entity.Card;
import com.risk.entity.Player;
import com.risk.map.util.GameUtil;
import com.risk.model.CardModel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * @author Vipul Srivastav
 * CardController controller for Card Exchange
 * @version 1.0.1
 */
public class CardController implements Initializable {

	/**
	 * The @tradeButton button.
	 */
	@FXML
	private Button trade; 

	/**
	 * The @currentPlayerId label.
	 */
	@FXML
	private Label currentPlayerName;

	/**
	 * The @addedArmies label.
	 */
	@FXML
	private Label textToShow;

	/**
	 * The @cardVbox .
	 */
	@FXML
	private VBox cardVbox;
	
	/**
	 * The @playerPlaying .
	 */
	private Player playerPlaying;
	
	/**
	 * The @playerCards .
	 */
	private List<Card> playerCards;	
	
	/**
	 * The @cbs .
	 */
	private CheckBox[] cbs;
	
	/**
	 * The @cardModel.
	 */
	private CardModel cardModel;
	
	/**
	 * The @cancelCardView.
	 */
	@FXML
	private Button cancelCardView;
	
	/**
	 * Constructor for CardController
	 * 
	 * @param playerPlaying
	 *            reference to get details about current player playing
	 * 
	 * @param cardModel
	 *            reference to get details about card model 
	 */
	public CardController(Player playerPlaying, CardModel cardModel){
		this.playerPlaying = playerPlaying;
		this.cardModel = cardModel;
	}
	
	/*
	 * (non-Javadoc) Card controller initializer, loading currentPlayer and current
	 * playerPlaying cardList data.
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {		
		currentPlayerName.setText("Cards of " + playerPlaying.getName());		
		playerCards = playerPlaying.getPlayerCardList();
		if (playerCards.size()<3) {
			trade.setDisable(true);
		}
		else {
			trade.setDisable(false);
		}
		loadAllCards();		
	}
	
	/**
	 * Load All cards of a player.
	 */
	public void loadAllCards() {
		int numberOfCards = playerCards.size();		
		cbs = new CheckBox[numberOfCards];				
		for (int i = 0; i < numberOfCards; i++){
			cbs[i] = new CheckBox(playerCards.get(i).getCardType().toString() + " -> " + playerCards.get(i).getTerritory().getName());
		}
		cardVbox.getChildren().addAll(cbs);
	}	
	
	/**
	 * Close view card screen.
	 * @param event action event
	 */
	@FXML
	private void cancelCardView(ActionEvent event) {
		GameUtil.closeScreen(cancelCardView);
	}
	
	/**
	 * Trade checked cards.
	 * @param event
	 *            event.
	 */
	@FXML
	private void checkTrade(ActionEvent event) {		
		trade.setDisable(false);
		textToShow.setText(null);
		List<Card> selectedCards = cardModel.retrieveSelectedCardsFromCheckbox(playerCards, cbs);
		
		if(selectedCards.size()==3) {
			boolean flag = cardModel.checkTradePossible(selectedCards);
			
			if(flag) {
				cardModel.setCardsExchangable(selectedCards);
				GameUtil.closeScreen(trade);
			}
			else {			
				textToShow.setText("Invalid Combination.");
				trade.setDisable(false);
				return;
			}	
		}		
		else {
			textToShow.setText("Select only 3 cards");
			return;
		}
	}
}