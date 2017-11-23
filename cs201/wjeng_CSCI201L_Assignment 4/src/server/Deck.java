package server;

import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

public class Deck {
	private Random rand;
	private String[] suit = {"HEARTS", "DIAMONDS", "CLUBS", "SPADES"};
	private String[] rank = {"ACE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE", "TEN", "JACK", "QUEEN", "KING"};
	private Set<String> deck = new LinkedHashSet<String>();
	
	//Create a deck
	//Using set to guarantee  duplicate
	//Use random number between 0-3 and 0-12 to make combinations of card
	public Deck() {
		rand = new Random();
		rand.setSeed(System.currentTimeMillis());
		while(deck.size()<52){
			int suit = rand.nextInt(4);
			int rank = rand.nextInt(13);
			String card = this.rank[rank] +" of " + this.suit[suit];
			deck.add(card);
		}
	}
	
	//return deck
	public Vector<String> getDeck(){
		Vector<String> allCards = new Vector<String>();
		for(String cards:deck) {
			allCards.add(cards);
		}
		return allCards;
	}
}
