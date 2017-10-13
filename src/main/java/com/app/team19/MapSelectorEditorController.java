package com.app.team19;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import com.risk.entity.Map;
import com.risk.map.util.MapFileParser;
import com.risk.map.util.MapUtil;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MapSelectorEditorController implements Initializable {
	
	private Map map;
	
	@FXML
	private Button loadMap;
	
	@FXML
	private Button exit;
	
	@FXML
	private void loadExistingMap(ActionEvent event) {

		MapFileParser fileParser = new MapFileParser();
		File file = MapUtil.showFileChooser();
		map = fileParser.parseAndReadMapFile(file);
		
		final Stage mapSelectorStage = new Stage();
		mapSelectorStage.setTitle("Map Selector");
		Scene scene = null;
		try {
			scene = new Scene(FXMLLoader.load(getClass().getResource("PlayerSelectorLayout.fxml")));
		} catch (Exception e) {
			e.printStackTrace();
		}	
		mapSelectorStage.setScene(scene);
		mapSelectorStage.show();
	}
	
	@FXML
	private void mapSelectorEditorExit(ActionEvent event) {
		System.out.println("Exiting the platform");
		Platform.exit();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub		
	}
}