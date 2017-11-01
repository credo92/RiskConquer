package com.risk.model;

import com.risk.entity.Territory;

/**
 * @author Gurpreet Singh DiceModel model to send data
 * @version 1.0.1
 */
public class DiceModel {

	private Territory attackingTerritory;

	private Territory defendingTerritory;

	public DiceModel(Territory attackingTerritory, Territory defendingTerritory) {
		this.attackingTerritory = attackingTerritory;
		this.defendingTerritory = defendingTerritory;

	}

	public int getArmyCountOnAttackingTerritory() {
		return attackingTerritory.getArmies();
	}

	public int getArmyCountOnDefendingTerritory() {
		return defendingTerritory.getArmies();
	}
	
	/**
	 * @return the attackingTerritory
	 */
	public Territory getAttackingTerritory() {
		return attackingTerritory;
	}

	/**
	 * @param attackingTerritory the attackingTerritory to set
	 */
	public void setAttackingTerritory(Territory attackingTerritory) {
		this.attackingTerritory = attackingTerritory;
	}

	/**
	 * @return the defendingTerritory
	 */
	public Territory getDefendingTerritory() {
		return defendingTerritory;
	}

	/**
	 * @param defendingTerritory the defendingTerritory to set
	 */
	public void setDefendingTerritory(Territory defendingTerritory) {
		this.defendingTerritory = defendingTerritory;
	}


}