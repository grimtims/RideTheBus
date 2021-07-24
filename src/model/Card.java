package model;

/**
 * Creates a card based on value and suit
 * 
 * @author Graham
 * @version 1.0
 * @since 2021-07-24
 */

public class Card {
	
	public static final int RED 	= 0;
	public static final int BLACK   = 1;
	
	public static final int SPADE 	= 0;
	public static final int CLUB  	= 1;
	public static final int HEART	= 2;
	public static final int DIAMOND	= 3;
	
	/**
	 * Actual card representation based on card index
	 */
	public static final String[] face_cards = {"2","3","4","5","6","7","8","9","10","J","Q","K","A"};
	
	/**
	 * Internal indexing for suit and value
	 */
	private int card_suit;
	private int card_value;
	
	/**
	 * Card constructor using internal indexing
	 * for suit and value
	 * 
	 * @param suit
	 * @param value
	 */
	public Card(int suit, int value) {
		setCardSuit(suit);
		setCardValue(value);
	}
	
	/**
	 * Set the suit in terms of it's suit numeric value
	 * 
	 * @param suit
	 */
	public void setCardSuit(int suit) {
		if (suit >= 0 && suit <= 3)
			this.card_suit = suit;
		else
			System.err.println("ERROR: suit out of range.");
	}
	
	/**
	 * Returns the suit's numeric value
	 * 
	 * @return card suit as it's numeric value
	 */
	public int getCardSuitNum() {
		return this.card_suit;
	}
	
	/**
	 * Returns the suit's letter representation
	 * 
	 * @return first letter of suit
	 */
	public String getCardSuitType() {
		switch (this.card_suit) {
			case 0: return "S";
			case 1: return "C"; 
			case 2: return "H"; 
			case 3: return "D";
			default: return "ERR";
		}
	}
	
	/**
	 * Returns the color of the suit.
	 * 
	 * @return red or black
	 */
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
	
	/**
	 * Set the card index value
	 * 
	 * @param index value
	 */
	public void setCardValue(int value) {
		if (value >= 2 && value <= 14)
			this.card_value = value;
		else
			System.err.println("ERROR: value out of range.");
	}
	
	/**
	 * Returns the card index value
	 * 
	 * @return card index value
	 */
	public int getCardValue() {
		return this.card_value;
	}
	
	/**
	 * Returns the card face value based
	 * on the card index value
	 * 
	 * @return "face value" of card
	 */
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
