package com.risk.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.risk.controller.GamePlayController;
import com.risk.entity.Map;
import com.risk.exception.InvalidMapException;
import com.risk.map.util.MapFileParser;
import com.risk.map.util.MapUtil;

/**
 * Test class for LoadGamePlay 
 * @author Garvpreet Singh
 * @version 1.0.1
 */

public class LoadGamePlayTest {
	
	/**
	 * The @file.
	 */
	File file;
	
	/**
	 * The @loadGamePlay class object
	 */
	static LoadGamePlay loadGamePlay;
	
	/**
	 * The @classLoader class loader
	 */
	ClassLoader classLoader;
	
	/**
	 * The @validFile
	 */
	String validFile = "ValidGameSaved.ser";
	
	/**
	 * The @invalidFile
	 */
	String invalidFile = "InvalidGameSaved.ser";
	
	/**
	 * This method is invoked at the start of the test class.
	 */
	@BeforeClass
	public static void beforeClass() {
		loadGamePlay = new LoadGamePlay();
	}	
	
	/**
	 * This method is invoked at the start of the every test method.
	 */
	@Before
	public void beforeTest() {
		classLoader = getClass().getClassLoader();
	}	
	
	/** 
	 * This method is used to test the size of continent.
	 * @throws InvalidMapException InvalidMapException
	 */
	
	public void loadSavedFile()  {		
		String validFile = "D:/EclipseWorkspace/RiskConquer/src/main/resources/ValidGameSaved.ser";
		file = new File(validFile);
		System.out.println(file.getAbsolutePath());
		System.out.println(file.getPath());
		GamePlayController gmc = loadGamePlay.loadSavedFile(file);
		System.out.println(gmc);
	}

}