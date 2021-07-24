package model;

public class Card {
	
	public static final int RED 	= 0;
	public static final int BLACK   = 1;
	
	public static final int SPADE 	= 0;
	public static final int CLUB  	= 1;
	public static final int HEART	= 2;
	public static final int DIAMOND	= 3;
	
	public static final String[] face_cards = {"2","3","4","5","6","7","8","9","10","J","Q","K","A"};
	
	private int card_suit;
	private int card_value;
	
	
	public Card(int suit, int value) {
		setCardSuit(suit);
		setCardValue(value);
	}
	
	public void setCardSuit(int suit) {
		if (suit >= 0 && suit <= 3)
			this.card_suit = suit;
		else
			System.err.println("ERROR: suit out of range.");
	}
	
	public int getCardSuitNum() {
		return this.card_suit;
	}
	
	public String getCardSuitType() {
		switch (this.card_suit) {
			case 0: return "S";
			case 1: return "C"; 
			case 2: return "H"; 
			case 3: return "D";
			default: return "ERR";
		}
	}
	
	public int getCardColor() {
		if (this.card_suit == SPADE || this.card_suit == CLUB) {
			return BLACK;
		} else if (this.card_suit == HEART || this.card_suit == DIAMOND) {
			return RED;
		} else {
			System.err.println("ERROR: card suit not valid");
			return -1;
		}
	}
	
	public void setCardValue(int value) {
		if (value >= 2 && value <= 14)
			this.card_value = value;
		else
			System.err.println("ERROR: value out of range.");
	}
	
	public int getCardValue() {
		return this.card_value;
	}
	
	public String getCardValueType() {
		return ""+face_cards[this.card_value-2];
	}
	
	@Override
	public String toString() {
		return ""+face_cards[this.card_value-2]+this.getCardSuitType();
	}

	public static void main(String[] args) {
		
		Card card = new Card(SPADE, 4);
		
		System.out.println(card.toString());

	}

}
