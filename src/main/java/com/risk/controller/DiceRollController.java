package com.risk.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import com.risk.entity.Territory;
import com.risk.map.util.MapUtil;
import com.risk.model.DiceModel;

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
import javafx.scene.layout.Pane;
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
	 * The @childPane pane.
	 */
	@FXML
	private Pane childPane;

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

	/**
	 * The @attackingTerritory list of territories attacker owns.
	 */
	private Territory attackingTerritory;

	/**
	 * The @defendingTerritory list of territories defender owns.
	 */
	private Territory defendingTerritory;

	/**
	 * The @diceModel reference to class DiceModel.
	 */
	private DiceModel diceModel;




	/**
	 * Constructor for DiceRollController
	 * 
	 * @param attackingTerritory
	 *            reference to get details about attacking territory
	 *   
	 * @param defendingTerritory
	 *            reference to get details about defending territory           
	 */
	public DiceRollController(Territory attackingTerritory, Territory defendingTerritory) {
		this.attackingTerritory = attackingTerritory;
		this.defendingTerritory = defendingTerritory;
		this.diceModel = new DiceModel(attackingTerritory, defendingTerritory);

	}

	/**
	 * Load attacker player information.
	 */
	public void loadAttackerInfo() {
		attackerPlayerName.setText(attackingTerritory.getPlayer().getName());
		attackerTerritoryName.setText(attackingTerritory.getName());
		attackerArmies.setText(String.valueOf(attackingTerritory.getArmies()));
	}

	/**
	 * Load defender player information.
	 */
	public void loadDefenderInfo() {
		defenderPlayerName.setText(defendingTerritory.getPlayer().getName());
		defenderTerritoryName.setText(defendingTerritory.getName());
		defenderArmies.setText(String.valueOf(defendingTerritory.getArmies()));
	}

	/*
	 * (non-Javadoc) Dice Roll controller initializer, loading player and territory data.
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// TODO Auto-generated method stub
		loadAttackerInfo();
		loadDefenderInfo();
		showDice();
		hideUnnecessaryElementOnStartUp();
		roll.setOnAction((event) -> {
			roll();
		});
		//winnerName.setText("hello");

	}

	/**
	 * Hide unnecessary elements on startup .
	 */
	public void hideUnnecessaryElementOnStartUp() {
		winnerName.setVisible(false);
		attackerDice1Value.setVisible(false);
		attackerDice2Value.setVisible(false);
		attackerDice3Value.setVisible(false);
		defenderDice1Value.setVisible(false);;
		defenderDice2Value.setVisible(false);
		childPane.setVisible(false);

	}

	/**
	 * Show dices according to number of armies .
	 */
	public void showDice() {
		if(diceModel.getArmyCountOnAttackingTerritory() >= 4 ) {
			attackerDice1.setVisible(true);
			attackerDice2.setVisible(true);
			attackerDice3.setVisible(true);
		}else if (diceModel.getArmyCountOnAttackingTerritory() >= 3) {
			attackerDice1.setVisible(true);
			attackerDice2.setVisible(true);
			attackerDice3.setVisible(false);
		}else if (diceModel.getArmyCountOnAttackingTerritory() >= 2) {
			attackerDice1.setVisible(true);
			attackerDice2.setVisible(false);
			attackerDice3.setVisible(false);
		}
		if(diceModel.getArmyCountOnDefendingTerritory() > 2 ) {
			defenderDice1.setVisible(true);
			defenderDice2.setVisible(true);
		}else if(diceModel.getArmyCountOnDefendingTerritory() >= 1) {
			defenderDice1.setVisible(true);
			defenderDice2.setVisible(false);	
		}
	}

	/**
	 * Set random values on each dice and set on label and make label visible .
	 */
	public void throwDice() {
		if(!attackerDice1.isSelected() && !attackerDice2.isSelected() && !attackerDice3.isSelected()) {
			MapUtil.infoBox("Please Select atleast one of the attacker dice", "Message", "");
		}else if(!defenderDice1.isSelected() && !defenderDice2.isSelected()){
			MapUtil.infoBox("Please Select atleast one of the defender dice", "Message", "");
			return;
		}
		if(attackerDice1.isSelected()) {
			attackerDice1Value.setText(String.valueOf(diceModel.randomNumber()));
			attackerDice1Value.setVisible(true);
		}
		if(attackerDice2.isSelected()) {
			attackerDice2Value.setText(String.valueOf(diceModel.randomNumber()));
			attackerDice2Value.setVisible(true);
		}
		if(attackerDice3.isSelected()) {
			attackerDice3Value.setText(String.valueOf(diceModel.randomNumber()));
			attackerDice3Value.setVisible(true);
		}
		if(defenderDice1.isSelected()) {
			defenderDice1Value.setText(String.valueOf(diceModel.randomNumber()));
			defenderDice1Value.setVisible(true);
		}
		if(defenderDice2.isSelected()) {
			defenderDice2Value.setText(String.valueOf(diceModel.randomNumber()));
			defenderDice2Value.setVisible(true);
		}
		
	}
	
	/**
	 * Returns list of attacker dice values in decreasing order.
	 */
	public List<Integer> getValuesFromAtatckerDice(){
		List<Integer> bestAttackerValue = new ArrayList<>();
		if(attackerDice1.isSelected() &&
				attackerDice1Value.getText() != null && 
				attackerDice1Value.getText().matches("\\d")) {
			bestAttackerValue.add(Integer.valueOf(attackerDice1Value.getText()));
		}
		if(attackerDice2.isSelected() &&
				attackerDice2Value.getText() != null && 
				attackerDice2Value.getText().matches("\\d")) {
			bestAttackerValue.add(Integer.valueOf(attackerDice2Value.getText()));
		}
		if(attackerDice3.isSelected() &&
				attackerDice3Value.getText() != null && 
				attackerDice3Value.getText().matches("\\d")) {
			bestAttackerValue.add(Integer.valueOf(attackerDice3Value.getText()));
		}
		
		//sorting the list into reverse order
		Collections.sort(bestAttackerValue, Collections.reverseOrder());
		
		return bestAttackerValue;
		
	}
	
	/**
	 * Returns list of defender dice values in decreasing order.
	 */
	public List<Integer> getValuesFromDefenderDice(){
		List<Integer> bestDefenderValue = new ArrayList<>();
		if(defenderDice1.isSelected() &&
				defenderDice1Value.getText() != null && 
						defenderDice1Value.getText().matches("\\d")) {
			bestDefenderValue.add(Integer.valueOf(defenderDice1Value.getText()));
		}
		if(defenderDice2.isSelected() &&
				defenderDice2Value.getText() != null && 
						defenderDice2Value.getText().matches("\\d")) {
			bestDefenderValue.add(Integer.valueOf(defenderDice2Value.getText()));
		}
	
		//sorting the list into reverse order
		Collections.sort(bestDefenderValue, Collections.reverseOrder());
		return bestDefenderValue;
		
	}
	
	public void deductArmies(List<String> playResult) {
		for(String check : playResult) {
			if(check.equals("tie")) {
				winnerName.setText("Deduct army from attacker as it is tie");
				winnerName.setVisible(true);
			}
			if(check.equals("attacker")) {
				winnerName.setText("Deduct army from defender as attacker wins");
				winnerName.setVisible(true);
			}else {
				winnerName.setText("Deduct army from attacker as defender wins");
				winnerName.setVisible(true);
			}
		}
	}
	
	public void roll() {
		throwDice();
		List<String> playResult = diceModel.getPlayResultAfterDiceThrown(getValuesFromAtatckerDice(), getValuesFromDefenderDice());
		deductArmies(playResult);
		/*winnerName.setText("roll clicked");
		attackingTerritory.setArmies(5);*/
	}


}
