package com.app.team19;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class MapEditorController implements Initializable {

	@FXML
	private TextField author;

	@FXML
	private TextField image;

	@FXML
	private Button exitButton;

	@FXML
	private Button loadMap;

	@FXML
	private void mapEditorExit(ActionEvent event) {
		Platform.exit();
	}

	@FXML
	private void loadExistingMap(ActionEvent event) {
		
		MapFileParser fileParser = new MapFileParser();
		Map map = fileParser.parseAndReadMapFile();
		this.author.setText(map.getMapData().get("author"));
		System.out.println(map.toString());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
}
