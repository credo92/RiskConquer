package com.risk.model;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.risk.entity.Territory;

public class DiceModelTest {
	
	final Territory attackingTerritory = new Territory();
	final Territory defendingTerritory = new Territory();

	
	@Before public void initialize() {
		attackingTerritory.setArmies(3);
		defendingTerritory.setArmies(0);
	    }
	
	@Test
	public void moreDiceRollAvailable() {
		assertTrue(attackingTerritory.getArmies() > 2);
		assertTrue(defendingTerritory.getArmies() <= 0);
	}
	
	@Test
	public void updateArmiesAfterAttack() {
		
	}
	


}
