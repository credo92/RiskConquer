package com.risk.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

import com.risk.entity.Territory;
import com.risk.map.util.GameUtil;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * @author Gurpreet Singh DiceModel model to send data
 * @version 1.0.1
 */
public class DiceModel extends Observable {

	private Territory attackingTerritory;

	private Territory defendingTerritory;

	private List<Integer> attackerDiceValues;

	private List<Integer> defenderDiceValues;
	
	private int numOfTerritoriesWon;

	public DiceModel(Territory attackingTerritory, Territory defendingTerritory) {
		this.attackingTerritory = attackingTerritory;
		this.defendingTerritory = defendingTerritory;
		attackerDiceValues = new ArrayList<>();
		defenderDiceValues = new ArrayList<>();
		numOfTerritoriesWon = 0;

	}

	public List<String> getPlayResultAfterDiceThrown() {
		List<String> playResult = new ArrayList<>();
		Collections.sort(attackerDiceValues, Collections.reverseOrder());
		Collections.sort(defenderDiceValues, Collections.reverseOrder());

		for (Integer defenderDiceValue : defenderDiceValues) {
			for (Integer attackerDiceValue : attackerDiceValues) {
				updateArmiesAfterAttack(defenderDiceValue, attackerDiceValue, playResult);
				break;
			}
			attackerDiceValues.remove(0);
		}
		return playResult;

	}

	public void updateArmiesAfterAttack(Integer defenderDiceValue, Integer attackerDiceValue, List<String> playResult) {
		if (attackerDiceValue.compareTo(defenderDiceValue) == 0) {
			playResult.add("Attacker lost 1 army.");
			if (attackingTerritory.getArmies() > 1) {
				attackingTerritory.setArmies(attackingTerritory.getArmies() - 1);
			}
		} else if (attackerDiceValue.compareTo(defenderDiceValue) > 0) {
			playResult.add("Defender lost 1 army");
			if (defendingTerritory.getArmies() > 0) {
				defendingTerritory.setArmies(defendingTerritory.getArmies() - 1);
			}
		} else {
			playResult.add("Attacker lost 1 army.");
			if (attackingTerritory.getArmies() > 1) {
				attackingTerritory.setArmies(attackingTerritory.getArmies() - 1);
			}
		}
	}

	public void cancelDiceRoll() {
		setChanged();
		notifyObservers("rollDiceComplete");
	}

	public void moveAllArmies() {
		int attckingArmies = getAttackingTerritory().getArmies();
		getAttackingTerritory().setArmies(1);
		getDefendingTerritory().setArmies(attckingArmies - 1);
		reassignTerritory();
		setChanged();
		notifyObservers("rollDiceComplete");
	}

	public void skipMoveArmy() {
		int attckingArmies = getAttackingTerritory().getArmies();
		getAttackingTerritory().setArmies(attckingArmies - 1);
		getDefendingTerritory().setArmies(1);
		setChanged();
		notifyObservers("rollDiceComplete");
	}

	public void moveArmies(int armiesToMove, Label message, Button moveArmies) {
		int currentArmies = getAttackingTerritory().getArmies();
		if (currentArmies <= armiesToMove) {
			message.setVisible(true);
			message.setText("You can move a miximum of " + (currentArmies - 1) + " armies");
			return;
		} else {
			getAttackingTerritory().setArmies(currentArmies - armiesToMove);
			getDefendingTerritory().setArmies(armiesToMove);
			reassignTerritory();
			GameUtil.closeScreen(moveArmies);
			setChanged();
			notifyObservers("rollDiceComplete");
		}
	}

	public void reassignTerritory() {
		List<Territory> defendersTerritories = defendingTerritory.getPlayer().getAssignedTerritory();
		defendersTerritories.remove(defendingTerritory);

		defendingTerritory.setPlayer(attackingTerritory.getPlayer());
		attackingTerritory.getPlayer().getAssignedTerritory().add(defendingTerritory);

	}

	public int randomNumber() {
		return (int) (Math.random() * 6) + 1;
	}

	public boolean moreDiceRollAvailable() {
		boolean diceRollAvailable = true;
		if (attackingTerritory.getArmies() < 2 || defendingTerritory.getArmies() <= 0) {
			diceRollAvailable = false;
		}
		return diceRollAvailable;
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

	/**
	 * @return the numOfTerritoriesWon
	 */
	public int getNumOfTerritoriesWon() {
		return numOfTerritoriesWon;
	}

	/**
	 * @param numOfTerritoriesWon the numOfTerritoriesWon to set
	 */
	public void setNumOfTerritoriesWon(int numOfTerritoriesWon) {
		this.numOfTerritoriesWon = numOfTerritoriesWon;
	}
	
}