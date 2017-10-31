package com.risk.entity;

import com.risk.constant.CardType;

/**
 * @author vipul
 * This is an entity class for Card with cardType object and territoryName as its member variable
 * @version 1.0.1
 * 
 */

public class Card {
	CardType cardType;
	private Territory territory;
	
	public Card(CardType cardType){
		this.cardType = cardType;
	}
	
	/**
	 * @return the cardType
	 */
	public CardType getCardType() {
		return cardType;
	}
	
	/**
	 * @return the territory Object
	 */
	public Territory getTerritory() {
		return territory;
	}
	
	/**
	 * @param territory
	 *            the territory to set
	 */
	public void setTerritory(Territory territory) {
		this.territory = territory;
	}
}