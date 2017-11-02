package com.risk.model;

import java.util.ArrayList;
import java.util.List;

import com.risk.entity.Territory;

/**
 * @author Gurpreet Singh DiceModel model to send data
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

	public String compareTwoNumbers(int attackerValue, int defenderValue) {
		String playerWinCheck;
		if (attackerValue > defenderValue) {

			playerWinCheck = "attacker";

		} else if (attackerValue < defenderValue) {

			playerWinCheck = "defender";

		} else {

			playerWinCheck = "tie";
		}

		return playerWinCheck;
	}

	public boolean checkIfDiceHasSameValue(List<Integer> bestAttackerValue, List<Integer> bestDefenderValue, 
			int sizeCheckForLoop) {
		for(int i = 0 ; i < sizeCheckForLoop; i++) {
			if(bestAttackerValue.get(i) == bestDefenderValue.get(i)) {
				return true;
			}
		}
		return false;

	}

	public List<String> getPlayResultAfterDiceThrown(List<Integer> bestAttackerValue, List<Integer> bestDefenderValue) {
		playResult.clear();
		int sizeCheckForLoop = 0;
		if(!bestAttackerValue.isEmpty() && !bestDefenderValue.isEmpty()) {
			if(bestAttackerValue.size() > bestDefenderValue.size()) {
				sizeCheckForLoop = bestDefenderValue.size();
			}else if(bestDefenderValue.size() > bestAttackerValue.size()) {
				sizeCheckForLoop = bestAttackerValue.size();
			}else {
				sizeCheckForLoop = bestDefenderValue.size();
			}
		}

		if(checkIfDiceHasSameValue(bestAttackerValue, bestDefenderValue, sizeCheckForLoop)) {
			for(int i = 0 ; i < sizeCheckForLoop; i++) {
				playResult.add(compareTwoNumbers((int) bestAttackerValue.get(i), (int) bestDefenderValue.get(i)));
			}	
		}else {
			playResult.add(compareTwoNumbers((int) bestAttackerValue.get(0), (int) bestDefenderValue.get(0)));
		}
		return playResult;

	}

	public int randomNumber() {
		return (int) (Math.random() * 6) + 1;
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
	 * @param attackingTerritory
	 *            the attackingTerritory to set
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
	 * @param defendingTerritory
	 *            the defendingTerritory to set
	 */
	public void setDefendingTerritory(Territory defendingTerritory) {
		this.defendingTerritory = defendingTerritory;
	}

	public int deductArmyFromAttacker() {
		if(attackingTerritory.getArmies() >= 2) {
			int newArmies = attackingTerritory.getArmies() - 1;
			attackingTerritory.setArmies(newArmies);
		}
		return attackingTerritory.getArmies();
	}

	public int deductArmyFromDefender() {
		if(defendingTerritory.getArmies() > 0) {
			int newArmies = defendingTerritory.getArmies() - 1;
			defendingTerritory.setArmies(newArmies);
		}
		System.out.println("armies on defend"+ defendingTerritory.getArmies());
		return defendingTerritory.getArmies();
	}

	public boolean checkIfAttackerContinue() {
		if(attackingTerritory.getArmies() >= 2 && defendingTerritory.getArmies() > 0) {
			return true;
		}	
		return false;
	}



}