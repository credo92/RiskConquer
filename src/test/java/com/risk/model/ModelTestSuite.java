package com.risk.model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suit for model test classes
 * @author Garvpreet Singh
 * @version 1.0.0
 */
@RunWith(Suite.class)
@SuiteClasses({MapModelTest.class, PlayerGamePhaseTest.class, CardModelTest.class, DiceModelTest.class, GameModelTest.class,TournamentModelTest.class})
public class ModelTestSuite {
}