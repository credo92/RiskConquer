package com.app.team19.gameplay;

public class GameplayController {

    private int number;

    public int selectedNumberOfPlayers(int number){
        this.number = number;
        System.out.println(this.number);
        return this.number;
    }
}
