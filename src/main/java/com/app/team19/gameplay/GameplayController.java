package com.app.team19.gameplay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameplayController {

    private int number;
    /**
     * players object holds the players information
     * @author vipulsrivastav
     */
//    private ArrayList<Player> players;
    
    
    public int selectedNumberOfPlayers(int number){
        this.number = number;
        System.out.println(this.number);
        return this.number;
    }
    /**Turn Order
     * @author vipulsrivastav
     * @param numberOfPlayers
     * @param players 
     */
    public void turnOrder(int numberOfPlayers, List<?> players) {
    	// Update according to Player.java class
    	Collections.shuffle(players);
    	//nextPlayer();
    }
    
    
    
}
