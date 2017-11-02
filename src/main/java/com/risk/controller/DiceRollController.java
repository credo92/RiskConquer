package com.risk.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.risk.entity.Territory;
import com.risk.map.util.GameUtil;
import com.risk.map.util.MapUtil;
import com.risk.model.DiceModel;

import javafx.event.ActionEvent;
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

	@FXML
	private Button continueRoll;

	@FXML
	private Button moveAllArmies;

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

	/*
	 * (non-Javadoc) Dice Roll controller initializer, loading player and territory
	 * data.
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		loadAttackScreen();
		showDice();
	}

	public void loadAttackScreen() {
		// Load attacker details
		Territory attackingTerritory = diceModel.getAttackingTerritory();
		attackerPlayerName.setText(attackingTerritory.getPlayer().getName());
		attackerTerritoryName.setText("Territory: " + attackingTerritory.getName());
		attackerArmies.setText("Armies: " + String.valueOf(attackingTerritory.getArmies()));

		// Load defender details
		Territory defendingTerritory = diceModel.getDefendingTerritory();
		defenderPlayerName.setText(defendingTerritory.getPlayer().getName());
		defenderTerritoryName.setText("Territory: " + defendingTerritory.getName());
		defenderArmies.setText("Armies: " + String.valueOf(defendingTerritory.getArmies()));

		// clear check boxes
		GameUtil.clearCheckBox(attackerDice1, attackerDice2, attackerDice3, defenderDice1, defenderDice2);
		// Hide output details
		GameUtil.disableControl(winnerName, continueRoll);
		GameUtil.disableViewPane(moveArmiesView);
	}

	@FXML
	private void moveArmies(ActionEvent event) {

	}

	@FXML
	private void moveAllArmies(ActionEvent event) {

	}

	@FXML
	private void continueDiceRoll(ActionEvent event) {
		loadAttackScreen();
		showDice();
	}

	/**
	 * Show dices according to number of armies .
	 */
	public void showDice() {
		if (diceModel.getAttackingTerritory().getArmies() >= 4) {
			GameUtil.showControl(attackerDice1, attackerDice2, attackerDice3);
		} else if (diceModel.getAttackingTerritory().getArmies() >= 3) {
			GameUtil.showControl(attackerDice1, attackerDice2);
			GameUtil.hideControl(attackerDice3);
		} else if (diceModel.getAttackingTerritory().getArmies() >= 2) {
			GameUtil.showControl(attackerDice1);
			GameUtil.hideControl(attackerDice2, attackerDice3);
		}
		if (diceModel.getDefendingTerritory().getArmies() > 2) {
			GameUtil.showControl(defenderDice1, defenderDice2);
		} else if (diceModel.getDefendingTerritory().getArmies() >= 1) {
			GameUtil.showControl(defenderDice1);
			GameUtil.hideControl(defenderDice2);
		}
	}

	public void rollAttackerDice(CheckBox... dices) {
		for (CheckBox dice : dices) {
			if (dice.isSelected()) {
				int value = diceModel.randomNumber();
				dice.setText(String.valueOf(value));
				diceModel.getAttackerDiceValues().add(value);
			}
		}
	}

	public void rollDefenderDice(CheckBox... dices) {
		for (CheckBox dice : dices) {
			if (dice.isSelected()) {
				int value = diceModel.randomNumber();
				dice.setText(String.valueOf(value));
				diceModel.getDefenderDiceValues().add(value);
			}
		}
	}

	@FXML
	public void rollDice(ActionEvent event) {
		if (!attackerDice1.isSelected() && !attackerDice2.isSelected() && !attackerDice3.isSelected()) {
			MapUtil.infoBox("Please Select atleast one of the attacker dice", "Message", "");
			return;
		} else if (!defenderDice1.isSelected() && !defenderDice2.isSelected()) {
			MapUtil.infoBox("Please Select atleast one of the defender dice", "Message", "");
			return;
		}
		rollAttackerDice(attackerDice1, attackerDice2, attackerDice3);
		rollDefenderDice(defenderDice1, defenderDice2);

		List<String> playResult = diceModel.getPlayResultAfterDiceThrown();

		Territory attackingTerritory = diceModel.getAttackingTerritory();
		Territory defendingTerritory = diceModel.getDefendingTerritory();
		if (defendingTerritory.getArmies() <= 0) {
			playResult.add(attackingTerritory.getPlayer().getName() + " won the territory: " + defendingTerritory.getName());
			GameUtil.enableViewPane(moveArmiesView);
			GameUtil.hideControl(roll, continueRoll, cancel);
		} else if (attackingTerritory.getArmies() < 2) {
			playResult.add(attackingTerritory.getPlayer().getName() + " lost the match");
			GameUtil.disableControl(roll, continueRoll);
		} else {
			GameUtil.disableControl(roll);
			GameUtil.enableControl(continueRoll);
		}
		defenderArmies.setText("Armies: " + String.valueOf(defendingTerritory.getArmies()));
		attackerArmies.setText("Armies: " + String.valueOf(attackingTerritory.getArmies()));
		winnerName.setText(playResult.toString());
		winnerName.setVisible(true);
	}

}
