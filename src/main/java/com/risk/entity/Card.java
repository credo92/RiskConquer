package com.risk.entity;

import com.risk.constant.CardType;

public class Card {

	CardType cardType;
	
	private String territoryName;

	public Card(CardType cardType) {
		this.cardType = cardType;
	}

	/**
	 * @return the territoryName
	 */
	public String getTerritoryName() {
		return territoryName;
	}

	/**
	 * @param territoryName
	 *            the territoryName to set
	 */
	public void setTerritoryName(String territoryName) {
		this.territoryName = territoryName;
	}

	/**
	 * @return the cardType
	 */
	public CardType getCardType() {
		return cardType;
	}
}