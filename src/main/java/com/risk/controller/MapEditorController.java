package com.risk.controller;

import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;

import com.risk.entity.Continent;
import com.risk.entity.Map;
import com.risk.entity.Territory;
import com.risk.exception.InvalidMapException;
import com.risk.map.util.MapUtil;
import com.risk.model.MapModel;

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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MapEditorController implements Initializable {

	/**
	 * The @map.
	 */
	private Map map;

	/**
	 * The @file.
	 */
	private File file;

	/**
	 * The @mapModel.
	 */
	private MapModel mapModel;

	/**
	 * The @author.
	 */
	@FXML
	private TextField author;

	/**
	 * The @image.
	 */
	@FXML
	private TextField image;

	/**
	 * The @scroll.
	 */
	@FXML
	private TextField scroll;

	/**
	 * The @warn.
	 */
	@FXML
	private TextField warn;

	/**
	 * The @wrap.
	 */
	@FXML
	private TextField wrap;

	/**
	 * The @exitButton.
	 */
	@FXML
	private Button exitButton;

	/**
	 * The @saveMap.
	 */
	@FXML
	private Button saveMap;

	/**
	 * The @selectedContinent.
	 */
	@FXML
	private Label selectedContinent;

	/**
	 * The @continentList.
	 */
	@FXML
	private ListView<Continent> continentList;

	/**
	 * The @territoryList.
	 */
	@FXML
	private ListView<Territory> territoryList;

	/**
	 * The @adjTerritoryList.
	 */
	@FXML
	private ListView<Territory> adjTerritoryList;

	/**
	 * The @newContinentName.
	 */
	@FXML
	private TextField newContinentName;

	/**
	 * The @newContinentValue.
	 */
	@FXML
	private TextField newContinentValue;

	/**
	 * The @newTerritoryName.
	 */
	@FXML
	private TextField newTerritoryName;

	/**
	 * The @territoryXaxis.
	 */
	@FXML
	private TextField territoryXaxis;

	/**
	 * The @territoryYaxis.
	 */
	@FXML
	private TextField territoryYaxis;

	/**
	 * The @riskImage.
	 */
	@FXML
	private ImageView riskImage;

	/**
	 * The @addContinent.
	 */
	@FXML
	private Button addContinent;

	/**
	 * The @updateCont.
	 */
	@FXML
	private Button updateCont;

	/**
	 * The @addTerritory.
	 */
	@FXML
	private Button addTerritory;

	/**
	 * The @updateTerrt.
	 */
	@FXML
	private Button updateTerrt;

	/**
	 * The @deleteAdjTerr.
	 */
	@FXML
	private Button deleteAdjTerr;

	/**
	 * The @outPutConsole.
	 */
	@FXML
	private TextArea outPutConsole;

	/**
	 * The @selectAdjTerritories.
	 */
	@FXML
	private ComboBox<Territory> selectAdjTerritories;

	/**
	 * Constructur for MapEditorController
	 */
	public MapEditorController() {
		this.mapModel = new MapModel();
	}

	/**
	 * Constructur for MapEditorController
	 * 
	 * @param map
	 *            map object
	 * @param file
	 *            file object
	 */
	public MapEditorController(Map map, File file) {
		this.map = map;
		this.file = file;
		this.mapModel = new MapModel();
	}

	/**
	 * Update continent event
	 * 
	 * @param event
	 *            action event
	 */
	@FXML
	private void updateContinent(ActionEvent event) {
		Continent continent = continentList.getSelectionModel().getSelectedItem();
		continent = mapModel.updateContinent(continent, newContinentValue.getText());

		MapUtil.enableControl(newContinentName, addContinent);

		MapUtil.clearTextField(newContinentName, newContinentValue);
	}

	/**
	 * Update territory event
	 * @param event action event
	 */
	@FXML
	private void updateTerritory(ActionEvent event) {
		Territory territory = territoryList.getSelectionModel().getSelectedItem();

		Territory adjTerritory = selectAdjTerritories.getSelectionModel().getSelectedItem();
		territory = mapModel.updateTerritory(territory, territoryXaxis.getText(), territoryYaxis.getText(),
				adjTerritory);

		MapUtil.enableControl(newTerritoryName, addTerritory);

		MapUtil.clearTextField(newTerritoryName, territoryXaxis, territoryYaxis);
	}

	/**
	 * Add new continent
	 * @param event action event
	 */
	@FXML
	private void addNewContinent(ActionEvent event) {

		Continent continent = null;

		try {
			continent = mapModel.addContinent(map, newContinentName.getText(), newContinentValue.getText());
		} catch (InvalidMapException ex) {
			MapUtil.infoBox(ex.getMessage(), "Error", "Invalid Map");
			return;
		}
		if (continentList == null) {
			continentList = new ListView<Continent>();
		}
		continentList.getItems().add(continent);
		map.getContinents().add(continent);
		MapUtil.clearTextField(newContinentName, newContinentValue);
	}

	/**
	 * Exit map editor.
	 * @param event action event.
	 */
	@FXML
	private void mapEditorExit(ActionEvent event) {
		Stage stage = (Stage) exitButton.getScene().getWindow();
		stage.close();
	}

	/**
	 * Load map data on the view.
	 */
	private void loadMapData() {
		author.setText(map.getMapData().get("author"));
		image.setText(map.getMapData().get("image"));
		scroll.setText(map.getMapData().get("scroll"));
		warn.setText(map.getMapData().get("warn"));
		wrap.setText(map.getMapData().get("wrap"));

		// Load adjacent erritory
		loadAdjTerritoryList();
		for (Continent continent : map.getContinents()) {
			continentList.getItems().add(continent);
		}
	}

	/**
	 * Populate territory for the continent
	 * @param continent continent object.
	 */
	private void populateTerritory(Continent continent) {
		territoryList.getItems().clear();
		if (continent != null && continent.getTerritories() != null) {
			for (Territory territory : continent.getTerritories()) {
				territoryList.getItems().add(territory);
			}
		}
	}

	/**
	 * Populate adjacent territory.
	 * @param territory territory object.
	 */
	private void populateAdjTerritory(Territory territory) {
		adjTerritoryList.getItems().clear();
		for (Territory adjTerritory : territory.getAdjacentTerritories()) {
			if (adjTerritory != null) {
				adjTerritoryList.getItems().add(adjTerritory);
			}
		}
		adjTerritoryList.setCellFactory(param -> new ListCell<Territory>() {
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
		adjTerritoryList.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				outPutConsole.clear();
			}
		});
	}

	/**
	 * Save map file.
	 * @param event action event
	 */
	@FXML
	private void saveMap(ActionEvent event) {
		map = saveOrUpdateMapDetail(map);
		try {
			MapUtil.saveMap(this.file, map);
		} catch (InvalidMapException ex) {
			MapUtil.infoBox(ex.getMessage(), "Error", "InvalidMap");
			return;
		}
		Stage stage = (Stage) saveMap.getScene().getWindow();
		stage.close();
	}

	/**
	 * Delete continent.
	 * @param event action event
	 */
	@FXML
	private void deleteContinent(ActionEvent event) {
		Continent continent = continentList.getSelectionModel().getSelectedItem();

		if (continent != null && continent.getTerritories() != null) {
			if (continent.getTerritories().size() > 1) {
				MapUtil.outPutMessgae(outPutConsole, "Remove associated territories first", false);
				return;
			}
		}
		if (map.getContinents() != null) {
			map.getContinents().remove(continent);
			continentList.getItems().remove(continent);
			MapUtil.outPutMessgae(outPutConsole, "Continent removed successfully.", true);
		}
	}

	/**
	 * Delete adjacent territory
	 * @param event action event
	 */
	@FXML
	private void deleteAdjTerritory(ActionEvent event) {
		Territory adjTerritory = adjTerritoryList.getSelectionModel().getSelectedItem();

		Territory territory = territoryList.getSelectionModel().getSelectedItem();
		if (territory != null && territory.getAdjacentTerritories() != null) {
			if (territory.getAdjacentTerritories().size() == 1) {
				MapUtil.outPutMessgae(outPutConsole, "Atleast one adjacent territory should exist", false);
				return;
			}
			territory.getAdjacentTerritories().remove(adjTerritory);
			adjTerritoryList.getItems().remove(adjTerritory);
			MapUtil.outPutMessgae(outPutConsole, "Adjacent territory removed.", true);
		}
	}

	/**
	 * Add new territory
	 * @param event action event
	 */
	@FXML
	private void addNewTerritory(ActionEvent event) {

		Continent continent = continentList.getSelectionModel().getSelectedItem();
		Territory adjTerritory = selectAdjTerritories.getSelectionModel().getSelectedItem();

		Territory territory = null;

		try {
			territory = mapModel.addTerritory(map, newTerritoryName.getText(), territoryXaxis.getText(),
					territoryYaxis.getText(), adjTerritory, continent);
		} catch (InvalidMapException ex) {
			MapUtil.infoBox(ex.getMessage(), "Error", "Invalid Map");
			return;
		}
		MapUtil.clearTextField(newTerritoryName, territoryXaxis, territoryYaxis);
		continent = mapModel.assignTerrToContinent(continent, territory);
		selectAdjTerritories.getItems().add(territory);
		territoryList.getItems().add(territory);
	}

	/**
	 * Delete territory
	 * @param event action event
	 */
	@FXML
	private void deleteTerritory(ActionEvent event) {
		Territory territory = territoryList.getSelectionModel().getSelectedItem();
		HashSet<Territory> territoryToBeRemovedFrom = new HashSet<>();
		Continent continent = continentList.getSelectionModel().getSelectedItem();

		if (continent != null && continent.getTerritories() != null) {
			if (continent.getTerritories().size() == 1) {
				MapUtil.outPutMessgae(outPutConsole,
						"There should be atleast one territory associated with the continent.", false);
				return;
			}
			for (Continent cont : map.getContinents()) {
				for (Territory terr : cont.getTerritories()) {
					if (terr.getAdjacentTerritories().contains(territory)
							&& (terr.getAdjacentTerritories().size() == 1)) {
						MapUtil.outPutMessgae(outPutConsole, "Territory: " + territory.getName()
								+ " is the only adjacent territory to " + terr.getName() + ", hence cannot delete.",
								false);
						return;
					}
					if (terr.getAdjacentTerritories().contains(territory)  && terr.getAdjacentTerritories().size()>1) {
						territoryToBeRemovedFrom.add(terr);
					}
				}
			}
			//If there was no exception than remove this territory from this other territory
			for (Territory t : territoryToBeRemovedFrom) {
				t.getAdjacentTerritories().remove(territory);
			}
			continent.getTerritories().remove(territory);
			territoryList.getItems().remove(territory);
			MapUtil.outPutMessgae(outPutConsole, "Territory removed successfully.", true);

		}
	}

	/*
	 * (non-Javadoc) Initialize view screen.
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (this.map == null) {
			map = new Map();
		} else {
			loadMapData();
		}

		riskImage = MapUtil.loadImageView(riskImage, getClass().getClassLoader());

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
				onMouseClickContinentList(event);
			}
		});

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
				onMouseClickTerritoryList(event);
			}
		});

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

	/**
	 * Load adjacent territory list
	 */
	private void loadAdjTerritoryList() {

		ObservableList<Territory> adjTerritoryList = FXCollections.observableArrayList();
		for (Continent continent : map.getContinents()) {
			for (Territory territory : continent.getTerritories()) {
				adjTerritoryList.add(territory);
			}
		}
		selectAdjTerritories.setItems(adjTerritoryList);
	}

	/**
	 * Update map data.
	 * @param map map object
	 * @return map updated map object
	 */
	private Map saveOrUpdateMapDetail(Map map) {

		map.getMapData().put("author", getDummyNameIfBlank(author.getText()));
		map.getMapData().put("image", getDummyNameIfBlank(image.getText()));
		map.getMapData().put("scroll", getDummyNameIfBlank(scroll.getText()));
		map.getMapData().put("warn", getDummyNameIfBlank(warn.getText()));
		map.getMapData().put("wrap", getDummyNameIfBlank(wrap.getText()));
		return map;
	}

	/**
	 * Get dummy if value blank
	 * 
	 * @param value string value
	 * @return String dummy if string is blank
	 */
	private String getDummyNameIfBlank(String value) {
		return StringUtils.isNotEmpty(value) ? value : "Dummy";
	}

	/**
	 * Mouse click event for continent list
	 * @param event action event
	 */
	private void onMouseClickContinentList(MouseEvent event) {
		Continent continent = continentList.getSelectionModel().getSelectedItem();
		selectedContinent.setText(continent.getName());
		newContinentName.setText(continent.getName());
		newContinentName.setDisable(true);
		newContinentValue.setText(continent.getValue());
		addContinent.setDisable(true);
		MapUtil.clearTextField(newTerritoryName, territoryXaxis, territoryYaxis);
		MapUtil.enableControl(newTerritoryName, addTerritory);
		adjTerritoryList.getItems().clear();
		outPutConsole.clear();
		populateTerritory(continentList.getSelectionModel().getSelectedItem());
	}

	/**
	 * Mouse click event for territory
	 * @param event action event
	 */
	private void onMouseClickTerritoryList(MouseEvent event) {
		Territory territory = territoryList.getSelectionModel().getSelectedItem();

		newTerritoryName.setText(territory.getName());
		territoryXaxis.setText(String.valueOf(territory.getxCoordinate()));
		territoryYaxis.setText(String.valueOf(territory.getyCoordinate()));
		newTerritoryName.setDisable(true);
		addTerritory.setDisable(true);
		outPutConsole.clear();

		populateAdjTerritory(territory);

	}
}
