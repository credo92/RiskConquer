package com.risk.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.risk.entity.Continent;
import com.risk.entity.Map;
import com.risk.entity.Territory;
import com.risk.map.util.MapFileParser;
import com.risk.map.util.MapFileWriter;
import com.risk.map.util.MapUtil;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

public class MapEditorController implements Initializable {

	public static final ObservableList<Continent> continentData = FXCollections.observableArrayList();

	public static final ObservableList<Territory> territoryData = FXCollections.observableArrayList();

	public static final ObservableList<String> adjTerritoryData = FXCollections.observableArrayList();

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
	private Button saveMap;

	@FXML
	private Label selectedContinent;

	@FXML
	private ListView<Continent> continentList;

	@FXML
	private ListView<Territory> territoryList;

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
	private ImageView riskImage;

	@FXML
	private ComboBox<Territory> selectAdjTerritories;

	@FXML
	private void addNewContinent(ActionEvent event) {
		
		Continent continent = new Continent();
		
		continent.setName(newContinentName.getText());
		continent.setValue(newContinentValue.getText());
		
		//continentData.add(continent);
		continentList.getItems().add(continent);
		map.getContinents().add(continent);
		MapUtil.clearTextField(newContinentName, newContinentValue);

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

		// Load adjacent erritory
		loadAdjTerritoryList();
		for (Continent continent : map.getContinents()) {
			continentData.add(continent);
		}
		continentList.setItems(continentData);
		continentList.setCellFactory(param -> new ListCell<Continent>() {
			@Override
			protected void updateItem(Continent item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null || item.getName() == null) {
					setText(null);
				} else {
					setText(item.getName());
				}
			}
		});

		continentList.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// System.out.println("clicked on " +
				// continentList.getSelectionModel().getSelectedItem());
				selectedContinent.setText(continentList.getSelectionModel().getSelectedItem().getName());
				populateTerritory(continentList.getSelectionModel().getSelectedItem());
			}
		});
	}

	private void populateTerritory(Continent continent) {
		territoryData.clear();
		adjTerritoryData.clear();
		if (continent != null && continent.getTerritories() != null) {
			for (Territory territory : continent.getTerritories()) {
				territoryData.add(territory);
			}
			territoryList.setItems(territoryData);
			territoryList.setCellFactory(param -> new ListCell<Territory>() {
				@Override
				protected void updateItem(Territory item, boolean empty) {
					super.updateItem(item, empty);

					if (empty || item == null || item.getName() == null) {
						setText(null);
					} else {
						setText(item.getName());
					}
				}
			});
			territoryList.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					populateAdjTerritory(territoryList.getSelectionModel().getSelectedItem());
				}
			});
		}
	}

	private void populateAdjTerritory(Territory territory) {
		adjTerritoryData.clear();
		for (Territory adjTerritory : territory.getAdjacentTerritories()) {
			adjTerritoryData.add(adjTerritory.getName());
		}
		adjTerritoryList.setItems(adjTerritoryData);
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
		Territory territory = new Territory();
		List<Territory> tList = new ArrayList<>();
		territory.setName(newTerritoryName.getText());
		territory.setxCoordinate(Integer.parseInt(territoryXaxis.getText()));
		territory.setyCoordinate(Integer.parseInt(territoryYaxis.getText()));
		territory.setBelongToContinent(continentList.getSelectionModel().getSelectedItem());
		tList.add(selectAdjTerritories.getSelectionModel().getSelectedItem());
		territory.setAdjacentTerritories(tList);
		
		if (continentList.getSelectionModel().getSelectedItem().getTerritories() == null) {
			List<Territory> newTList = new ArrayList<>();
			newTList.add(territory);
			continentList.getSelectionModel().getSelectedItem().setTerritories(newTList);  
		} else {
			continentList.getSelectionModel().getSelectedItem().getTerritories().add(territory);
		}

		territoryList.getItems().add(territory);
	}

	@FXML
	private void deleteTerritory(ActionEvent event) {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(new File(getClass().getClassLoader().getResource("risk.jpg").getFile()));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Image image = new Image(inputStream);

		riskImage.setImage(image);
	}

	private void loadAdjTerritoryList() {

		ObservableList<Territory> adjTerritoryList = FXCollections.observableArrayList();
		for (Continent continent : map.getContinents()) {
			for (Territory territory : continent.getTerritories()) {
				adjTerritoryList.add(territory);
			}
		}
		selectAdjTerritories.setItems(adjTerritoryList);
		selectAdjTerritories.setCellFactory(param -> new ListCell<Territory>() {
			@Override
			protected void updateItem(Territory item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null || item.getName() == null) {
					setText(null);
				} else {
					setText(item.getName());
				}
			}
		});
	}
}
