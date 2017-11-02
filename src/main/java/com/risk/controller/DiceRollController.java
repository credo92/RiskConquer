package com.risk.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import com.risk.entity.Territory;
import com.risk.map.util.GameUtil;
import com.risk.map.util.MapUtil;
import com.risk.model.DiceModel;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * @author Gurpreet Singh DiceRollController controller to control all the
 * @version 1.0.1
 */
public class DiceRollController implements Initializable {

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
	private Pane moveArmiesView;

	/**
	 * The @numberOfArmiesLabel label.
	 */
	@FXML
	private Label numberOfArmies;

	/**
	 * The @numberOfArmiesInput textField.
	 */
	@FXML
	private TextField numberOfArmiesInput;

	/**
	 * The @numberOfArmiesMove button.
	 */
	@FXML
	private Button moveArmies;

	/**
	 * The @numberOfArmiesCancel button.
	 */
	@FXML
	private Button numberOfArmiesCancel;

	/**
	 * The @winnerName label.
	 */
	@FXML
	private Label winnerName;

	/**
	 * The @diceModel reference to class DiceModel.
	 */
	private DiceModel diceModel;

	List<String> playResult = new ArrayList<>();

	/**
	 * Constructor for DiceRollController
	 * 
	 * @param attackingTerritory
	 *            reference to get details about attacking territory
	 * 
	 * @param defendingTerritory
	 *            reference to get details about defending territory
	 */
	public DiceRollController(DiceModel diceModel) {
		this.diceModel = diceModel;

	}

	/**
	 * Load attacker player information.
	 */
	public void loadAttackerInfo() {
		Territory attackingTerritory = diceModel.getAttackingTerritory();
		attackerPlayerName.setText(attackingTerritory.getPlayer().getName());
		attackerTerritoryName.setText(attackingTerritory.getName());
		attackerArmies.setText("Armies: " + String.valueOf(attackingTerritory.getArmies()));
	}

	/**
	 * Load defender player information.
	 */
	public void loadDefenderInfo() {
		Territory defendingTerritory = diceModel.getDefendingTerritory();
		defenderPlayerName.setText(defendingTerritory.getPlayer().getName());
		defenderTerritoryName.setText(defendingTerritory.getName());
		defenderArmies.setText("Armies: " + String.valueOf(defendingTerritory.getArmies()));
	}

	/*
	 * (non-Javadoc) Dice Roll controller initializer, loading player and territory
	 * data.
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		hideUneccessaryElementOnStartUp();
		loadAttackerInfo();
		loadDefenderInfo();
		showDice();
		roll.setOnAction((event) -> {
			roll();
		});
		// winnerName.setText("hello");

	}

	public void hideUneccessaryElementOnStartUp() {
		GameUtil.disableControl(winnerName);
		GameUtil.disableViewPane(moveArmiesView);
	}

	/**
	 * Show dices according to number of armies .
	 */
	public void showDice() {
		if (diceModel.getAttackingTerritory().getArmies() >= 4) {
			GameUtil.enableControl(attackerDice1, attackerDice2, attackerDice3);
		} else if (diceModel.getAttackingTerritory().getArmies() >= 3) {
			GameUtil.enableControl(attackerDice1, attackerDice2);
			GameUtil.disableControl(attackerDice3);
		} else if (diceModel.getArmyCountOnAttackingTerritory() >= 2) {
			GameUtil.enableControl(attackerDice1);
			GameUtil.disableControl(attackerDice2, attackerDice3);
		}
		if (diceModel.getArmyCountOnDefendingTerritory() > 2) {
			GameUtil.enableControl(defenderDice1, defenderDice2);
		} else if (diceModel.getArmyCountOnDefendingTerritory() >= 1) {
			GameUtil.enableControl(defenderDice1);
			GameUtil.disableControl(defenderDice2);
		}
	}

	/**
	 * Set random values on each dice and set on label and make label visible .
	 */
	public void throwDice() {
		if (!attackerDice1.isSelected() && !attackerDice2.isSelected() && !attackerDice3.isSelected()) {
			GameUtil.infoBox("Please Select atleast one of the attacker dice", "Message", "");
			return;
		} else if (!defenderDice1.isSelected() && !defenderDice2.isSelected()) {
			GameUtil.infoBox("Please Select atleast one of the defender dice", "Message", "");
			return;
		}
		if (attackerDice1.isSelected()) {
			attackerDice1.setText(String.valueOf(diceModel.randomNumber()));
		}
		if (attackerDice2.isSelected()) {
			attackerDice2.setText(String.valueOf(diceModel.randomNumber()));
		}
		if (attackerDice3.isSelected()) {
			attackerDice3.setText(String.valueOf(diceModel.randomNumber()));
		}
		if (defenderDice1.isSelected()) {
			defenderDice1.setText(String.valueOf(diceModel.randomNumber()));
		}
		if (defenderDice2.isSelected()) {
			defenderDice2.setText(String.valueOf(diceModel.randomNumber()));
		}

	}

	/**
	 * Returns list of attacker dice values in decreasing order.
	 */
	public List<Integer> getValuesFromAtatckerDice() {
		List<Integer> bestAttackerValue = new ArrayList<>();
		if (attackerDice1.isSelected() &&
				attackerDice1.getText() != null && 
				attackerDice1.getText().matches("\\d")
				) {
			bestAttackerValue.add(Integer.valueOf(attackerDice1.getText()));
		}
		if (attackerDice2.isSelected() &&
				attackerDice2.getText() != null && 
				attackerDice2.getText().matches("\\d")) {
			bestAttackerValue.add(Integer.valueOf(attackerDice2.getText()));
		}
		if (attackerDice3.isSelected() &&
				attackerDice3.getText() != null && 
				attackerDice3.getText().matches("\\d")) {
			bestAttackerValue.add(Integer.valueOf(attackerDice3.getText()));
		}

		// sorting the list into reverse order
		Collections.sort(bestAttackerValue, Collections.reverseOrder());
		
		if(bestAttackerValue.size() == 3) {
			bestAttackerValue.remove(bestAttackerValue.size() - 1);
		}
		
		return bestAttackerValue;

	}

	/**
	 * Returns list of defender dice values in decreasing order.
	 */
	public List<Integer> getValuesFromDefenderDice() {
		List<Integer> bestDefenderValue = new ArrayList<>();
		if (defenderDice1.isSelected() &&
				defenderDice1.getText() != null && 
				defenderDice1.getText().matches("\\d")) {
			bestDefenderValue.add(Integer.valueOf(defenderDice1.getText()));
		}
		if (defenderDice2.isSelected() &&
				defenderDice2.getText() != null && 
				defenderDice2.getText().matches("\\d")) {
			bestDefenderValue.add(Integer.valueOf(defenderDice2.getText()));
		}

		// sorting the list into reverse order
		Collections.sort(bestDefenderValue, Collections.reverseOrder());
		
		return bestDefenderValue;

	}

	
	public void deductArmies(List<String> playResult) {
		if(!playResult.isEmpty()) {
			for (String check : playResult) {
				if (check.equals("tie")) {
					if(diceModel.checkIfAttackerContinue()){
					attackerArmies.setText(String.valueOf(diceModel.deductArmyFromAttacker()));
					}
					else{
						//Need to show message in game console 
						//MapUtil.infoBox("You must have at least two armies to continue attack", "Message", "");
						GameUtil.disableControl(roll);
						GameUtil.enableViewPane(moveArmiesView);
					}
				}
				else if (check.equals("attacker")) {
					if(diceModel.checkIfAttackerContinue()){
						attackerArmies.setText(String.valueOf(diceModel.deductArmyFromAttacker()));
						}
						else{
							//Need to show message in game console 
							//MapUtil.infoBox("You must have at least two armies to continue attack", "Message", "");
							GameUtil.disableControl(roll);
							GameUtil.enableViewPane(moveArmiesView);
						}
					
				} else if (check.equals("defender")){
					attackerArmies.setText(String.valueOf(diceModel.deductArmyFromAttacker()));	
				}
			}
		}
	}

	public void roll() {
		throwDice();
		playResult.clear();
		playResult = diceModel.getPlayResultAfterDiceThrown(getValuesFromAtatckerDice(),
				getValuesFromDefenderDice());
		deductArmies(playResult);
		/*
		 * winnerName.setText("roll clicked"); attackingTerritory.setArmies(5);
		 */
	}

}
