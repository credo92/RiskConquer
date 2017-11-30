package com.risk.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import com.risk.entity.Map;
import com.risk.entity.Player;
import com.risk.map.util.GameUtil;
import com.risk.map.util.MapUtil;
import com.risk.model.TournamentModel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 * Controller class for Tournament.
 * 
 * @author Garvpreet Singh
 * @version 1.0.0
 */
public class TournamentController implements Initializable {

	/**
	 * The @model reference to TournamentModel.
	 */
	private TournamentModel model;

	/**
	 * The @numberOfTurns number of turns.
	 */
	@FXML
	private ChoiceBox<Integer> numberOfTurns;

	/**
	 * The @numberOfgames count of games.
	 */
	@FXML
	private ChoiceBox<Integer> numberOfGames;

	/**
	 * The @player1 .
	 */
	@FXML
	private ChoiceBox<String> player1;

	/**
	 * The @player2 .
	 */
	@FXML
	private ChoiceBox<String> player2;

	/**
	 * The @player3 .
	 */
	@FXML
	private ChoiceBox<String> player3;

	/**
	 * The @player4 .
	 */
	@FXML
	private ChoiceBox<String> player4;

	/**
	 * The @mapButton1 map button 1.
	 */
	@FXML
	private Button mapButton1;

	/**
	 * The @mapButton2 map button 2.
	 */
	@FXML
	private Button mapButton2;

	/**
	 * The @mapButton3 map button 3.
	 */
	@FXML
	private Button mapButton3;

	/**
	 * The @mapButton4 map button 4.
	 */
	@FXML
	private Button mapButton4;

	/**
	 * The @mapButton5 map button 5.
	 */
	@FXML
	private Button mapButton5;

	/**
	 * The @playButton playButton.
	 */
	@FXML
	private Button playButton;

	/**
	 * The @errorLine errorLine.
	 */
	@FXML
	private Label errorLine;

	/**
	 * The @tConsole output console.
	 */
	@FXML
	private TextArea tConsole;
	
	/**
	 * The @resultTextArea.
	 */
	@FXML
	private TextArea resultTextArea;

	/**
	 * The @closeButton.
	 */
	@FXML
	private Button closeButton;

	/**
	 * The @numberOfTurnsSelected.
	 */
	private int numberOfTurnsSelected;

	/**
	 * The @numberOfGamesSelected .
	 */
	private int numberOfGamesSelected;

	/**
	 * The @listOfMaps .
	 */
	private List<Map> listOfMaps;

	/**
	 * The @listOfPlayerss .
	 */
	private List<Player> listOfPlayers;

	/**
	 * Get List of Maps
	 * 
	 * @return listOfMaps
	 */
	public List<Map> getListOfMaps() {
		return listOfMaps;
	}

	/**
	 * Get List of Players
	 * 
	 * @return listOfPlayers
	 */
	public List<Player> getListOfPlayers() {
		return listOfPlayers;
	}

	/**
	 * Number Of Games Selected
	 * 
	 * @return numberOfGamesSelected
	 */
	public int getNumberOfGamesSelected() {
		return numberOfGamesSelected;
	}

	/**
	 * Set Number of Games Selected
	 * 
	 * @param numberOfgamesSelected
	 *            the number Of games to set
	 */
	public void setNumberOfGamesSelected(int numberOfgamesSelected) {
		this.numberOfGamesSelected = numberOfgamesSelected;
	}

	/**
	 * Get Number Of Turns
	 * 
	 * @return numberOfTurnsSelected
	 * 
	 */
	public int getNumberOfTurnsSelected() {
		return numberOfTurnsSelected;
	}

	/**
	 * Set Number Of Turns Selected
	 * 
	 * @param numberOfTurnsSelected
	 *            the number Of Turns to set
	 */
	public void setNumberOfTurnsSelected(int numberOfTurnsSelected) {
		this.numberOfTurnsSelected = numberOfTurnsSelected;
	}

	/**
	 * Constructor for TournamentController
	 */
	public TournamentController() {
		listOfMaps = new ArrayList<>();
		listOfPlayers = new ArrayList<>();
	}

	/**
	 * Decides no. of turns for a game.
	 */
	public void tournamentTurnsSelectionListner() {
		numberOfTurns.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
				setNumberOfTurnsSelected(numberOfTurns.getSelectionModel().getSelectedItem());
			}
		});
	}

	/**
	 * Decides no. of games in a tournament.
	 */
	public void tournamentGamesSelectionListner() {
		numberOfGames.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
				setNumberOfGamesSelected(numberOfGames.getSelectionModel().getSelectedItem());
			}
		});
	}

	/**
	 * Check If	Player	Exist
	 * 
	 * @param players listOfPlayers
	 * @param id id of player
	 * @return player player.
	 */
	public Player checkIfPlayerExist(List<Player> players, int id) {
		Player player = null;
		for (Player p : players) {
			if (p.getId() == id) {
				player = p;
			}
		}
		if (player == null) {
			player = new Player(id);
			players.add(player);
		}
		return player;
	}

	/**
	 * Decides type of players in a tournament.
	 */
	public void tournamentPlayersSelectionListner() {
		player1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				Player p1 = checkIfPlayerExist(listOfPlayers, 0);
				p1.setName("Player" + 0);
				model.returnPlayerType(player1.getSelectionModel().getSelectedItem(), p1);
			}
		});

		player2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				Player p2 = checkIfPlayerExist(listOfPlayers, 1);
				p2.setName("Player" + 1);
				model.returnPlayerType(player2.getSelectionModel().getSelectedItem(), p2);
			}
		});

		player3.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				Player p3 = checkIfPlayerExist(listOfPlayers, 2);
				p3.setName("Player" + 2);
				model.returnPlayerType(player3.getSelectionModel().getSelectedItem(), p3);
			}
		});

		player4.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				Player p4 = checkIfPlayerExist(listOfPlayers, 3);
				p4.setName("Player" + 3);
				model.returnPlayerType(player4.getSelectionModel().getSelectedItem(), p4);
			}
		});
	}
	
	/**
	 * Close
	 * 
	 * @param event
	 *            action event
	 */
	@FXML
	private void close(ActionEvent event) {
		GameUtil.closeScreen(closeButton);
	}

	/**
	 * Function to upload map 1
	 * 
	 * @param event
	 *            action event
	 */
	@FXML
	private void uploadMap1(ActionEvent event) {
		File file = model.uploadMap(listOfMaps);
		mapButton1.setText(file.getName());
	}

	/**
	 * Function to upload map 2
	 * 
	 * @param event
	 *            action event
	 */
	@FXML
	private void uploadMap2(ActionEvent event) {
		File file = model.uploadMap(listOfMaps);
		mapButton2.setText(file.getName());
	}

	/**
	 * Function to upload map 3
	 * 
	 * @param event
	 *            action event
	 */
	@FXML
	private void uploadMap3(ActionEvent event) {
		File file = model.uploadMap(listOfMaps);
		mapButton3.setText(file.getName());
	}

	/**
	 * Function to upload map 4
	 * 
	 * @param event
	 *            action event
	 */
	@FXML
	private void uploadMap4(ActionEvent event) {
		File file = model.uploadMap(listOfMaps);
		mapButton4.setText(file.getName());
	}

	/**
	 * Function to upload map 5
	 * 
	 * @param event
	 *            action event
	 */
	@FXML
	private void uploadMap5(ActionEvent event) {
		File file = model.uploadMap(listOfMaps);
		mapButton5.setText(file.getName());
	}

	/**
	 * Function to play tournament
	 * 
	 * @param event
	 *            action event
	 */
	@FXML
	private void playTournament(ActionEvent event) {
		errorLine.setText("");
		if (getNumberOfGamesSelected() == 0) {
			errorLine.setText("Select number of games");
			return;
		}
		if (getNumberOfTurnsSelected() == 0) {
			errorLine.setText("Select number of turns");
			return;
		}
		else if (listOfMaps.isEmpty()) {
			errorLine.setText("Choose atleast one map");
			return;
		}  else if (listOfPlayers.size() != 4) {
			errorLine.setText("Select all 4 players.");
			return;
		} else {

			MapUtil.appendTextToGameConsole("===Tournament started!===\n", tConsole);
			for (Map map : listOfMaps) {
				int count = 0;
				while (count != numberOfGamesSelected) {
					Map newMap = null;
					try {
						newMap = model.createMapClone(map);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					model.startTournamentGame(listOfPlayers, newMap, numberOfTurnsSelected, tConsole, count);
					count++;
				}
			}
			MapUtil.appendTextToGameConsole("===Tournament ended!===\n", tConsole);
		}
		for (Entry<String, HashMap<String, String>> entry : model.getTournamentResult().entrySet()) {
			MapUtil.appendTextToGameConsole(entry.getKey() + "\n", resultTextArea);
			for (Entry<String, String> data : entry.getValue().entrySet()) {
				MapUtil.appendTextToGameConsole(data.getKey() + " : " + data.getValue() + "\n", resultTextArea);
			}
			MapUtil.appendTextToGameConsole("=============================================\n", resultTextArea);
		}

	}

	/*
	 * (non-Javadoc
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		model = new TournamentModel();
		model.setTournamentResult(new HashMap<>());
		GameUtil.initializeTotalTurnsInTournament(numberOfTurns);
		GameUtil.initializeTotalGamesInTournament(numberOfGames);
		GameUtil.initializePlayersInTournament(player1);
		GameUtil.initializePlayersInTournament(player2);
		GameUtil.initializePlayersInTournament(player3);
		GameUtil.initializePlayersInTournament(player4);
		tournamentTurnsSelectionListner();
		tournamentGamesSelectionListner();
		tournamentPlayersSelectionListner();
	}

}