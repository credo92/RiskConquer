package com.risk.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.risk.controller.CardController;
import com.risk.entity.Card;
import com.risk.entity.Player;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Vipul Srivastav Card model to send data
 * @version 1.0.1
 */
public class CardModel  {
	
	private Player playerPlaying;
	
	private Stack<Card> cardStack;
	
	public CardModel(Player playerPlaying, Stack<Card> cardStack)
	{
		this.playerPlaying = playerPlaying;
		this.cardStack = cardStack;
	}
	
	
	
	public void cardWindow(){
		//Test start
				List<Card> cardList = new ArrayList<Card>();
				cardList.add(cardStack.pop());
				cardList.add(cardStack.pop());
				cardList.add(cardStack.pop());
				cardList.add(cardStack.pop());
				cardList.add(cardStack.pop());
				cardList.add(cardStack.pop());
				
				playerPlaying.setPlayerCardList(cardList);
				//Test end	
				
				final Stage newMapStage = new Stage();
				newMapStage.setTitle("Card Window");
			    
				CardController cardController = new CardController(playerPlaying);
				
				FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Cards.fxml"));
				loader.setController(cardController);

				Parent root = null;
				try {
					root = (Parent) loader.load();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Scene scene = new Scene(root);
				newMapStage.setScene(scene);
				newMapStage.show();

	}
	
}
