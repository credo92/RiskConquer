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
	
	/**
	 * The @id.
	 */
	private int id;
	
	/**
	 * The @name
	 */
	private String name;
	
	/**
	 * The @armies
	 */
	private int armies;
	
	/**
	 * The @assignedTerritory
	 */
	private List<Territory> assignedTerritory;
	
	/**
	 * The @playerCardList
	 */
	private List<Card> playerCardList;
	
	/**
	 * Constructor for Player
	 * 
	 * @param id
	 *            for player id
	 * 
	 * @param name
	 *            for player name 
	 */
	public Player(int id, String name) {
		this.id = id;
		this.name = name;
		this.assignedTerritory = new ArrayList<>();
		this.playerCardList = new ArrayList<>();
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
	
	

	/**
	 * @return the playerCardList
	 */
	public List<Card> getPlayerCardList() {
		return playerCardList;
	}

	/**
	 * @param playerCardList the playerCardList to set
	 */
	public void setPlayerCardList(List<Card> playerCardList) {
		this.playerCardList = playerCardList;
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
}