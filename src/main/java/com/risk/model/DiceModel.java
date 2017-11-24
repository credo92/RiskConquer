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
 * DiceModel model to send data
 * @author Gurpreet Singh 
 * @version 1.0.0
 */
public class DiceModel extends Observable {

	/**
	 * The @attackingTerritory .
	 */
	private Territory attackingTerritory;

	/**
	 * The @defendingTerritory .
	 */
	private Territory defendingTerritory;

	/**
	 * The @attackerDiceValues .
	 */
	private List<Integer> attackerDiceValues;

	/**
	 * The @defenderDiceValues .
	 */
	private List<Integer> defenderDiceValues;

	/**
	 * The @numOfTerritoriesWon .
	 */
	private int numOfTerritoriesWon;

	/**
	 * Constructor for DiceModel
	 * 
	 * @param attackingTerritory
	 *            reference to get details about attacking territory
	 * 
	 * @param defendingTerritory
	 *            reference to get details about defending territory
	 */
	public DiceModel(Territory attackingTerritory, Territory defendingTerritory) {
		this.attackingTerritory = attackingTerritory;
		this.defendingTerritory = defendingTerritory;
		attackerDiceValues = new ArrayList<>();
		defenderDiceValues = new ArrayList<>();
		numOfTerritoriesWon = 0;

	}

	/**
	 * Get Play Result after the dice is thrown
	 * 
	 * @return List list of players.
	 */
	public List<String> getPlayResultAfterDiceThrown() {
		List<String> playResult = new ArrayList<>();
		Collections.sort(attackerDiceValues, Collections.reverseOrder());
		Collections.sort(defenderDiceValues, Collections.reverseOrder());
		System.out.println("Number of attacker dice" + attackerDiceValues.size());
		System.out.println("Number of defender dice" + defenderDiceValues.size());
		
		for (Integer defenderDiceValue : defenderDiceValues) {
			for (Integer attackerDiceValue : attackerDiceValues) {
				updateArmiesAfterAttack(defenderDiceValue, attackerDiceValue, playResult);
				break;
			}
			if (attackerDiceValues.size() >= 1) {
				attackerDiceValues.remove(0);
			}
		}
		return playResult;
	}

	/**
	 * Update Armies After attack
	 * 
	 * @param defenderDiceValue
	 *            Integer defenderDiceValue
	 * @param attackerDiceValue
	 *            Integer attackerDiceValue
	 * @param playResult
	 *            List playResult
	 */
	public void updateArmiesAfterAttack(Integer defenderDiceValue, Integer attackerDiceValue, List<String> playResult) {
		System.out.println("Attacking territory armies" + attackingTerritory.getArmies());
		System.out.println("Defending territory armies" + defendingTerritory.getArmies());
		System.out.println("attackerDiceValue   " + attackerDiceValue);
		System.out.println("defenderDiceValue   " + defenderDiceValue);
		
		
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
		
		System.out.println("After attack Attacking territory armies" + attackingTerritory.getArmies());
		System.out.println("After attack Defending territory armies" + defendingTerritory.getArmies());
	}

	/**
	 * Cancel Dice Roll
	 */
	public void cancelDiceRoll() {
		setChanged();
		notifyObservers("rollDiceComplete");
	}

	/**
	 * Move All Armies
	 */
	public void moveAllArmies() {
		int attckingArmies = getAttackingTerritory().getArmies();
		getAttackingTerritory().setArmies(1);
		getDefendingTerritory().setArmies(attckingArmies - 1);
		reassignTerritory();
		setChanged();
		notifyObservers("rollDiceComplete");
	}

	/**
	 * Skip Move Army
	 */
	public void skipMoveArmy() {
		int attckingArmies = getAttackingTerritory().getArmies();
		getAttackingTerritory().setArmies(attckingArmies - 1);
		getDefendingTerritory().setArmies(1);
		reassignTerritory();
		setChanged();
		notifyObservers("rollDiceComplete");
	}

	/**
	 * Move the desired number of armies.
	 * 
	 * @param armiesToMove
	 *            armies to move
	 * @param message
	 *            message
	 * @param moveArmies
	 *            movearmies button refrence
	 */
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

	/**
	 * Reassign Territory
	 */
	public void reassignTerritory() {
		List<Territory> defendersTerritories = defendingTerritory.getPlayer().getAssignedTerritory();
		defendersTerritories.remove(defendingTerritory);

		defendingTerritory.setPlayer(attackingTerritory.getPlayer());
		attackingTerritory.getPlayer().getAssignedTerritory().add(defendingTerritory);

	}

	/**
	 * @return Int randomNumber
	 */
	public int randomNumber() {
		return (int) (Math.random() * 6) + 1;
	}

	/**
	 * Check if more dice role available
	 * @return diceRollAvailable
	 */
	public boolean moreDiceRollAvailable() {
		boolean diceRollAvailable = true;
		if (attackingTerritory.getArmies() < 2 || defendingTerritory.getArmies() <= 0) {
			diceRollAvailable = false;
		}
		return diceRollAvailable;
	}

	/**
	 * Get Attacking Territory
	 * @return Territory attacking territory
	 */
	public Territory getAttackingTerritory() {
		return attackingTerritory;
	}

	/**
	 * Set Attacking Territory
	 * @param attackingTerritory
	 *            the attackingTerritory to set
	 */
	public void setAttackingTerritory(Territory attackingTerritory) {
		this.attackingTerritory = attackingTerritory;
	}

	/**
	 * Get Defending Territory
	 * @return the defendingTerritory
	 */
	public Territory getDefendingTerritory() {
		return defendingTerritory;
	}

	/**
	 * Set Defending Territory
	 * @param defendingTerritory
	 *            the defendingTerritory to set
	 */
	public void setDefendingTerritory(Territory defendingTerritory) {
		this.defendingTerritory = defendingTerritory;
	}

	/**
	 * Get Attacker Dice Values
	 * @return the attackerDiceValues
	 */
	public List<Integer> getAttackerDiceValues() {
		return attackerDiceValues;
	}

	/**
	 * Set Attacker Dice Values
	 * @param attackerDiceValues
	 *            the attackerDiceValues to set
	 */
	public void setAttackerDiceValues(List<Integer> attackerDiceValues) {
		this.attackerDiceValues = attackerDiceValues;
	}

	/**
	 * Get Defender Dice Values
	 * @return defenderDiceValues
	 */
	public List<Integer> getDefenderDiceValues() {
		return defenderDiceValues;
	}

	/**
	 * Set Defender Dice Values
	 * @param defenderDiceValues
	 *            the defenderDiceValues to set
	 */
	public void setDefenderDiceValues(List<Integer> defenderDiceValues) {
		this.defenderDiceValues = defenderDiceValues;
	}

	/**
	 * Get Number Of Territories Won
	 * @return the numOfTerritoriesWon
	 */
	public int getNumOfTerritoriesWon() {
		return numOfTerritoriesWon;
	}

	/**
	 * Set Number Of Territories Won
	 * @param numOfTerritoriesWon the numOfTerritoriesWon to set
	 */
	public void setNumOfTerritoriesWon(int numOfTerritoriesWon) {
		this.numOfTerritoriesWon = numOfTerritoriesWon;
	}

}