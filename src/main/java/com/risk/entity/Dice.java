package com.risk.entity;

import java.util.ArrayList;
import java.util.List;

import com.risk.constant.DiceType;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * @author Gurpreet
 * This is an entity class for Dice with DiceType enum ,Player object, values integer and diceList
 * @version 1.0.1
 * 
 */

public class Dice implements EventHandler<ActionEvent> {
	DiceType diceType;
	
	private int value;
	private Player player;
	private List<Dice> diceList;
	
	public Dice() {
		diceList = new ArrayList<>();
	}
	
	/**
	 * @return the diceType
	 */
	public DiceType getDicetype() {
		return diceType;
	}

	/**
	 * @param diceType
	 *            the diceType to set
	 */
	public void setDicetype(DiceType diceType) {
		this.diceType = diceType;
	}
	
	/**
	 * @return the value
	 */
	public int getValues() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValues(int value) {
		this.value = value;
	}
	
	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @param player
	 *            the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	/**
	 * @return the diceList
	 */
	public List<Dice> getDice() {
		return diceList;
	}

	/**
	 * @param diceList
	 *            the diceList to set
	 */
	public void setDice(List<Dice> diceList) {
		this.diceList = diceList;
	}

	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}	
}
