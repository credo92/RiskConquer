package com.risk.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.risk.constant.PlayerType;
import com.risk.entity.Map;
import com.risk.entity.Player;
import com.risk.exception.InvalidMapException;
import com.risk.main.MapEditor;
import com.risk.map.util.GameUtil;
import com.risk.map.util.MapFileParser;
import com.risk.map.util.MapUtil;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controller class for Tournament.
 * @author Garvpreet Singh 
 * @version 1.0.0 
 */
public class TournamentController implements Initializable{
	
	/**
	 * The @numberOfPlayers count of players.
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
	 * The @mapButton1 map button 2.
	 */
	@FXML
	private Button mapButton2;
	
	/**
	 * The @mapButton1 map button 3.
	 */
	@FXML
	private Button mapButton3;
	
	/**
	 * The @mapButton1 map button 4.
	 */
	@FXML
	private Button mapButton4;
	
	/**
	 * The @mapButton1 map button 5.
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
	 * The @numberOfPlayersSelected .
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
	
	public List<Map> getListOfMaps() {
		return listOfMaps;
	}
	
	public List<Player> getListOfPlayers() {
		return listOfPlayers;
	}

	public int getNumberOfGamesSelected() {
		return numberOfGamesSelected;
	}

	public void setNumberOfGamesSelected(int numberOfgamesSelected) {
		this.numberOfGamesSelected = numberOfgamesSelected;
	}

	public int getNumberOfTurnsSelected() {
		return numberOfTurnsSelected;
	}

	public void setNumberOfTurnsSelected(int numberOfTurnsSelected) {
		this.numberOfTurnsSelected = numberOfTurnsSelected;
	}
	
	public TournamentController() {
		listOfMaps = new ArrayList<>();
		listOfPlayers = new ArrayList<>(4);
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
	 * Decides type of players in a tournament.
	 */
	public void tournamentPlayersSelectionListner() {
		player1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				Player p1 = new Player(0);
				p1.setType(returnPlayerType(player1.getSelectionModel().getSelectedItem()));
				listOfPlayers.add(p1);
			}
		});
		
		player2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				Player p2 = new Player(1);
				p2.setType(returnPlayerType(player2.getSelectionModel().getSelectedItem()));
				listOfPlayers.add(p2);
			}
		});
		
		player3.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				Player p3 = new Player(2);
				p3.setType(returnPlayerType(player3.getSelectionModel().getSelectedItem()));
				listOfPlayers.add(p3);
			}
		});
		
		player4.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				Player p4 = new Player(3);
				p4.setType(returnPlayerType(player4.getSelectionModel().getSelectedItem()));
				listOfPlayers.add(p4);
			}
		});
	}	
	
	/**
	 * This method converts string to enum.
	 */
	private PlayerType returnPlayerType(String playerType) {
		if(PlayerType.AGGRESSIVE.toString().equals(playerType)) {
			return PlayerType.AGGRESSIVE;
		}
		else if(PlayerType.BENEVOLENT.toString().equals(playerType)) {
			return PlayerType.BENEVOLENT;
		}
		else if(PlayerType.CHEATER.toString().equals(playerType)) {
			return PlayerType.CHEATER;
		}
		else if(PlayerType.RANDOM.toString().equals(playerType)) {
			return PlayerType.RANDOM;
		}
		return null;
	}
		
	/**
	 * Function to upload map 1
	 * @param event
	 *            action event
	 * @return 
	 */
	@FXML
	private void uploadMap1(ActionEvent event) {
		File file = MapUtil.showFileChooser();

		MapFileParser fileLoaderAndParser = new MapFileParser();
		Map map = null;
		try {
			map = fileLoaderAndParser.parseAndReadMapFile(file);
			listOfMaps.add(map);
			mapButton1.setText(file.getName());
		} catch (InvalidMapException ex) {
			MapUtil.infoBox(ex.getMessage(), "Error", "Invalid Map");
		}		
	}
	
	/**
	 * Function to upload map 2
	 * @param event
	 *            action event
	 * @return 
	 */
	@FXML
	private void uploadMap2(ActionEvent event) {
		File file = MapUtil.showFileChooser();

		MapFileParser fileLoaderAndParser = new MapFileParser();
		Map map = null;
		try {
			map = fileLoaderAndParser.parseAndReadMapFile(file);
			listOfMaps.add(map);
			mapButton2.setText(file.getName());
		} catch (InvalidMapException ex) {
			MapUtil.infoBox(ex.getMessage(), "Error", "Invalid Map");
		}		
	}
	
	/**
	 * Function to upload map 3
	 * @param event
	 *            action event
	 * @return 
	 */
	@FXML
	private void uploadMap3(ActionEvent event) {
		File file = MapUtil.showFileChooser();

		MapFileParser fileLoaderAndParser = new MapFileParser();
		Map map = null;
		try {
			map = fileLoaderAndParser.parseAndReadMapFile(file);
			listOfMaps.add(map);
			mapButton3.setText(file.getName());
		} catch (InvalidMapException ex) {
			MapUtil.infoBox(ex.getMessage(), "Error", "Invalid Map");
		}		
	}
	
	/**
	 * Function to upload map 4
	 * @param event
	 *            action event
	 * @return 
	 */
	@FXML
	private void uploadMap4(ActionEvent event) {
		File file = MapUtil.showFileChooser();

		MapFileParser fileLoaderAndParser = new MapFileParser();
		Map map = null;
		try {
			map = fileLoaderAndParser.parseAndReadMapFile(file);
			listOfMaps.add(map);
			mapButton4.setText(file.getName());
		} catch (InvalidMapException ex) {
			MapUtil.infoBox(ex.getMessage(), "Error", "Invalid Map");
		}		
	}
	
	/**
	 * Function to upload map 5
	 * @param event
	 *            action event
	 * @return 
	 */
	@FXML
	private void uploadMap5(ActionEvent event) {
		File file = MapUtil.showFileChooser();

		MapFileParser fileLoaderAndParser = new MapFileParser();
		Map map = null;
		try {
			map = fileLoaderAndParser.parseAndReadMapFile(file);
			listOfMaps.add(map);
			mapButton5.setText(file.getName());
		} catch (InvalidMapException ex) {
			MapUtil.infoBox(ex.getMessage(), "Error", "Invalid Map");
		}		
	}
	
	@FXML
	private void playTournament(ActionEvent event) {
		errorLine.setText(null);
		if(listOfMaps.isEmpty()) {	
			errorLine.setText("Choose atleast one map");
			return;
		}
		else if(getNumberOfTurnsSelected()==0) {	
			errorLine.setText("Select number of turns");
			return;
		}
		else if(listOfPlayers.size()!=4) {	
			errorLine.setText("Select all 4 players.");
			return;
		}
		else {
			MapUtil.appendTextToGameConsole("===Tournament started!===\n", tConsole);	
			MapUtil.appendTextToGameConsole("===Tournament ended!===\n", tConsole);
		}		
	}
	
	/* (non-Javadoc
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
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