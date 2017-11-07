package com.risk.model;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import com.risk.entity.Territory;

public class DiceModelTest {

	/**
	 * The @diceModel .
	 */
	static DiceModel diceModel;
	/**
	 * The @attackingTerritory .
	 */
	static Territory attackingTerritory;

	/**
	 * The @defendingTerritory .
	 */
	static Territory defendingTerritory;

	/**
	 * The @attackerDiceValues .
	 */
	static List<Integer> attackerDiceValues;

	/**
	 * The @defenderDiceValues .
	 */
	static List<Integer> defenderDiceValues;

	/**
	 * This method is invoked at the start of the test class.
	 */
	@BeforeClass
	public static void beforeClass() {
		attackingTerritory = new Territory();
		defendingTerritory = new Territory();	
		attackerDiceValues = new ArrayList<Integer>();
		defenderDiceValues = new ArrayList<Integer>();
		diceModel = new DiceModel(attackingTerritory, defendingTerritory);
		

	}	

	public void setRandomArmies() {
		for(int i =0; i <3; i++) {
			attackerDiceValues.add(diceModel.randomNumber());
		}
		for(int i =0; i <2; i++) {
			defenderDiceValues.add(diceModel.randomNumber());
		}
	}

	/**
	 * This method is invoked at the start of all the test methods.
	 */
	@Before
	public void beforeTest() {
		attackingTerritory.setArmies(3);
		defendingTerritory.setArmies(3);
	}

	@Test
	public void moreDiceRollAvailable() {
		assertTrue(attackingTerritory.getArmies() > 2);
		assertTrue(defendingTerritory.getArmies() > 0);
		assertEquals(true, diceModel.moreDiceRollAvailable());
	}

	
	@Test
	public void getPlayResultAfterDiceThrown() {
		setRandomArmies();
		List<String> playResult = new ArrayList<>();
		for (Integer defenderDiceValue : defenderDiceValues) {
			for (Integer attackerDiceValue : attackerDiceValues) {
				diceModel.updateArmiesAfterAttack(defenderDiceValue, attackerDiceValue, playResult);
				break;
			}
			attackerDiceValues.remove(0);
		}
		
		assertTrue(playResult.get(0).equals("Defender lost 1 army") ||
				playResult.get(0).equals("Attacker lost 1 army."));
		
	}

}
