package com.risk.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.risk.model.PlayerGamePhase;
import com.risk.constant.PlayerType;
import com.risk.entity.Player;
import com.risk.map.util.GameUtil;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Player Selection controller.
 * @author Gurpreet Singh
 * @version 1.0.0
 */

public class PlayerSelectionController implements Initializable{

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
	 * The @cancelButton.
	 */
	@FXML
	private Button cancelButton;
	
	private Player player;

		
	
	private List<Player> playerList;
	
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
	
	public HBox getElementsWindow(int playerId) {
		ChoiceBox<PlayerType> playerType = new ChoiceBox<>();
		playerType.getItems().addAll(PlayerType.values());
		
		TextField textField = new TextField();
		
		HBox hBox = new HBox();
		hBox.setSpacing(10);
		
		Label playerIdShow = new Label();
		playerIdShow.setText(String.valueOf("Player "+ playerId));
		
		hBox.getChildren().addAll(playerIdShow, textField, playerType);
		
		return hBox;
	}
	
	/**
	 * Load All players.
	 */
	public void loadAllPlayers() {
		vBoxPane.setSpacing(10);
		
		for(int i = 0; i < playerList.size(); i++) {
			HBox getBox = getElementsWindow(Integer.valueOf(playerList.get(i).getId()));			
			vBoxPane.getChildren().addAll(getBox);
		}
		
	}
	
	public boolean validateTextFields(ObservableList<Node> hBoxList) {
		for(Node n : hBoxList) {
			HBox box = (HBox)n;
			for (Node node : box.getChildren()) {
				if(node instanceof TextField) {
					if((TextField)node != null) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 *save players
	 * 
	 * @param event
	 *            action event
	 */
	public void savePlayerType(ActionEvent event) {
		ObservableList<Node> hBoxList = vBoxPane.getChildren();
		if(validateTextFields(hBoxList)) {
			for(Node n : hBoxList) {
				HBox box = (HBox)n;
				for (Node node : box.getChildren()) {
					if(node instanceof TextField) {
						player.setName(((TextField)node).getText());
					}
				}
			}
		}

		
	}
}
