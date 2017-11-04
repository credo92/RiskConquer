package com.risk.controller;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.risk.entity.Player;
import com.risk.map.util.MapUtil;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.collections.FXCollections;
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
	 * The @cardVbox .
	 */
	@FXML
	private VBox cardVbox;

	private ObservableSet<CheckBox> selectedCheckBoxes = FXCollections.observableSet();

	private ObservableSet<CheckBox> unselectedCheckBoxes = FXCollections.observableSet();

	private IntegerBinding numCheckBoxesSelected = Bindings.size(selectedCheckBoxes);

	private final int maxNumSelected =  3; 

	private Player playerPlaying;

	private CheckBox[] cbs;

	private ArrayList<String> selected = new ArrayList<String>();

	public CardController(Player playerPlaying){
		this.playerPlaying = playerPlaying;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		currentPlayerName.setText(playerPlaying.getName());

		cardVbox.setPadding(new Insets(15,20, 10,10));
		cardVbox.setSpacing(10);   
		cbs = new CheckBox[playerPlaying.getPlayerCardList().size()];

		for (int i = 0; i < playerPlaying.getPlayerCardList().size(); i++){
			//			CheckBox cb = cbs[i] = new CheckBox(playerPlaying.getPlayerCardList().get(i).getCardType().name()+"->"+playerPlaying.getPlayerCardList().get(i).getTerritory().getName());
			CheckBox cb = cbs[i] = new CheckBox(playerPlaying.getPlayerCardList().get(i).getCardType().name());
			configureCheckBox(cb);
			makeCheckboxSelectedList(cb);

		}

		cardVbox.getChildren().addAll(cbs);

		numCheckBoxesSelected.addListener((obs, oldSelectedCount, newSelectedCount) -> {
			if (newSelectedCount.intValue() == maxNumSelected) {
				unselectedCheckBoxes.forEach(cb -> cb.setDisable(true));
				trade.setDisable(false);
			} else {
				unselectedCheckBoxes.forEach(cb -> cb.setDisable(false));
				trade.setDisable(true);
			}
		});

	}

	/**
	 * configureCheckbox
	 * @param checkBox
	 *            adds checkboxes to Observable sets to make sure only 3 checkboxes can be selected
	 */
	public void configureCheckBox(CheckBox checkBox) {

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

	/**
	 * makeCheckboxSelectedList
	 * @param checkBox
	 *            builds the selected Checkboxes list
	 */
	public void makeCheckboxSelectedList(CheckBox checkBox) {

		checkBox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
			if (isNowSelected) {
				//				selected.add(checkBox.getText().substring(0,checkBox.getText().indexOf("-") ).trim());
				selected.add(checkBox.getText());
			} else {
				//				selected.remove(checkBox.getText().substring(0,checkBox.getText().indexOf("-") ).trim());
				selected.remove(checkBox.getText());
			}

		});
	}


	/**
	 * checkCardSetCheckbox
	 * @param checkBox selected
	 *            checks the selected checkboxes if they are valid or not
	 */
	public Boolean checkCardSetCheckbox(ArrayList<String> selected) {
		Boolean validSetBool = null;
		if (  ( (selected.get(0) != selected.get(1) ) && (selected.get(1) != selected.get(2))  ) && (selected.get(0) != selected.get(2))  ) 
		{

			System.out.println("Valid Card Set 1");
			System.out.println(selected);
			validSetBool = true;
		}

		else if (  ( (selected.get(0) == selected.get(1) ) && (selected.get(1) == selected.get(2) )  ) && (selected.get(0) == selected.get(2))  ) {
			System.out.println("Valid Card Set 2");
			System.out.println(selected);
			validSetBool = true;
		}

		else {
			MapUtil.infoBox("Please Select valid card set", "Message", "");
		}

		return validSetBool;
	}

	/**
	 * trade
	 * @param event
	 *            event.
	 */
	@FXML
	private void trade(ActionEvent event) {
		System.out.println(selected);
		Boolean tradeButtonToggle = checkCardSetCheckbox(selected);
		if(tradeButtonToggle!=null && tradeButtonToggle) {
			System.out.println("inside trade event");
		}
		else if(tradeButtonToggle == null) {
			System.out.println("inside trade event");
		}
		else {
			System.out.println("inside trade event");
		}
	}

}





