package com.risk.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rahul
 *
 */
public class Player {

	private int id;

	private String name;

	private int armies;

	private List<Territory> assignedTerritory;

	public Player(int id, String name, int armies) {
		this.id = id;
		this.name = name;
		this.armies = armies;
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
}
