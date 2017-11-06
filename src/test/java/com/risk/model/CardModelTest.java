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
	
	static CardModel cardModel;
	
	List<Card> listOfCards = new ArrayList<>();
	List<Continent> listOfContinents = new ArrayList<>();	
	List<Territory> listOfTerr = new ArrayList<>();
	
	/**
	 * This method is invoked at the start of the test class.
	 */
	@BeforeClass
	public static void beforeClass() {
		cardModel = new CardModel();
	}
	
	/**
	 * This method is invoked at the start of the every test.
	 */
	@Before
	public void beforeTest() {
				
	}
	
	/**
	 * This method is used to check different combinations of cards and if trade is possible or not.
	 */
	@Test
	public void checkTradePossible() {
		listOfCards.add(new Card(CardType.ARTILLERY));
		listOfCards.add(new Card(CardType.CAVALRY));
		listOfCards.add(new Card(CardType.INFANTRY));	
		boolean expectedResult = cardModel.checkTradePossible(listOfCards);
		Assert.assertEquals(expectedResult, true);
		listOfCards.clear();
		listOfCards.add(new Card(CardType.ARTILLERY));
		listOfCards.add(new Card(CardType.ARTILLERY));
		listOfCards.add(new Card(CardType.ARTILLERY));
		expectedResult = cardModel.checkTradePossible(listOfCards);
		Assert.assertEquals(expectedResult, true);
		listOfCards.clear();
		listOfCards.add(new Card(CardType.CAVALRY));
		listOfCards.add(new Card(CardType.CAVALRY));
		listOfCards.add(new Card(CardType.CAVALRY));
		expectedResult = cardModel.checkTradePossible(listOfCards);
		Assert.assertEquals(expectedResult, true);
		listOfCards.clear();
		listOfCards.add(new Card(CardType.INFANTRY));
		listOfCards.add(new Card(CardType.INFANTRY));
		listOfCards.add(new Card(CardType.INFANTRY));
		expectedResult = cardModel.checkTradePossible(listOfCards);
		Assert.assertEquals(expectedResult, true);
		listOfCards.clear();
		listOfCards.add(new Card(CardType.CAVALRY));
		listOfCards.add(new Card(CardType.INFANTRY));
		listOfCards.add(new Card(CardType.INFANTRY));
		expectedResult = cardModel.checkTradePossible(listOfCards);
		Assert.assertEquals(expectedResult, false);
		listOfCards.clear();
		listOfCards.add(new Card(CardType.CAVALRY));
		listOfCards.add(new Card(CardType.CAVALRY));
		listOfCards.add(new Card(CardType.INFANTRY));
		expectedResult = cardModel.checkTradePossible(listOfCards);
		Assert.assertEquals(expectedResult, false);
		listOfCards.clear();
		listOfCards.add(new Card(CardType.ARTILLERY));
		listOfCards.add(new Card(CardType.ARTILLERY));
		listOfCards.add(new Card(CardType.INFANTRY));
		expectedResult = cardModel.checkTradePossible(listOfCards);
		Assert.assertEquals(expectedResult, false);
	}
}
