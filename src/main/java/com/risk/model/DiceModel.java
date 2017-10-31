package com.risk.model;

import com.risk.entity.Player;
import com.risk.entity.Territory;

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
}
