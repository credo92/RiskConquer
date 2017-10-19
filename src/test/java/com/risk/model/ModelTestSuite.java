package com.risk.model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suit for model test classes
 * @author Garvpreet Singh
 *
 */
@RunWith(Suite.class)
@SuiteClasses({GameModelTest.class, MapModelTest.class})
public class ModelTestSuite {
}