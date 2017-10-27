package com.risk.entity;

import com.risk.constant.CardType;

/**
 * @author vipul
 * This is an entity class for Card with cardEnum and Territory object
 * @version 1.0.1
 * 
 */

public class Card {
	CardType cardType;
	private String territoryName;
	
	public String getTerritoryName() {
		return territoryName;
	}
	public void setTerritoryName(String territoryName) {
		this.territoryName = territoryName;
	}
	public Card(CardType cardType){
		this.cardType = cardType;
	}
	public CardType getCardType() {
		return cardType;
	}
}