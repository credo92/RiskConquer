package com.risk.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.risk.entity.Territory;
import com.risk.map.util.MapUtil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author Gurpreet Singh
 * DiceRollController controller to control all the
 * @version 1.0.1
 */
public class DiceRollController implements Initializable{
	


	
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
	
	private Territory attackingTerritory;
	private Territory defendingTerritory;
	
	public DiceRollController(Territory attackingTerritory, Territory defendingTerritory) {
		this.attackingTerritory = attackingTerritory;
		this.defendingTerritory = defendingTerritory;
		
	}
	
	public void loadAttackerInfo() {
		attackerPlayerName.setText(attackingTerritory.getPlayer().getName());
		attackerTerritoryName.setText(attackingTerritory.getName());
		attackerArmies.setText(String.valueOf(attackingTerritory.getArmies()));
	}
	
	public void loadDefenderInfo() {
		defenderPlayerName.setText(defendingTerritory.getPlayer().getName());
		defenderTerritoryName.setText(defendingTerritory.getName());
		defenderArmies.setText(String.valueOf(defendingTerritory.getArmies()));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// TODO Auto-generated method stub
		loadAttackerInfo();
		loadDefenderInfo();
		roll.setOnAction((event) -> {
			roll();
		});
		//winnerName.setText("hello");

	}
	
   public void roll() {
	winnerName.setText("roll clicked");
	attackingTerritory.setArmies(5);
	}
	
	
}