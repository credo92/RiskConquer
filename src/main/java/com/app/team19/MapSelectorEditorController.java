package com.app.team19;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class MapSelectorEditorController implements Initializable {

	@FXML
	private ChoiceBox<Integer> numberOfPlayersCB;
	
	@FXML
	private TextArea gameConsole;
	
	private int numberOfPlayersSelected;
	
	public int getNumberOfPlayersSelected() {
		return numberOfPlayersSelected;
	}

	public void setNumberOfPlayersSelected(int numberOfPlayersSelected) {
		this.numberOfPlayersSelected = numberOfPlayersSelected;
	}
	
	public void initializeTotalPlayers() {
		numberOfPlayersCB.getItems().removeAll(numberOfPlayersCB.getItems());
		numberOfPlayersCB.getItems().addAll(2,3,4,5);
	}
	
	public void selectionOfPlayersListener() {
		numberOfPlayersCB.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
				setNumberOfPlayersSelected(numberOfPlayersCB.getSelectionModel().getSelectedItem());				
			}
		});	
	}

	public void appendTextToGameConsole(String valueOf) {
        Platform.runLater(() -> gameConsole.appendText(valueOf));
    }
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeTotalPlayers();
		selectionOfPlayersListener();			
	}	
}