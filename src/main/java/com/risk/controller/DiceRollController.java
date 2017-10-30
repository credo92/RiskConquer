package com.risk.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * @author Gurpreet Singh
 * DiceRollController controller to control all the
 * @version 1.0.1
 */
public class DiceRollController {

	/**
	 * The @roll button.
	 */
	@FXML
	private Button roll; 
	
	/**
	 * The @attackerPlayerName label.
	 */
	@FXML
	private Label attackerPlayerName;
	
	/**
	 * The @defenderPlayerName label.
	 */
	@FXML
	private Label defenderPlayerName;
	
	/**
	 * The @attackerTerritoryName label.
	 */
	@FXML
	private Label attackerTerritoryName;
	
	/**
	 * The @attackerArmies label.
	 */
	@FXML
	private Label attackerArmies;
	
	/**
	 * The @defenderTerritoryName label.
	 */
	@FXML
	private Label defenderTerritoryName;
	
	/**
	 * The @defenderArmies label.
	 */
	@FXML
	private Label defenderArmies;
	
	
	/**
	 * The @attackerDice1 checkbox.
	 */
	@FXML
	private CheckBox attackerDice1;

	
	/**
	 * The @attackerDice2 checkbox.
	 */
	@FXML
	private CheckBox attackerDice2;
	
	
	/**
	 * The @attackerDice3 checkbox.
	 */
	@FXML
	private CheckBox attackerDice3;
	
	
	/**
	 * The @defenderDice1 checkbox.
	 */
	@FXML
	private CheckBox defenderDice1;
	
	
	/**
	 * The @defenderDice2 checkbox.
	 */
	@FXML
	private CheckBox defenderDice2;
	
	
	/**
	 * The @cancel button.
	 */
	@FXML
	private Button cancel;
		
	/**
	 * The @numberOfArmiesLabel label.
	 */
	@FXML
	private Label numberOfArmiesLabel;
	
	/**
	 * The @numberOfArmiesInput textField.
	 */
	@FXML
	private TextField numberOfArmiesInput;
	
	/**
	 * The @numberOfArmiesMove button.
	 */
	@FXML
	private Button numberOfArmiesMove;
	
	/**
	 * The @numberOfArmiesCancel button.
	 */
	@FXML
	private Button numberOfArmiesCancel;
	
	/**
	 * The @attackerDice1Value label.
	 */
	@FXML
	private Label attackerDice1Value;
	
	/**
	 * The @attackerDice2Value label.
	 */
	@FXML
	private Label attackerDice2Value;
	
	/**
	 * The @attackerDice3Value label.
	 */
	@FXML
	private Label attackerDice3Value;
	
	/**
	 * The @defenderDice1Value label.
	 */
	@FXML
	private Label defenderDice1Value;
	
	/**
	 * The @adefenderDice2Value label.
	 */
	@FXML
	private Label defenderDice2Value;
	
	/**
	 * The @winnerName label.
	 */
	@FXML
	private Label winnerName;
	
	
}
