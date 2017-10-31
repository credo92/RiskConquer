package com.risk.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.risk.entity.Territory;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

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
	private Button tradeButton; 
	
	/**
	 * The @currentPlayerId label.
	 */
	@FXML
	private Label currentPlayerId;
	
	/**
	 * The @currentPlayerArmies label.
	 */
	@FXML
	private Label currentPlayerArmies;
	
	/**
	 * The @TerritoryName label.
	 */
	@FXML
	private Label territoryName;
	
	/**
	 * The @cardType label.
	 */
	@FXML
	private Label cardType;
	
	/**
	 * The @cardCheckBox checkbox.
	 */
	@FXML
	private CheckBox cardCheckBox;

	/**
	 * The @addedArmies label.
	 */
	@FXML
	private Label addedArmies;
	
	/**
	 * The @cardListView list view with checkboxes.
	 */
	@FXML
	private ListView<Territory> cardListView;
	
	private List<Territory> territory;
	
	CardController(List<Territory> allterritories){
		this.territory = allterritories;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		System.out.println(territory);
		
		cardListView.setCellFactory(param -> new ListCell<Territory>() {
			@Override
			protected void updateItem(Territory item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null || item.getName() == null) {
					setText(null);
				} else {
					setText(item.getName() + ":-" + item.getArmies() + "-" + item.getPlayer().getName());
				}
			}
		});
	
	}
	
	
	
}