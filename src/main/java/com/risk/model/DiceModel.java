package com.risk.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.risk.entity.Territory;

/**
 * @author Gurpreet Singh DiceModel model to send data
 * @version 1.0.1
 */
public class DiceModel {

	private Territory attackingTerritory;

	private Territory defendingTerritory;

	private List<Integer> attackerDiceValues;

	private List<Integer> defenderDiceValues;

	public DiceModel(Territory attackingTerritory, Territory defendingTerritory) {
		this.attackingTerritory = attackingTerritory;
		this.defendingTerritory = defendingTerritory;
		attackerDiceValues = new ArrayList<>();
		defenderDiceValues = new ArrayList<>();

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

	public List<String> getPlayResultAfterDiceThrown() {
		List<String> playResult = new ArrayList<>();
		Collections.sort(attackerDiceValues);
		Collections.sort(defenderDiceValues);

		for (Integer defenderDiceValue : defenderDiceValues) {
			for (Integer attackerDiceValue : attackerDiceValues) {
				updateArmiesAfterAttack(defenderDiceValue, attackerDiceValue, playResult);
			}
		}
		return playResult;

	}

	public void updateArmiesAfterAttack(Integer defenderDiceValue, Integer attackerDiceValue, List<String> playResult) {
		if (attackerDiceValue.compareTo(defenderDiceValue) == 0) {
			playResult.add("Defending territory won!");
			attackingTerritory.setArmies(attackingTerritory.getArmies() - 1);
		} else if (attackerDiceValue.compareTo(defenderDiceValue) > 0) {
			playResult.add("Attacking territory won!");
			defendingTerritory.setArmies(defendingTerritory.getArmies() - 1);
		} else {
			playResult.add("Defending territory won!");
			attackingTerritory.setArmies(attackingTerritory.getArmies() - 1);
		}
	}

	public int randomNumber() {
		return (int) (Math.random() * 6) + 1;
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

	/**
	 * @return the attackerDiceValues
	 */
	public List<Integer> getAttackerDiceValues() {
		return attackerDiceValues;
	}

	/**
	 * @param attackerDiceValues
	 *            the attackerDiceValues to set
	 */
	public void setAttackerDiceValues(List<Integer> attackerDiceValues) {
		this.attackerDiceValues = attackerDiceValues;
	}

	/**
	 * @return the defenderDiceValues
	 */
	public List<Integer> getDefenderDiceValues() {
		return defenderDiceValues;
	}

	/**
	 * @param defenderDiceValues
	 *            the defenderDiceValues to set
	 */
	public void setDefenderDiceValues(List<Integer> defenderDiceValues) {
		this.defenderDiceValues = defenderDiceValues;
	}

}