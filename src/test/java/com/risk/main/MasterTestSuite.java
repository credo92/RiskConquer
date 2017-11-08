package com.risk.main;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.risk.map.util.UtilTestSuite;
import com.risk.model.ModelTestSuite;
import com.risk.validate.ValidateTestSuite;

/**
 * This is a master test suite of the project.
 * @author Garvpreet Singh
 * @version 1.0.0
 */
@RunWith(Suite.class)
@SuiteClasses({UtilTestSuite.class, ModelTestSuite.class, ValidateTestSuite.class})
public class MasterTestSuite {
}
