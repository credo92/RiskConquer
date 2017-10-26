package com.risk.entity;

import com.risk.constant.CardType;

/**
 * @author vipul
 * This is an entity class for Card with cardEnum and Territory object
 * @version 1.0.1
 * 
 */

public class Card {
	CardType cardEnum;
	private Territory territory;

	
	public Territory getTerritory() {
		return territory;
	}
	public void setTerritory(Territory territory) {
		this.territory = territory;
	}
	public Card(){
		this.cardEnum = cardEnum;
	}
	public CardType getCardEnum() {
		return cardEnum;
	}

	public void setCardEnum(CardType cardEnum) {
		this.cardEnum = cardEnum;
	}

}
