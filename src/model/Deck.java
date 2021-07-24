package model;

import java.util.ArrayList;

public class Deck {
	
	private ArrayList<Card> deck = new ArrayList<Card>();
	
	public Deck() {
		this.deck = new ArrayList<Card>();
		this.generateNewDeck(1);
	}
	
	public Deck(int num_of_decks) {
		if (num_of_decks >= 1)
			this.generateNewDeck(num_of_decks);
		else
			this.generateNewDeck(1);
	}
	
	public void generateNewDeck(int num_of_decks) {
		this.deck = new ArrayList<Card>();
		for (int deck = 0; deck < num_of_decks; deck++) {
			for (int suit = 0; suit < 4; suit++) {
				for (int val = 2; val < 15; val++) {
					this.deck.add(new Card(suit,val));
				}
			}
		}
	}
	
	public int getDeckSize() {
		if (this.deck != null)
			return this.deck.size();
		else
			return -1;
	}
	
	public Card getCard(int i) {
		if (i >= 0 && i < this.deck.size()) {
			return deck.get(i);
		} else {
			return null;
		}
	}
	
	public void removeCard(int i) {
		if (i >= 0 && i < this.deck.size()) {
			deck.remove(i);
		} 
	}
	
	@Override
	public String toString() {
		String strDeck = "";
		for (Card card : deck) {
			strDeck += card.toString()+" ";
		}
		return strDeck;
	}

	public static void main(String[] args) {
		Deck deck = new Deck(2);
		
		System.out.println("Deck : "+deck.toString());
		System.out.println("# Cards : "+deck.getDeckSize());
	}

}
