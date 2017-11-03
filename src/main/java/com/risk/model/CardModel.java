package com.risk.model;

import java.util.Observable;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.scene.control.CheckBox;

/**
 * @author Vipul Srivastav Card model to send data
 * @version 1.0.1
 */
public class CardModel  {
	
	private ObservableSet<CheckBox> selectedCheckBoxes = FXCollections.observableSet();

	private ObservableSet<CheckBox> unselectedCheckBoxes = FXCollections.observableSet();

	private IntegerBinding numCheckBoxesSelected = Bindings.size(selectedCheckBoxes);
	
	private final int maxNumSelected =  3; 
	
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
}
