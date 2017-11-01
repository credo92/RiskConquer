package com.risk.model;

import com.risk.entity.Player;
import com.risk.entity.Territory;

/**
 * @author Gurpreet Singh
 * DiceModel model to send data
 * @version 1.0.1
 */
public class DiceModel {

	private Territory attackingTerritory;
	
	private Territory defendingTerritory;
	
	public DiceModel(Territory attackingTerritory, Territory defendingTerritory) {
		this.attackingTerritory = attackingTerritory;
		this.defendingTerritory = defendingTerritory;
		
	}
	
		
	public boolean checkIfAttackIsValid() {
		if(attackingTerritory != null && defendingTerritory != null ) {
			if(attackingTerritory.getPlayer().getName() != defendingTerritory.getPlayer().getName()) {
				return true;
			}	
		}
		
		return false;	
	}
	
	public int getArmyCountOnAttackingTerritory() {
		return attackingTerritory.getArmies();
	}
	
	public int getArmyCountOnDefendingTerritory() {
		return defendingTerritory.getArmies();
	}
	
	public int randomNumber() {
		return (int) (Math.random() * 6) + 1;
	}
	
}
