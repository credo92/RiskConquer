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
import com.risk.exception.InvalidMapException;
import com.risk.map.util.MapUtil;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MapEditorController implements Initializable {

	private Map map;

	private File file;

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
	private ListView<Territory> adjTerritoryList;

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
	private Button addContinent;

	@FXML
	private Button updateCont;

	@FXML
	private Button addTerritory;

	@FXML
	private Button updateTerrt;

	@FXML
	private Button deleteAdjTerr;

	@FXML
	private TextArea outPutConsole;

	@FXML
	private ComboBox<Territory> selectAdjTerritories;

	public static final ObservableList<Continent> continentData = FXCollections.observableArrayList();

	public static final ObservableList<Territory> territoryData = FXCollections.observableArrayList();

	public static final ObservableList<Territory> adjTerritoryData = FXCollections.observableArrayList();

	public MapEditorController() {
	}

	public MapEditorController(Map map, File file) {
		this.map = map;
		this.file = file;
	}

	@FXML
	private void updateContinent(ActionEvent event) {
		Continent continent = continentList.getSelectionModel().getSelectedItem();
		continent.setValue(newContinentValue.getText());

		newContinentName.setDisable(false);
		addContinent.setDisable(false);
		MapUtil.clearTextField(newContinentName, newContinentValue);
	}

	@FXML
	private void updateTerritory(ActionEvent event) {
		Territory territory = territoryList.getSelectionModel().getSelectedItem();

		territory.setxCoordinate(Integer.valueOf(territoryXaxis.getText()));
		territory.setyCoordinate(Integer.valueOf(territoryYaxis.getText()));

		Territory adjTerritory = selectAdjTerritories.getSelectionModel().getSelectedItem();
		if (adjTerritory != null) {
			if (!territory.getAdjacentTerritories().contains(adjTerritory))
				territory.getAdjacentTerritories().add(adjTerritory);
		}
		newTerritoryName.setDisable(false);
		addTerritory.setDisable(false);
		MapUtil.clearTextField(newTerritoryName, territoryXaxis, territoryYaxis);
	}

	@FXML
	private void addNewContinent(ActionEvent event) {

		Continent continent = new Continent();

		continent.setName(newContinentName.getText());
		continent.setValue(newContinentValue.getText());

		// continentData.add(continent);
		if (continentList == null) {
			continentList = new ListView<Continent>();
		}
		continentList.getItems().add(continent);
		map.getContinents().add(continent);
		MapUtil.clearTextField(newContinentName, newContinentValue);

	}

	@FXML
	private void mapEditorExit(ActionEvent event) {
		Stage stage = (Stage) exitButton.getScene().getWindow();
		stage.close();
	}

	private void loadMapData() {

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
	}

	private void populateTerritory(Continent continent) {
		territoryData.clear();
		adjTerritoryData.clear();
		if (continent != null && continent.getTerritories() != null) {
			for (Territory territory : continent.getTerritories()) {
				territoryData.add(territory);
			}
			territoryList.setItems(territoryData);
		}
	}

	private void populateAdjTerritory(Territory territory) {
		adjTerritoryData.clear();
		for (Territory adjTerritory : territory.getAdjacentTerritories()) {
			if (adjTerritory != null) {
				adjTerritoryData.add(adjTerritory);
			}
		}
		adjTerritoryList.setItems(adjTerritoryData);
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
	 * @param event
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

	@FXML
	private void addNewTerritory(ActionEvent event) {

		Territory territory = new Territory();
		List<Territory> tList = new ArrayList<>();

		territory.setName(newTerritoryName.getText());
		territory.setxCoordinate(Integer.parseInt(territoryXaxis.getText()));
		territory.setyCoordinate(Integer.parseInt(territoryYaxis.getText()));
		territory.setBelongToContinent(continentList.getSelectionModel().getSelectedItem());

		Territory adjTerritory = selectAdjTerritories.getSelectionModel().getSelectedItem();
		if (adjTerritory != null) {
			tList.add(adjTerritory);
		}
		territory.setAdjacentTerritories(tList);

		if (continentList.getSelectionModel().getSelectedItem().getTerritories() == null) {
			List<Territory> newTList = new ArrayList<>();
			newTList.add(territory);
			continentList.getSelectionModel().getSelectedItem().setTerritories(newTList);
		} else {
			continentList.getSelectionModel().getSelectedItem().getTerritories().add(territory);
		}
		selectAdjTerritories.getItems().add(territory);
		territoryList.getItems().add(territory);
	}

	@FXML
	private void deleteTerritory(ActionEvent event) {
		Territory territory = territoryList.getSelectionModel().getSelectedItem();

		Continent continent = continentList.getSelectionModel().getSelectedItem();

		if (continent != null && continent.getTerritories() != null) {
			if (continent.getTerritories().size() == 1) {
				MapUtil.outPutMessgae(outPutConsole,
						"There should be atleast one territory associated with the continent.", false);
				return;
			}
			continent.getTerritories().remove(territory);
			territoryList.getItems().remove(territory);
			MapUtil.outPutMessgae(outPutConsole, "Territory removed successfully.", true);

		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (this.map == null) {
			map = new Map();
		} else {
			loadMapData();
		}
    
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(new File(getClass().getClassLoader().getResource("risk.jpg").getFile()));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Image image = new Image(inputStream);
		riskImage.setImage(image);
	

		/// initialize continent list**********start*****************
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
				Continent continent = continentList.getSelectionModel().getSelectedItem();
				selectedContinent.setText(continent.getName());
				newContinentName.setText(continent.getName());
				newContinentName.setDisable(true);
				newContinentValue.setText(continent.getValue());
				addContinent.setDisable(true);
				outPutConsole.clear();
				populateTerritory(continentList.getSelectionModel().getSelectedItem());
			}
		});
		/// initialize continent list******done************************

		/// initialize territory list********start*********************
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
				Territory territory = territoryList.getSelectionModel().getSelectedItem();

				newTerritoryName.setText(territory.getName());
				territoryXaxis.setText(String.valueOf(territory.getxCoordinate()));
				territoryYaxis.setText(String.valueOf(territory.getyCoordinate()));
				newTerritoryName.setDisable(true);
				addTerritory.setDisable(true);
				outPutConsole.clear();

				populateAdjTerritory(territory);
			}
		});
		/// initialize territory list*******done***************

		/// initialize adjacentterritory list **********start**************
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
		/// initialize adjacentterritory list*******done*******************
	}

	private void loadAdjTerritoryList() {

		ObservableList<Territory> adjTerritoryList = FXCollections.observableArrayList();
		for (Continent continent : map.getContinents()) {
			for (Territory territory : continent.getTerritories()) {
				adjTerritoryList.add(territory);
			}
		}
		selectAdjTerritories.setItems(adjTerritoryList);
	}

	private Map saveOrUpdateMapDetail(Map map) {

		map.getMapData().put("author", author.getText());
		map.getMapData().put("image", image.getText());
		map.getMapData().put("scroll", scroll.getText());
		map.getMapData().put("warn", warn.getText());
		map.getMapData().put("wrap", wrap.getText());
		return map;
	}
}
