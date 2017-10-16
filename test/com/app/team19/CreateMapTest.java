package com.app.team19;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Test Class for CreateMapTest.java 
 * Tests that Root Node should not be Null
 * @author Vipul Srivastav
 * 
 *
 */
public class CreateMapTest {
	
	@Test
	public void testRootNotNull() {
		CreateMap createMap = new CreateMap();
		assertNotNull(createMap.rootNode);
	}

}
