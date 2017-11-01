package com.risk.model;

import java.util.ArrayList;
import java.util.List;

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
	
	List<String> playResult = new ArrayList<>();
	
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
	
	public String compareTwoNumbers(int attackerValue, int defenderValue) {
		String playerWinCheck;
		if(attackerValue > defenderValue) {
			
			playerWinCheck = "attacker";
			
		}else if(attackerValue < defenderValue) {
			
			playerWinCheck = "defender";
			
		}else {
			
			playerWinCheck = "tie";
		}
		
		return playerWinCheck;
	}
	
	public int deductArmyFromAttacker(int currentArmies) {
		int newArmies = currentArmies - 1;
		return newArmies;
	}
	
	public int deductArmyFromDefender(int currentArmies) {
		int newArmies = currentArmies - 1;
		return newArmies;
	}
	
	public List<String> getPlayResultAfterDiceThrown(List<Integer> bestAttackerValue,List<Integer> bestDefenderValue) {
		playResult.clear();
		if(!bestAttackerValue.isEmpty() && !bestDefenderValue.isEmpty()) {
			if(bestAttackerValue.size() > 0 && bestAttackerValue.get(0) != null && bestDefenderValue.size() > 0 && bestDefenderValue.get(0) != null) {
				int attackerValue = (int) bestAttackerValue.get(0);
				int defenderValue = (int) bestDefenderValue.get(0);
				playResult.add(compareTwoNumbers(attackerValue, defenderValue));
			}
			if(bestAttackerValue.size() > 1 && bestAttackerValue.get(1) != null && bestDefenderValue.size() > 1 && bestDefenderValue.get(1) != null) {
				int attackerValue = (int) bestAttackerValue.get(1);
				int defenderValue = (int) bestDefenderValue.get(1);
				playResult.add(compareTwoNumbers(attackerValue, defenderValue));
			}
		}
		
		return playResult;
	}
	
	
}
