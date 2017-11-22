package com.risk.entity;

import com.risk.constant.CardType;

/**
 * This is an entity class for Card with cardType object and territoryName as
 * its member variable
 * 
 * @author Vipul Srivastav
 * @version 1.0.0
 */

public class Card {

	/**
	 * The @cardType .
	 */
	CardType cardType;

	/**
	 * The @territory .
	 */
	private Territory territory;

	/**
	 * Constructor for Card
	 * 
	 * @param cardType
	 *            reference to get cardType enum
	 */
	public Card(CardType cardType) {
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

	@Override
	public boolean equals(Object obj) {

		if (obj == this) {
			return true;
		}

		if (!(obj instanceof Card)) {
			return false;
		}

		Card card = (Card) obj;
		return card.getCardType().toString().equalsIgnoreCase(cardType.toString())
				&& card.getTerritory().equals(territory);
	}

}
