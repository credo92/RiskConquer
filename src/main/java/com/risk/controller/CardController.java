package com.risk.controller;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.risk.constant.CardType;
import com.risk.entity.Card;
import com.risk.entity.Player;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

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
	 * The @addedArmies label.
	 */
	@FXML
	private Label addedArmies;

	/**
	 * The @cardVbox .
	 */
	@FXML
	private VBox cardVbox;

	private ObservableSet<CheckBox> selectedCheckBoxes = FXCollections.observableSet();

	private ObservableSet<CheckBox> unselectedCheckBoxes = FXCollections.observableSet();

	private IntegerBinding numCheckBoxesSelected = Bindings.size(selectedCheckBoxes);

	private final int maxNumSelected =  3; 
	
	private Player player;
	
	CardController(Player player){
		this.player = player;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

//		ObservableList<String> cardList = FXCollections.<String>observableArrayList("Infantry", "Cavalry", "Artillery","Infantry", "Cavalry", "Artillery");
	
		currentPlayerName.setText(player.getName());
		
		cardVbox.setPadding(new Insets(15,20, 10,10));
		cardVbox.setSpacing(10);   
		CheckBox[] cbs = new CheckBox[player.getPlayerCardList().size()];

		for (int i = 0; i < player.getPlayerCardList().size(); i++){
//			CheckBox cb = cbs[i] = new CheckBox(player.getPlayerCardList().get(i).getCardType().name()+"->"+player.getPlayerCardList().get(i).getTerritory().getName().toString()+" with "+player.getPlayerCardList().get(i).getTerritory().getArmies()+" armies");
			CheckBox cb = cbs[i] = new CheckBox(player.getPlayerCardList().get(i).getCardType().name()+"->"+player.getPlayerCardList().get(i).getTerritory().getName().toString());
			configureCheckBox(cb);
		}
		
		for(int i=0;i<cbs.length;i++){
			
		}

		cardVbox.getChildren().addAll(cbs);

		numCheckBoxesSelected.addListener((obs, oldSelectedCount, newSelectedCount) -> {
			if (newSelectedCount.intValue() >= maxNumSelected) {
				unselectedCheckBoxes.forEach(cb -> cb.setDisable(true));
				trade.setDisable(false);
			} else {
				unselectedCheckBoxes.forEach(cb -> cb.setDisable(false));
				trade.setDisable(true);
			}
		});

	}

	/**
	 * trade
	 * @param event
	 *            event.
	 */
	@FXML
	private void trade(ActionEvent event) {
		
	}
	/**
	 * configureCheckbox
	 * @param checkBox
	 *            adds checkboxes to Observable sets to make sure only 3 checkboxes can be selected
	 */
	private void configureCheckBox(CheckBox checkBox) {

        if (checkBox.isSelected()) {
            selectedCheckBoxes.add(checkBox);
        } else {
            unselectedCheckBoxes.add(checkBox);
        }

        checkBox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected) {
                unselectedCheckBoxes.remove(checkBox);
                selectedCheckBoxes.add(checkBox);
            } else {
                selectedCheckBoxes.remove(checkBox);
                unselectedCheckBoxes.add(checkBox);
            }

        });

    }



}
