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
	
	private Player playerPlaying;	
	private List<Card> playerCards;	
	private CheckBox[] cbs;
	
	private CardModel cardModel;
	
	public CardController(Player playerPlaying, CardModel cardModel){
		this.playerPlaying = playerPlaying;
		this.cardModel = cardModel;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {		
		currentPlayerName.setText("Cards of " + playerPlaying.getName());		
		playerCards = playerPlaying.getPlayerCardList();
		loadAllCards();		
	}
	
	public void loadAllCards() {
		int numberOfCards = playerCards.size();		
		cbs = new CheckBox[numberOfCards];				
		for (int i = 0; i < numberOfCards; i++){
			cbs[i] = new CheckBox(playerCards.get(i).getCardType().toString() + " -> " + playerCards.get(i).getTerritory().getName());
		}
		cardVbox.getChildren().addAll(cbs);
	}	
	
	/**
	 * trade
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
				//cardModel.tradeCardsForArmy(playerPlaying, selectedCards);
				//textToShow.setText("Valid combination. So, player has been assigned one army.");
			}
			else {			
				textToShow.setText("Invalid Combination.");
				trade.setDisable(false);
				return;
			}	
		}		
	}
}
