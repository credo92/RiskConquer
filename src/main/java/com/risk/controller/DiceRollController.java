package com.risk.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;

import com.risk.entity.Territory;
import com.risk.map.util.GameUtil;
import com.risk.map.util.MapUtil;
import com.risk.model.DiceModel;
import com.risk.strategy.HumanStrategy;
import com.risk.strategy.PlayerBehaviorStrategy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * DiceRollController controller to control all the
 * 
 * @author Gurpreet Singh
 * @version 1.0.0
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
	 * The @continueRoll button.
	 */
	@FXML
	private Button continueRoll;

	/**
	 * The @moveAllArmies button.
	 */
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
	 * The @cancelDiceRoll button.
	 */
	@FXML
	private Button cancelDiceRoll;

	/**
	 * The @moveArmiesView pane.
	 */
	@FXML
	private Pane moveArmiesView;

	/**
	 * The @numberOfArmies label.
	 */
	@FXML
	private Label numberOfArmies;

	/**
	 * The @numberOfArmiesInput textField.
	 */
	@FXML
	private TextField numberOfArmiesInput;

	/**
	 * The @moveArmies button.
	 */
	@FXML
	private Button moveArmies;

	/**
	 * The @skipMoveArmy button.
	 */
	@FXML
	private Button skipMoveArmy;

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
	 * The @gameConsole 
	 */
	private TextArea gameConsole;
	
	/**
	 * The @strategy reference to class PlayerBehaviorStrategy.
	 */
	private PlayerBehaviorStrategy strategy;

	/**
	 * Constructor for dice roll controller.
	 * 
	 * @param diceModel
	 *            dice model object
	 */
	public DiceRollController(DiceModel diceModel, PlayerBehaviorStrategy strategy, TextArea gameConsole) {
		this.diceModel = diceModel;
		this.strategy = strategy;
		this.gameConsole = gameConsole;
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
	
	/**
	 * The Auto Start Dice Roll Controller
	 */
	public void autoStartDiceRollController() {
		autoInitialize();
		loadAttackScreen();
		showDice();
		if (!(strategy instanceof HumanStrategy)) {
			autoRollDice();
		}
	}
	
	/**
	 * The Select Visible Dice
	 * 
	 * @param controls
	 * 				  Control... controls
	 */
	private void selectVisibleDice(Control... controls) {
		for (Control control : controls) {
			if (control.isVisible()) {
				((CheckBox) control).setSelected(true);
			}
		}
	}
	
	/**
	 * The Auto Roll Dice
	 */
	private void autoRollDice() {
		selectVisibleDice(attackerDice1, attackerDice2, attackerDice3, defenderDice1, defenderDice2);

		rollDice(null);
		if (!continueRoll.isDisabled() && !cancelDiceRoll.isDisabled()) {
			continueDiceRoll(null);
		} else if (continueRoll.isDisabled() && !cancelDiceRoll.isDisabled()) {
			diceModel.cancelDiceRoll();
		} else if (moveArmiesView.isVisible()) {
			diceModel.moveAllArmies();
		}
	}

	/**
	 * Load attack Screen for attacker and defender.
	 */
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
		winnerName.setText(StringUtils.EMPTY);
		// clear check boxes
		GameUtil.clearCheckBox(attackerDice1, attackerDice2, attackerDice3, defenderDice1, defenderDice2);
		// Hide output details
		GameUtil.enableControl(roll);
		GameUtil.disableControl(winnerName, continueRoll);
		GameUtil.disableViewPane(moveArmiesView);
	}

	/**
	 * Move armies
	 * 
	 * @param event
	 *            action event
	 */
	@FXML
	private void moveArmies(ActionEvent event) {
		String value = numberOfArmiesInput.getText();
		if (StringUtils.isEmpty(value)) {
			MapUtil.infoBox("Please enter a number of armies to move", "Info", "");
			return;
		}
		int armiesToMove = Integer.valueOf(value);
		diceModel.moveArmies(armiesToMove, winnerName, moveArmies);
	}

	/**
	 * Move all armies
	 * 
	 * @param event
	 *            action event
	 */
	@FXML
	private void moveAllArmies(ActionEvent event) {
		diceModel.moveAllArmies();
		GameUtil.closeScreen(moveAllArmies);
	}

	/**
	 * Skip Move army
	 * 
	 * @param event
	 *            action event
	 */
	@FXML
	private void skipMoveArmy(ActionEvent event) {
		diceModel.skipMoveArmy();
		GameUtil.closeScreen(skipMoveArmy);
	}

	/**
	 * Cancel Dice Roll
	 * 
	 * @param event
	 *            action event
	 */
	@FXML
	private void cancelDiceRoll(ActionEvent event) {
		diceModel.cancelDiceRoll();
		GameUtil.closeScreen(cancelDiceRoll);
	}

	/**
	 * Continue Dice Roll
	 * 
	 * @param event
	 *            action event
	 */
	@FXML
	private void continueDiceRoll(ActionEvent event) {
		diceModel.setAttackerDiceValues(new ArrayList<>());
		diceModel.setDefenderDiceValues(new ArrayList<>());
		/*
		 * if (!(strategy instanceof HumanStrategy)) { autoInitialize(); }
		 */
		loadAttackScreen();
		showDice();
		if (!(strategy instanceof HumanStrategy)) {
			autoRollDice();
		}
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

	/**
	 * Roll Attacker Dice
	 * 
	 * @param dices
	 *            checkBox... dices
	 */
	public void rollAttackerDice(CheckBox... dices) {
		for (CheckBox dice : dices) {
			if (dice.isSelected()) {
				int value = diceModel.randomNumber();
				dice.setText(String.valueOf(value));
				diceModel.getAttackerDiceValues().add(value);
			}
		}
	}

	/**
	 * Roll Defender Dice
	 * 
	 * @param dices
	 *            checkBox... dices
	 */
	public void rollDefenderDice(CheckBox... dices) {
		for (CheckBox dice : dices) {
			if (dice.isSelected()) {
				int value = diceModel.randomNumber();
				dice.setText(String.valueOf(value));
				diceModel.getDefenderDiceValues().add(value);
			}
		}
	}

	/**
	 * Roll Dice to battle among the territories
	 * 
	 * @param event
	 *            action event
	 */
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
			playResult.add(
					attackingTerritory.getPlayer().getName() + " won the territory: " + defendingTerritory.getName());
			diceModel.setNumOfTerritoriesWon(diceModel.getNumOfTerritoriesWon() + 1);
			GameUtil.enableViewPane(moveArmiesView);
			GameUtil.disableControl(roll, continueRoll, cancelDiceRoll);
			GameUtil.hideControl(roll, continueRoll, cancelDiceRoll);
		} else if (attackingTerritory.getArmies() < 2) {
			playResult.add(attackingTerritory.getPlayer().getName() + " lost the match");
			GameUtil.disableControl(roll, continueRoll);
			GameUtil.enableControl(cancelDiceRoll);
			GameUtil.disableViewPane(moveArmiesView);
		} else {
			GameUtil.disableViewPane(moveArmiesView);
			GameUtil.disableControl(roll);
			GameUtil.enableControl(continueRoll, cancelDiceRoll);
		}
		MapUtil.appendTextToGameConsole(playResult.toString() + "\n", gameConsole);
		defenderArmies.setText("Armies: " + String.valueOf(defendingTerritory.getArmies()));
		attackerArmies.setText("Armies: " + String.valueOf(attackingTerritory.getArmies()));
		winnerName.setText(playResult.toString());
		winnerName.setVisible(true);
	}
	
	/**
	 * Auto Initialize .
	 */
	public void autoInitialize() {
		roll = new Button();
		continueRoll = new Button();
		moveAllArmies = new Button();
		skipMoveArmy = new Button();
		moveArmies = new Button();
		cancelDiceRoll = new Button();
		attackerPlayerName = new Label();
		winnerName = new Label();
		numberOfArmies = new Label();
		defenderPlayerName = new Label();
		numberOfArmies = new Label();
		defenderArmies = new Label();
		attackerTerritoryName = new Label();
		attackerArmies = new Label();
		defenderTerritoryName = new Label();
		defenderArmies = new Label();
		attackerDice1 = new CheckBox();
		attackerDice2 = new CheckBox();
		attackerDice3 = new CheckBox();
		defenderDice1 = new CheckBox();
		defenderDice2 = new CheckBox();
		moveArmiesView = new Pane();
		numberOfArmiesInput = new TextField();

	}
}
