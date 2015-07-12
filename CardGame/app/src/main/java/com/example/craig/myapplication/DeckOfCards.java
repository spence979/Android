package com.example.craig.myapplication;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

public class DeckOfCards{
    public int currentCard; // index of next Card to be dealt

   ArrayList<String> CardDeck = new ArrayList<String>();
   public static ArrayList<String> UsedCards = new ArrayList<String>();

   public DeckOfCards()
   {
      String faces[] = { "ace", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "jack", "queen", "king" };
      String suits[] = { "hearts", "diamonds", "clubs", "spades" };

      currentCard = 0; // set currentCard so first Card dealt is deck[ 0 ]
      
      for(int Suit = 0; Suit < suits.length; Suit ++){
          for(int Face = 0; Face < faces.length; Face ++){
        	  CardDeck.add(faces[Face] + "_of_" + suits[Suit]);
          }
      }
   }

   	public void shuffle(){
   		currentCard = 0;
   		Collections.shuffle(this.CardDeck);
   	}

   public String dealCard(){
      if ( currentCard < (CardDeck.size()))
    	  return CardDeck.get(currentCard ++);
      else{
          try {
              String LastCard = UsedCards.get(UsedCards.size() - 1);
              CardDeck = new ArrayList<String>(UsedCards);
              UsedCards.clear();
              UsedCards.add(LastCard);
              CardDeck.remove(CardDeck.size() - 1);
          }catch(Exception X){
              System.out.println("Error: " + X.getMessage());
          }
    	  shuffle();
    	  return dealCard();
      }
   }
}