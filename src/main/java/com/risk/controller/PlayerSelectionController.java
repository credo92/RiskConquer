package com.risk.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.risk.model.PlayerModel;
import com.risk.constant.PlayerType;
import com.risk.entity.Player;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * Player Selection controller.
 * @author Gurpreet Singh
 * @version 1.0.0
 */

public class PlayerSelectionController {

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
	 * The @playerIdShow label.
	 */
	@FXML
	private Label playerIdShow;
	
	/**
	 * The @playerType type of player.
	 */
	@FXML
	private ChoiceBox<String> playerType;
	
	/**
	 * The @playerName.
	 */
	@FXML
	private TextField playerName;
		
	
	private List<Player> playerList;
	
	public PlayerSelectionController(List<Player> playerList) {
		this.playerList = playerList;
	}
	
	private GamePlayController gamePlayController;
	
	private PlayerModel playerModel;
	
	
	
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
	 * Load All players.
	 */
	public void loadAllPlayers() {
		playerType = new ChoiceBox<>();

		playerType.getItems().add(PlayerType.HUMAN.toString());
		playerType.getItems().add(PlayerType.AGGRESSIVE.toString());
		playerType.getItems().add(PlayerType.BENEVOLENT.toString());
		playerType.getItems().add(PlayerType.CHEATER.toString());
		playerType.getItems().add(PlayerType.RANDOM.toString());
		

		playerIdShow = new Label();

		int numberOfPlayers = gamePlayController.getNumberOfPlayersSelected();
				
//		for (int i = 0; i < numberOfPlayers; i++){
//			playerType = new ChoiceBox<>();
//		}
		
		playerIdShow.setText("hello");
		vBoxPane.getChildren().addAll(playerType, playerIdShow);
	}	
	
}
