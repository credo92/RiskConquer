package com.risk.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.risk.constant.CardType;
import com.risk.entity.Card;
import com.risk.entity.Continent;
import com.risk.entity.Territory;

/**
 * Card Model Test class
 * @author Garvpreet Singh
 * @version 1.0.1
 */
public class CardModelTest {
	
	/**
	 * The @cardModel reference
	 */
	static CardModel cardModel;	
	
	/**
	 * The @listOfCards
	 */
	static List<Card> listOfCards;
	
	/**
	 * The @listOfContinents
	 */
	static List<Continent> listOfContinents;
	
	/**
	 * The @listOfTerr
	 */
	static List<Territory> listOfTerr;
	
	/**
	 * This method is invoked at the start of the test class.
	 */
	@BeforeClass
	public static void beforeClass() {		
		cardModel = new CardModel();
		listOfCards = new ArrayList<>();
		listOfContinents = new ArrayList<>();
		listOfTerr = new ArrayList<>();
	}
	
	/**
	 * This method is invoked at the start of the every test.
	 */
	@Before
	public void beforeTest() {
		listOfCards.clear();		
	}
	
	/**
	 * This method is used to check different combinations of cards and if trade is possible or not.
	 */
	@Test
	public void checkTradePossibleForDiffCards() {
		listOfCards.add(new Card(CardType.ARTILLERY));
		listOfCards.add(new Card(CardType.CAVALRY));
		listOfCards.add(new Card(CardType.INFANTRY));	
		boolean actualResult = cardModel.checkTradePossible(listOfCards);
		Assert.assertEquals(true, actualResult);		
	}
	
	/**
	 * This method is used to check similar combinations of cards (CAVALRY/INFANTRY/ARTILLERY) and if trade is possible or not.
	 */
	@Test
	public void checkTradePossibleForSimilarCards() {		
		listOfCards.add(new Card(CardType.CAVALRY));
		listOfCards.add(new Card(CardType.CAVALRY));
		listOfCards.add(new Card(CardType.CAVALRY));
		boolean actualResult = cardModel.checkTradePossible(listOfCards);
		Assert.assertEquals(true, actualResult);
		listOfCards.clear();	
		listOfCards.add(new Card(CardType.INFANTRY));
		listOfCards.add(new Card(CardType.INFANTRY));
		listOfCards.add(new Card(CardType.INFANTRY));
		boolean actualResult1 = cardModel.checkTradePossible(listOfCards);
		Assert.assertEquals(true, actualResult1);	
		listOfCards.clear();	
		listOfCards.add(new Card(CardType.ARTILLERY));
		listOfCards.add(new Card(CardType.ARTILLERY));
		listOfCards.add(new Card(CardType.ARTILLERY));
		boolean actualResult2 = cardModel.checkTradePossible(listOfCards);
		Assert.assertEquals(true, actualResult2);
	}
	
	/**
	 * This method is used to check number of cards are not 3 and if trade is possible or not.
	 */
	@Test
	public void checkTradePossibleForNNumberOfCardsFailure() {
		listOfCards.add(new Card(CardType.CAVALRY));
		listOfCards.add(new Card(CardType.INFANTRY));
		listOfCards.add(new Card(CardType.ARTILLERY));
		listOfCards.add(listOfCards.get(0));
		listOfCards.add(listOfCards.get(1));
		listOfCards.add(listOfCards.get(2));
		boolean actualResult = cardModel.checkTradePossible(listOfCards);
		Assert.assertEquals(false, actualResult);
	}
	
	/**
	 * This method is used to check number of cards are 3 and if trade is possible or not.
	 */
	@Test
	public void checkTradePossibleForNNumberOfCardsSuccess() {
		listOfCards.add(new Card(CardType.CAVALRY));
		listOfCards.add(new Card(CardType.INFANTRY));
		listOfCards.add(new Card(CardType.ARTILLERY));
		boolean actualResult = cardModel.checkTradePossible(listOfCards);
		Assert.assertEquals(true, actualResult);
	}
}
