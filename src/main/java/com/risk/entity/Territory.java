package com.risk.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This is an entity class for Territory with name, xCoordinate, yCoordinate, continent to which it belongs and adjacent Territories as its member variables. 
 * @author rahul
 * @version 1.0.0
 * 
 */
public class Territory implements Serializable{
	
	/**
	 * The @name.
	 */
	private String name;
	
	/**
	 * The @xCoordinate.
	 */
	private int xCoordinate;
	
	/**
	 * The @yCoordinate.
	 */
	private int yCoordinate;
	
	/**
	 * The @belongToContinent.
	 */
	private Continent belongToContinent;
	
	/**
	 * The @adjTerritories.
	 */
	private List<String> adjTerritories;
	
	/**
	 * The @adjacentTerritories
	 */
	private List<Territory> adjacentTerritories;
	
	/**
	 * The @isProcessed
	 */
	private boolean isProcessed;
	
	/**
	 * The @player
	 */
	private Player player;
	
	/**
	 * The @armies
	 */
	private int armies;
	
	/**
	 * Constructor for Territory
	 */
	public Territory() {
		adjTerritories = new ArrayList<>();
		adjacentTerritories = new ArrayList<>();
	}
	
	/**
	 * Get Armies
	 * 
	 * @return armies
	 */
	public int getArmies() {
		return armies;
	}
	
	/**
	 *  Is Processed
	 *  
	 * @return isProcessed
	 */
	public boolean isProcessed() {
		return isProcessed;
	}

	/**
	 * Set Is Processed
	 * 
	 * @param isProcessed 
	 * 					the isProcessed to set
	 */
	public void setProcessed(boolean isProcessed) {
		this.isProcessed = isProcessed;
	}

	/**
	 * Set Armies
	 * 
	 * @param armies 
	 * 				the armies to set
	 */
	public void setArmies(int armies) {
		this.armies = armies;
	}

	/**
	 * Get Adjacent Territories
	 * 
	 * @return adjTerritories
	 */
	public List<String> getAdjTerritories() {
		return adjTerritories;
	}

	/**
	 * Set Adjacent Territories
	 * 
	 * @param adjTerritories
	 *            the adjTerritories to set
	 */
	public void setAdjTerritories(List<String> adjTerritories) {
		this.adjTerritories = adjTerritories;
	}

	/**
	 * Get Name
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set Name
	 * 
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get X Coordinate
	 * 
	 * @return xCoordinate
	 */
	public int getxCoordinate() {
		return xCoordinate;
	}

	/**
	 * Set xCoordinate
	 * 
	 * @param xCoordinate
	 *            the xCoordinate to set
	 */
	public void setxCoordinate(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	/**
	 * Get yCoordinate
	 * 
	 * @return yCoordinate
	 */
	public int getyCoordinate() {
		return yCoordinate;
	}

	/**
	 * Set yCoordinate
	 * 
	 * @param yCoordinate
	 *            the yCoordinate to set
	 */
	public void setyCoordinate(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	/**
	 * Get Adjacent Territories
	 * 
	 * @return adjacentTerritories
	 */
	public List<Territory> getAdjacentTerritories() {
		return adjacentTerritories;
	}

	/**
	 * Set Adjacent Territories
	 * 
	 * @param adjacentTerritories
	 *            the adjacentTerritories to set
	 */
	public void setAdjacentTerritories(List<Territory> adjacentTerritories) {
		this.adjacentTerritories = adjacentTerritories;
	}

	/**
	 * Get Belong to Continent
	 * 
	 * @return belongToContinent
	 */
	public Continent getBelongToContinent() {
		return belongToContinent;
	}

	/**
	 * Set Belong to continent 
	 * 
	 * @param belongToContinent
	 *            the belongToContinent to set
	 */
	public void setBelongToContinent(Continent belongToContinent) {
		this.belongToContinent = belongToContinent;
	}
	
	/**
	 * Get Player
	 * 
	 * @return player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Set Player
	 * 
	 * @param player 
	 * 			    the player to set
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
