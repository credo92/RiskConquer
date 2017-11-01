package com.risk.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.risk.entity.Player;
import com.risk.entity.Territory;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Vipul Srivastav
 * CardController controller for Card Exchange
 * @version 1.0.1
 */
public class CardController implements Initializable {


	/**
	 * The @tradeButton button.
	 */
	@FXML
	private Button trade; 

	/**
	 * The @currentPlayerId label.
	 */
	@FXML
	private Label currentPlayerName;

	/**
	 * The @currentPlayerArmies label.
	 */
	@FXML
	private Label currentPlayerArmies;

	/**
	 * The @addedArmies label.
	 */
	@FXML
	private Label addedArmies;

	/**
	 * The @cardListView list view.
	 */
	@FXML
	private ListView<Territory> cardListView;

	/**
	 * The @cardListView list view.
	 */
	@FXML
	private VBox cardVbox;

	private List<Territory> territory;
	
	private final int maxNumSelected =  3; 

	CardController(List<Territory> allterritories){
		this.territory = allterritories;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		System.out.println(territory);

		ObservableList<String> cardList = FXCollections.<String>observableArrayList("Infantry", "Cavalry", "Artillery","Infantry", "Cavalry", "Artillery");

		cardVbox.setPadding(new Insets(15,20, 10,10));
		cardVbox.setSpacing(10);   

		for(String string: cardList){
			cardVbox.getChildren().add(new CheckBox(string));
		}
	}

	/**
	 * trade
	 * @param event
	 *            event.
	 */
	@FXML
	private void trade(ActionEvent event) {
		
	}



}