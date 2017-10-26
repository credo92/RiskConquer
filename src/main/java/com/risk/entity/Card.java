package com.risk.entity;

import com.risk.constant.CardEnum;

public class Card {
	CardEnum cardEnum;

	private String cardType;
	private String cardTerritoryName;

	public String getCardTerritoryName() {
		return cardTerritoryName;
	}

	public void setCardTerritoryName(String cardWithTerritoryName) {
		this.cardTerritoryName = cardWithTerritoryName;
	}

	public Card(CardEnum cardEnum) {
		this.cardEnum = cardEnum;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

}
