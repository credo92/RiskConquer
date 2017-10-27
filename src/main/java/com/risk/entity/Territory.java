package com.risk.entity;

import java.util.ArrayList;
import java.util.List;

import com.risk.constant.CardType;

/**
 * @author rahul
 * This is an entity class for Territory with name, xCoordinate, yCoordinate, continent to which it belongs and adjacent Territories as its member variables.
 * @version 1.0.0
 * 
 */
public class Territory {
	private String name;
	private int xCoordinate;
	private int yCoordinate;
	private Continent belongToContinent;
	private List<String> adjTerritories;
	private List<Territory> adjacentTerritories;
	private boolean isProcessed;
	
	private Player player;
	
	private int armies;

	public Territory() {
		adjTerritories = new ArrayList<>();
		adjacentTerritories = new ArrayList<>();
	}
	
	/**
	 * @return the armies
	 */
	public int getArmies() {
		return armies;
	}
	
	/**
	 * @return the isProcessed
	 */
	public boolean isProcessed() {
		return isProcessed;
	}

	/**
	 * @param isProcessed the isProcessed to set
	 */
	public void setProcessed(boolean isProcessed) {
		this.isProcessed = isProcessed;
	}

	/**
	 * @param armies the armies to set
	 */
	public void setArmies(int armies) {
		this.armies = armies;
	}

	/**
	 * @return the adjTerritories
	 */
	public List<String> getAdjTerritories() {
		return adjTerritories;
	}

	/**
	 * @param adjTerritories
	 *            the adjTerritories to set
	 */
	public void setAdjTerritories(List<String> adjTerritories) {
		this.adjTerritories = adjTerritories;
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
	 * @return the xCoordinate
	 */
	public int getxCoordinate() {
		return xCoordinate;
	}

	/**
	 * @param xCoordinate
	 *            the xCoordinate to set
	 */
	public void setxCoordinate(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	/**
	 * @return the yCoordinate
	 */
	public int getyCoordinate() {
		return yCoordinate;
	}

	/**
	 * @param yCoordinate
	 *            the yCoordinate to set
	 */
	public void setyCoordinate(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	/**
	 * @return the adjacentTerritories
	 */
	public List<Territory> getAdjacentTerritories() {
		return adjacentTerritories;
	}

	/**
	 * @param adjacentTerritories
	 *            the adjacentTerritories to set
	 */
	public void setAdjacentTerritories(List<Territory> adjacentTerritories) {
		this.adjacentTerritories = adjacentTerritories;
	}

	/**
	 * @return the belongToContinent
	 */
	public Continent getBelongToContinent() {
		return belongToContinent;
	}

	/**
	 * @param belongToContinent
	 *            the belongToContinent to set
	 */
	public void setBelongToContinent(Continent belongToContinent) {
		this.belongToContinent = belongToContinent;
	}
	
	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @param player the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
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

		if (!(obj instanceof Territory)) {
			return false;
		}

		Territory territory = (Territory) obj;
		return territory.getName().equalsIgnoreCase(name);
	}

}