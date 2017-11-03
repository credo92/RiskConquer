package com.risk.controller;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import org.loadui.testfx.GuiTest;

public class DiceRollControllerTest extends GuiTest{

	CheckBox attackerDice1;
	CheckBox attackerDice2;
	CheckBox attackerDice3;
	
	@Override
	protected Parent getRootNode() {
		/* Parent parent = null;
	        try {
	            parent = FXMLLoader.load(getClass().getClassLoader().getResource("DiceView.fxml"));
	            return parent;
	        } catch (IOException ex) {
	            // TODO ...
	        }
	        //Parent root = (Parent) loader.load();
*/
	        
	        
	        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("DiceView.fxml"));
	        Parent root = null;
	        try {
				root =  loader.load(this.getClass().getClassLoader().getResource("DiceView.fxml").openStream());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			
			/*try {
				root = (Parent) loader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}*/
	        return root;

	}

	
	@Test
	public void checkIfFail() {
		
		attackerDice1 = find("#attackerDice1");
		attackerDice1.setText("4");
        

		//assertEquals("4", attackerDice1.getText());
	}



}
