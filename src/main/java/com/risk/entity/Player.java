package com.risk.entity;

import java.util.ArrayList;
import java.util.List;

import com.risk.strategy.PlayerBehaviorStrategy;

/**
 * This is an entity class for Player with id, name, armies and assignedTerritories as its member variables.
 * @author rahul
 * @version 1.0.0
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
	
	private PlayerBehaviorStrategy strategy;
	
	

	/**
	 * Constructor for Player
	 * 
	 * @param id
	 *            for player id
	 * 
	 * @param name
	 *            for player name 
	 */
	public Player(int id) {
		this.id = id;
		this.assignedTerritory = new ArrayList<>();
		this.playerCardList = new ArrayList<>();
	}

	/**
	 * Get Id 
	 * 
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set Id
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
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
	 * Get Armies
	 * 
	 * @return armies
	 */
	public int getArmies() {
		return armies;
	}

	/**
	 * Set Armies
	 * 
	 * @param armies
	 *            the armies to set
	 */
	public void setArmies(int armies) {
		this.armies = armies;
	}

	/**
	 * Get Assigned Territory
	 * 
	 * @return assignedTerritory
	 */
	public List<Territory> getAssignedTerritory() {
		return assignedTerritory;
	}

	/**
	 * Set Assigned Territory
	 * 
	 * @param assignedTerritory
	 *            the assignedTerritory to set
	 */
	public void setAssignedTerritory(List<Territory> assignedTerritory) {
		this.assignedTerritory = assignedTerritory;
	}
	
	/**
	 * Get Player Card List
	 * 
	 * @return playerCardList
	 */
	public List<Card> getPlayerCardList() {
		return playerCardList;
	}

	/**
	 * Set PlayerCardList
	 * 
	 * @param playerCardList 
	 * 						the playerCardList to set
	 */
	public void setPlayerCardList(List<Card> playerCardList) {
		this.playerCardList = playerCardList;
	}
	
	public PlayerBehaviorStrategy getStrategy() {
		return strategy;
	}

	public void setStrategy(PlayerBehaviorStrategy strategy) {
		this.strategy = strategy;
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
		return player.getId() == id;
	}
}