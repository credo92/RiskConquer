package com.app.team19;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

public class MapEditorController implements Initializable {

	public static final ObservableList<String> data = FXCollections.observableArrayList();

	private Map map;

	@FXML
	private TextField author;

	@FXML
	private TextField image;

	@FXML
	private TextField scroll;

	@FXML
	private TextField warn;

	@FXML
	private TextField wrap;

	@FXML
	private Button exitButton;

	@FXML
	private Button loadMap;

	@FXML
	private Button saveMap;

	@FXML
	private ListView<String> continentList;

	@FXML
	private ListView<String> territoryList;

	@FXML
	private ListView<String> adjTerritoryList;

	@FXML
	private TextField newContinentName;

	@FXML
	private TextField newContinentValue;
	
	@FXML
	private TextField newTerritoryName;
	
	@FXML
	private TextField territoryXaxis;
	
	@FXML
	private TextField territoryYaxis;
	
	@FXML
	private ComboBox<Territory> selectAdjTerritories;

	@FXML
	private void addNewContinent(ActionEvent event) {
		Continent continent = new Continent();
		continent.setName(newContinentName.getText());
		continent.setValue(newContinentValue.getText());
		continentList.getItems().add(continent.getName());
	}

	@FXML
	private void mapEditorExit(ActionEvent event) {
		Platform.exit();
	}

	@FXML
	private void loadExistingMap(ActionEvent event) {

		MapFileParser fileParser = new MapFileParser();
		map = fileParser.parseAndReadMapFile();

		author.setText(map.getMapData().get("author"));
		image.setText(map.getMapData().get("image"));
		scroll.setText(map.getMapData().get("scroll"));
		warn.setText(map.getMapData().get("warn"));
		wrap.setText(map.getMapData().get("wrap"));

		for (Continent continent : map.getContinents()) {
			data.add(continent.getName());
		}
		continentList.setItems(data);
		continentList.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				System.out.println("clicked on " + continentList.getSelectionModel().getSelectedItem());
				populateTerritory(map.getContinentMap().get(continentList.getSelectionModel().getSelectedItem()));
			}
		});
	}

	private void populateTerritory(Continent continent) {
		ObservableList<String> tdata = FXCollections.observableArrayList();
		for (Territory territory : continent.getTerritories()) {
			tdata.add(territory.getName());
		}
		territoryList.setItems(tdata);
		territoryList.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				System.out.println("clicked on " + territoryList.getSelectionModel().getSelectedItem());
				populateAdjTerritory(
						continent.getTerritoryMap().get(territoryList.getSelectionModel().getSelectedItem()));
			}
		});
	}

	private void populateAdjTerritory(Territory territory) {
		ObservableList<String> adJtdata = FXCollections.observableArrayList();
		for (Territory adjTerritory : territory.getAdjacentTerritories()) {
			adJtdata.add(adjTerritory.getName());
		}
		adjTerritoryList.setItems(adJtdata);
	}

	@FXML
	private void saveMap(ActionEvent event) {
		MapFileWriter fileWriter = new MapFileWriter();
		FileChooser fileChooser = new FileChooser();

		// Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Map files (*.map)", "*.map");
		fileChooser.getExtensionFilters().add(extFilter);

		// Show save file dialog
		File file = fileChooser.showSaveDialog(null);

		if (file != null) {
			fileWriter.writeMapToFile(map, file);
		}
	}
	
	@FXML
	private void deleteContinent(ActionEvent event) {
		
	}
	
	@FXML
	private void addNewTerritory(ActionEvent event) {
		
	}
	
	@FXML
	private void deleteTerritory(ActionEvent event) {
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
}
