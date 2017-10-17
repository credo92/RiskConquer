package com.risk.map.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Scanner;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test class for {@link MapFileParser}
 * @author Garvpreet Singh
 * @version 0.0.1
 */
public class MapFileParserTest {
	/**
	 * This method is invoked at the start of the test class.
	 */
	@BeforeClass
	public static void beforeClass() {
		System.out.println("Testing MapFileParser.java started");		
	}	
	
	@Before
	public void beforeTest() {
		//ClassLoader classLoader = getClass().getClassLoader();
		//File file = new File(classLoader.getResource("World.map").getFile());
	/*	String fileName = getClass().getResource("World.map").getFile();
		try {
			InputStream stream = new FileInputStream(new File(getClass().getResource("World.map").getPath()));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	*/	
		/*File file = new File(getClass().getClassLoader().getResource("World.map").getFile());
		System.out.println(file.getAbsolutePath());		
		
		StringBuilder result = new StringBuilder("");
		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				result.append(line).append("\n");
			}
			scanner.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(result.toString());	*/
	}
	
	/**
	 * This method is invoked at the end of the test class.
	 */
	@AfterClass
	public static void afterClass() {
		System.out.println("Testing MapFileParser.java completed");
	}	
	
	@Test
	public void testFunc() {
		System.out.println("hi");
		MapFileParser mp = new MapFileParser();
		Assert.assertEquals(true, true);
		Assert.assertNotNull(mp);
	}
}
