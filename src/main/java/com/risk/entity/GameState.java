package com.risk.entity;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonBackReference;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 * This class is used to store Game State.
 * @author Vipul
 * @version 1.0.0
 *
 */
@JsonAutoDetect
public class GameState implements EventHandler<ActionEvent> {

	/**
	 * The @map reference.
	 */
	private Map map;

	/**
	 * The @selectedTerritoryList list of territories drop down.
	 */
	@JsonBackReference
	private List<Territory> selectedTerritoryList;

	/**
	 * The @adjTerritoryList adjacent territory list.
	 */
	@JsonBackReference 
	private ObservableList<Territory> adjTerritoryList;

	/**
	 * The @playerChosen current player playing.
	 */
	private Label playerChosen;

	/**
	 * The @gamePhase display current phase .
	 */
	private Label gamePhase;

	/**
	 * The @numberOfPlayersSelected .
	 */
	private int numberOfPlayersSelected;

	/**
	 * The @gamePlayerList list of players in the game.
	 */
	private List<Player> gamePlayerList;

	/**
	 * The @playerPlaying current player playing.
	 */
	private Player playerPlaying;

	/**
	 * The @cardStack.
	 */
	private Stack<Card> cardStack;

	/**
	 * The @numberOfCardSetExchanged.
	 */
	private int numberOfCardSetExchanged;

	/**
	 * The @playerIterator.
	 */
	private Iterator<Player> playerIterator;
	
	/**
	 * @return map object
	 */
	public Map getMap() {
		return map;
	}

	/**
	 * @param map
	 *            the map to set
	 */
	public void setMap(Map map) {
		this.map = map;
	}

	public List<Territory> getSelectedTerritoryList() {
		return selectedTerritoryList;
	}

	public void setSelectedTerritoryList(List<Territory> selectedTerritoryList) {
		this.selectedTerritoryList = selectedTerritoryList;
	}

	public ObservableList<Territory> getAdjTerritoryList() {
		return adjTerritoryList;
	}

	public void setAdjTerritoryList(ObservableList<Territory> adjTerritoryList) {
		this.adjTerritoryList = adjTerritoryList;
	}

	public Label getPlayerChosen() {
		return playerChosen;
	}

	public void setPlayerChosen(Label playerChosen) {
		this.playerChosen = playerChosen;
	}

	public Label getGamePhase() {
		return gamePhase;
	}

	public void setGamePhase(Label gamePhase) {
		this.gamePhase = gamePhase;
	}
	
	/**
	 * @return numberOfPlayersSelected
	 */
	public int getNumberOfPlayersSelected() {
		return numberOfPlayersSelected;
	}

	/**
	 * @param setNumberOfPlayersSelected
	 *            the setNumberOfPlayersSelected to set
	 */
	public void setNumberOfPlayersSelected(int numberOfPlayersSelected) {
		this.numberOfPlayersSelected = numberOfPlayersSelected;
	}

	public List<Player> getGamePlayerList() {
		return gamePlayerList;
	}

	public void setGamePlayerList(List<Player> gamePlayerList) {
		this.gamePlayerList = gamePlayerList;
	}
	
	/**
	 * @return playerPlaying Object
	 */
	public Player getPlayerPlaying() {
		return playerPlaying;
	}
	
	/**
	 * @param playerPlaying
	 *            the playerPlaying to set
	 */
	public void setPlayerPlaying(Player playerPlaying) {
		this.playerPlaying = playerPlaying;
	}

	public Stack<Card> getCardStack() {
		return cardStack;
	}

	public void setCardStack(Stack<Card> cardStack) {
		this.cardStack = cardStack;
	}
	
	/**
	 * @return numberOfCardSetExchanged 
	 */
	public int getNumberOfCardSetExchanged() {
		return numberOfCardSetExchanged;
	}
	
	/**
	 * @param numberOfCardSetExchanged
	 *            the numberOfCardSetExchanged to set
	 */
	public void setNumberOfCardSetExchanged(int numberOfCardSetExchanged) {
		this.numberOfCardSetExchanged = numberOfCardSetExchanged;
	}

	public Iterator<Player> getPlayerIterator() {
		return playerIterator;
	}

	public void setPlayerIterator(Iterator<Player> playerIterator) {
		this.playerIterator = playerIterator;
	}
	
	/**
	 * Constructor for GameState
	 * 
	 * @param map
	 * @param selectedTerritoryList2
	 * @param adjTerritoryList2
	 * @param playerChosen
	 * @param gamePhase
	 * @param numberOfPlayersSelected
	 * @param gamePlayerList
	 * @param playerPlaying  
	 * @param cardStack 
	 * @param numberOfCardSetExchanged   
	 * @param playerIterator     
	 */
	public GameState(Map map,ListView<Territory> selectedTerritoryList2,ListView<Territory> adjTerritoryList2,Label playerChosen,Label gamePhase,int numberOfPlayersSelected,List<Player> gamePlayerList, Player playerPlaying,Stack<Card> cardStack,int numberOfCardSetExchanged,Iterator<Player> playerIterator)  { 
		this.map = map;
		this.selectedTerritoryList=(List<Territory>) selectedTerritoryList2.getItems();
		this.adjTerritoryList = adjTerritoryList2.getItems();
		this.playerChosen=playerChosen;
		this.gamePhase=gamePhase;
		this.numberOfPlayersSelected=numberOfPlayersSelected;
		this.gamePlayerList = gamePlayerList;
		this.playerPlaying=playerPlaying;
		this.cardStack=cardStack;
		this.numberOfCardSetExchanged = numberOfCardSetExchanged;
		this.playerIterator = playerIterator;
	}

	//Introducing the dummy constructor to remove org.codehaus.jackson.map.JsonMappingException error
	public GameState() {
	}

	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub

	}

}
