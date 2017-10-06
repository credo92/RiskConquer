package com.app.team19;

import java.util.List;


/**
 * @author Garvpreet Singh.
 *
 */
public class Territory {
	private String name;
	private int xCoordinate;
	private int yCoordinate;
	private String belongToContinent;
	private List<Territory> adjacentTerritories;

	/**
	 * @return
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
	public String getBelongToContinent() {
		return belongToContinent;
	}

	/**
	 * @param belongToContinent
	 *            the belongToContinent to set
	 */
	public void setBelongToContinent(String belongToContinent) {
		this.belongToContinent = belongToContinent;
	}

}
