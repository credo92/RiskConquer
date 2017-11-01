package com.risk.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.risk.entity.Territory;
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

	/**
	 * Load attacker player information.
	 */
	public void loadAttackerInfo() {
		Territory attackingTerritory = diceModel.getAttackingTerritory();
		attackerPlayerName.setText(attackingTerritory.getPlayer().getName());
		attackerTerritoryName.setText(attackingTerritory.getName());
		attackerArmies.setText(String.valueOf(attackingTerritory.getArmies()));
	}

	/**
	 * Load defender player information.
	 */
	public void loadDefenderInfo() {
		Territory defendingTerritory = diceModel.getDefendingTerritory();
		defenderPlayerName.setText(defendingTerritory.getPlayer().getName());
		defenderTerritoryName.setText(defendingTerritory.getName());
		defenderArmies.setText(String.valueOf(defendingTerritory.getArmies()));
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

		// TODO Auto-generated method stub
		loadAttackerInfo();
		loadDefenderInfo();
		showDice();
		hideUneccessaryElementOnStartUp();
		roll.setOnAction((event) -> {
			roll();
		});
		// winnerName.setText("hello");

	}

	public void hideUneccessaryElementOnStartUp() {
		winnerName.setVisible(false);
		attackerDice1Value.setVisible(false);
		attackerDice2Value.setVisible(false);
		attackerDice3Value.setVisible(false);
		defenderDice1Value.setVisible(false);
		;
		defenderDice2Value.setVisible(false);
		//childPane.setVisible(false);

	}

	/**
	 * Show dices according to number of armies .
	 */
	public void showDice() {
		if (diceModel.getArmyCountOnAttackingTerritory() >= 4) {
			attackerDice1.setVisible(true);
			attackerDice2.setVisible(true);
			attackerDice3.setVisible(true);
		} else if (diceModel.getArmyCountOnAttackingTerritory() >= 3) {
			attackerDice1.setVisible(true);
			attackerDice2.setVisible(true);
			attackerDice3.setVisible(false);
		} else if (diceModel.getArmyCountOnAttackingTerritory() >= 2) {
			attackerDice1.setVisible(true);
			attackerDice2.setVisible(false);
			attackerDice3.setVisible(false);
		}
		if (diceModel.getArmyCountOnDefendingTerritory() > 2) {
			defenderDice1.setVisible(true);
			defenderDice2.setVisible(true);
		} else if (diceModel.getArmyCountOnDefendingTerritory() >= 1) {
			defenderDice1.setVisible(true);
			defenderDice2.setVisible(false);
		}
	}

	public void roll() {
		winnerName.setText("roll clicked");
		diceModel.getAttackingTerritory().setArmies(5);
	}

}
