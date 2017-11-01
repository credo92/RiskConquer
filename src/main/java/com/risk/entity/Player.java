package com.risk.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rahul
 * This is an entity class for Player with id, name, armies and assignedTerritories as its member variables.
 * @version 1.0.0
 *
 */
public class Player {

	private int id;

	private String name;

	private int armies;

	private List<Territory> assignedTerritory;
	
	/**
	 * The @cardTypeList. 
	 * 		stores player's list of cards
	 */
	private List<Card> playerCardList;
	
	public Player(int id, String name) {
		this.id = id;
		this.name = name;
		this.assignedTerritory = new ArrayList<>();
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the armies
	 */
	public int getArmies() {
		return armies;
	}

	/**
	 * @param armies
	 *            the armies to set
	 */
	public void setArmies(int armies) {
		this.armies = armies;
	}

	/**
	 * @return the assignedTerritory
	 */
	public List<Territory> getAssignedTerritory() {
		return assignedTerritory;
	}

	/**
	 * @param assignedTerritory
	 *            the assignedTerritory to set
	 */
	public void setAssignedTerritory(List<Territory> assignedTerritory) {
		this.assignedTerritory = assignedTerritory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {

		if (obj == this) {
			return true;
		}

		if (!(obj instanceof Player)) {
			return false;
		}

		Player player = (Player) obj;
		return player.getName().equalsIgnoreCase(name);
	}
	
	/**
	 * The @getCardTypeList.
	 * 		getter method 
	 */
	public List<Card> getPlayerCardList() {
		return playerCardList;
	}
	
	/**
	 * The @setCardTypeList.
	 * 		setter method 
	 */
	public void setPlayerCardList(List<Card> playerCardList) {
		this.playerCardList = playerCardList;
	}
}