package com.risk.controller;

import java.net.URL;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.risk.constant.PlayerType;
import com.risk.entity.Player;
import com.risk.map.util.GameUtil;
import com.risk.map.util.MapUtil;
import com.risk.strategy.AggressiveStrategy;
import com.risk.strategy.BenevolentStrategy;
import com.risk.strategy.CheaterStrategy;
import com.risk.strategy.HumanStrategy;
import com.risk.strategy.PlayerBehaviorStrategy;
import com.risk.strategy.RandomStrategy;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Player Selection controller.
 * 
 * @author Gurpreet Singh
 * @version 1.0.0
 */

public class PlayerSelectionController extends Observable implements Initializable {

	/**
	 * The @vBoxPane .
	 */
	@FXML
	private VBox vBoxPane;

	/**
	 * The @exitButton.
	 */
	@FXML
	private Button okButton;
	
	/**
	 * The @playerList.
	 */
	private List<Player> playerList;

	/**
	 * The @flag.
	 */
	public boolean flag;
	
	/**
	 * Constructor for PlayerSelectionController
	 * 
	 * @param playerList
	 *            reference to get playerList
	 */
	public PlayerSelectionController(List<Player> playerList) {
		this.playerList = playerList;	
	}

	/*
	 * (non-Javadoc) Card controller initializer, loading currentPlayer and current
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	public void initialize(URL location, ResourceBundle resources) {
		loadAllPlayers();
	}

	/**
	 * Get Elements Window.
	 * 
	 * @param playerId 
	 * 				   int playerId
	 * @return hBox
	 */
	public HBox getElementsWindow(int playerId) {
		ChoiceBox<PlayerType> playerType = new ChoiceBox<>();
		playerType.getItems().addAll(PlayerType.values());
		playerType.getSelectionModel().selectFirst();

		TextField textField = new TextField();

		HBox hBox = new HBox();
		hBox.setSpacing(10);

		Label playerIdShow = new Label();
		playerIdShow.setText(String.valueOf(playerId));

		hBox.getChildren().addAll(playerIdShow, textField, playerType);

		return hBox;
	}

	/**
	 * Load All players.
	 */
	public void loadAllPlayers() {
		vBoxPane.setSpacing(10);

		for (int i = 0; i < playerList.size(); i++) {
			HBox getBox = getElementsWindow(Integer.valueOf(playerList.get(i).getId()));
			vBoxPane.getChildren().addAll(getBox);
		}

	}
	
	/**
	 * Get Strategy Object
	 * 
	 * @param  strategyType
	 * 					   String strategyType
	 * @return strategy
	 */
	public PlayerBehaviorStrategy getStrategyObject(String strategyType) {
		PlayerBehaviorStrategy strategy = null;
		if (strategyType.equals("HUMAN")) {
			strategy = new HumanStrategy();
		} else if (strategyType.equals("AGGRESSIVE")) {
			strategy = new AggressiveStrategy();

		} else if (strategyType.equals("BENEVOLENT")) {
			strategy = new BenevolentStrategy();

		} else if (strategyType.equals("RANDOM")) {
			strategy = new RandomStrategy();

		} else if (strategyType.equals("CHEATER")) {
			strategy = new CheaterStrategy();

		}
		return strategy;
	}
	
	/**
	 * Validate Text Fields
	 * @param  hBoxList hBoxList
	 * @return true or false
	 */
	public boolean validateTextFields(ObservableList<Node> hBoxList) {
		for (Node n : hBoxList) {
			HBox box = (HBox) n;
			for (Node node : box.getChildren()) {
				if (node instanceof TextField) {
					if ((TextField) node == null || ((TextField) node).getText().trim().isEmpty()) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * save players
	 * 
	 * @param event
	 *            action event
	 */
	public void savePlayerType(ActionEvent event) {
		ObservableList<Node> hBoxList = vBoxPane.getChildren();
		if (validateTextFields(hBoxList)) {
			for (Node n : hBoxList) {
				HBox box = (HBox) n;
				ObservableList<Node> node = box.getChildren();
				List<Player> players = playerList.stream()
						.filter(p -> p.getId() == Integer.parseInt(((Label) node.get(0)).getText()))
						.collect(Collectors.toList());
				if (node.get(1) instanceof TextField) {
					players.get(0).setName(((TextField) node.get(1)).getText());
					flag = true;
				}
				if (node.get(2) instanceof ChoiceBox<?>) {
					PlayerType selectedPlayerType = (PlayerType) ((ChoiceBox<?>) node.get(2)).getSelectionModel().getSelectedItem();
					players.get(0).setType(selectedPlayerType);
					PlayerBehaviorStrategy strategy = getStrategyObject(selectedPlayerType.toString());
					players.get(0).setStrategy(strategy);
				}
			}
		}
		if (flag) {
			GameUtil.closeScreen(okButton);
			setChanged();
			notifyObservers("playersCreated"); 		
		} else {
			MapUtil.infoBox("Please fill all the details", "Message", "");
		}
	}
}
