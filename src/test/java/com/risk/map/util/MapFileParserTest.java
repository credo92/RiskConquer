package com.risk.map.util;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.risk.entity.Map;
import com.risk.exception.InvalidMapException;

/**
 * Test class for {@link MapFileParser}
 * @author Garvpreet Singh
 * @version 1.0.0
 */
public class MapFileParserTest {
	
	/**
	 * The @file.
	 */
	File file;
	
	/**
	 * The @mfp map file parser
	 */
	static MapFileParser mfp;
	
	/**
	 * The @classLoader class loader
	 */
	ClassLoader classLoader;
	
	/**
	 * The @validFile
	 */
	String validFile = "World.map";
	
	/**
	 * The @invalidFileContinent
	 */
	String invalidFileContinent = "World_Invalid_Continent.map";
	
	/**
	 * The @invalidFileTerritoryAbandoned
	 */
	String invalidFileTerritoryAbandoned = "World_Invalid_Territory_Abandoned.map";
	
	/**
	 * The @invalidFileTerritorySubgraph
	 */
	String invalidFileTerritorySubgraph = "World_Invalid_Territory_Subgraph.map";
	
	/**
	 * The @invalidFileContinentSubgraph
	 */
	String invalidFileContinentSubgraph = "World_Invalid_Continent_Subgraph.map";
	
	/**
	 * The @invalidFileTerritoryDuplication
	 */
	String invalidFileTerritoryDuplication = "World_Invalid_Territory_Duplication.map";
	
	/**
	 * This method is invoked at the start of the test class.
	 */
	@BeforeClass
	public static void beforeClass() {
		mfp = new MapFileParser();
	}	
	
	/**
	 * This method is invoked at the start of the every test method.
	 * @throws IOException IOException
	 */
	@Before
	public void beforeTest() throws IOException {		
		classLoader = getClass().getClassLoader();		
	}	
	
	/** 
	 * This method is used to test the size of continent.
	 * @throws InvalidMapException InvalidMapException
	 */
	@Test
	public void checkValidNumberOfContinents() throws InvalidMapException {
		file = new File(classLoader.getResource(validFile).getFile());
		Map map = mfp.parseAndReadMapFile(file);
		Assert.assertEquals(map.getContinents().size(), 7);
	}
	
	/**
	 * This method is used to test if a continent has a territory in it.
	 * @throws InvalidMapException InvalidMapException
	 */
	@Test (expected=InvalidMapException.class)
	public void checkInvalidMapForContinentWithoutTerritory() throws InvalidMapException {
		file = new File(classLoader.getResource(invalidFileContinent).getFile());
		MapFileParser mfp = new MapFileParser();
		mfp.parseAndReadMapFile(file);
	}
	
	/**
	 * This method is used to test if a territory belongs to any continent or not.
	 * @throws InvalidMapException InvalidMapException
	 */
	@Test (expected=InvalidMapException.class)
	public void checkInvalidMapForAbandonedTerritory() throws InvalidMapException {
		file = new File(classLoader.getResource(invalidFileTerritoryAbandoned).getFile());
		MapFileParser mfp = new MapFileParser();
		mfp.parseAndReadMapFile(file);
	}
	
	/**
	 * This method is used to test if a set of territories are connected or not.
	 * @throws InvalidMapException InvalidMapException
	 */
	@Test (expected=InvalidMapException.class)
	public void checkInvalidMapForTerritorySubgraph() throws InvalidMapException {
		file = new File(classLoader.getResource(invalidFileTerritorySubgraph).getFile());
		MapFileParser mfp = new MapFileParser();
		mfp.parseAndReadMapFile(file);
	}
	
	/**
	 * This method is used to test if a set of continent are connected or not.
	 * @throws InvalidMapException InvalidMapException
	 */
	@Test (expected=InvalidMapException.class)
	public void checkInvalidMapForContinentSubgraph() throws InvalidMapException {
		file = new File(classLoader.getResource(invalidFileContinentSubgraph).getFile());
		MapFileParser mfp = new MapFileParser();
		mfp.parseAndReadMapFile(file);
	}
	
	/**
	 * This method is used to test if a territory exists in multiple continents.
	 * @throws InvalidMapException InvalidMapException
	 */
	@Test (expected=InvalidMapException.class)
	public void checkInvalidMapForTerritoryDuplication() throws InvalidMapException {
		file = new File(classLoader.getResource(invalidFileTerritoryDuplication).getFile());
		MapFileParser mfp = new MapFileParser();
		mfp.parseAndReadMapFile(file);
	}
}