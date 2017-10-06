package com.app.team19;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class MapEditorController implements Initializable {
	
	private Map map;

	@FXML
	private TextField author;

	@FXML
	private TextField image;

	@FXML
	private Button exitButton;

	@FXML
	private Button loadMap;

	@FXML
	private Button saveMap;

	@FXML
	private void mapEditorExit(ActionEvent event) {
		Platform.exit();
	}

	@FXML
	private void loadExistingMap(ActionEvent event) {

		MapFileParser fileParser = new MapFileParser();
		map = fileParser.parseAndReadMapFile();
		//author.textProperty().bindBidirectional(map.getAuthor());
		//System.out.println(map.toString());
	}

	@FXML
	private void saveMap(ActionEvent event) {
		MapFileWriter fileWriter = new MapFileWriter();
		FileChooser fileChooser = new FileChooser();
		  
        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Map files (*.map)", "*.map");
        fileChooser.getExtensionFilters().add(extFilter);
        
        //Show save file dialog
        File file = fileChooser.showSaveDialog(null);
        
        if(file != null){
        	fileWriter.writeMapToFile(map, file);
        }
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
}
