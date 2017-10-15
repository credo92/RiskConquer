package com.risk.validate;

import static org.junit.Assert.*;
import com.risk.entity.Continent;


import org.junit.Test;

public class MapValidatorTest {

	@Test
	public void test() {
		MapValidator mapValidator = new MapValidator();
		assertTrue(mapValidator.getTerritorySize() > 1);
	}

}
