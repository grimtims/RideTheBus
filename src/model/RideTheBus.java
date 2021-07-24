package model;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import model.RTBConstants.*;

public class RideTheBus {
	
	private Deck deck = null;
	private ArrayList<Card> cards_played = null;
//	private int currentStop = 0;
	private Random rand = null;
	
//	public final static int RED_OR_BLACK = 0;
//	public final static int HIGHER_LOWER = 1;
//	public final static int IN_OUT_POST  = 2;
//	public final static int GUESS_SUIT   = 3;
//	public final static int WIN		   = 4;
//	public final static int EMPTY_DECK   = 5;
	
//	public final static STOP_STATUS PASS = true;
//	public final static boolean FAIL = false;
	
	
	
	private String prompt = "";
	private String strResultText = "";
	private Scanner sc = null;
	
	private STOP currStop = STOP.RED_OR_BLACK;
	
	
	private Card first_card = null;
	private Card second_card = null;
	private Card third_card = null;
	private Card last_card = null;
	
	private int num_decks = 1;
	
	public RideTheBus() {
		this.deck = new Deck(1);
		this.cards_played = new ArrayList<Card>();
		this.rand = new Random();
		this.setStop(STOP.RED_OR_BLACK);
		System.out.println("Welcome to Ride the Bus!");
	}
	
	public RideTheBus(int num_decks) {
		this.num_decks = num_decks;
		this.deck = new Deck(num_decks);
		this.cards_played = new ArrayList<Card>();
		this.rand = new Random();
		this.setStop(STOP.RED_OR_BLACK);
		System.out.println("Welcome to Ride The Bus! ("+this.num_decks+" Deck(s))");
	}
	
	public String getResultText() {
		return this.strResultText;
	}
	
	private void setResultText(String res) {
		this.strResultText = res;
	}
	
	private void setResultText(GUESS_STATUS status) {
		
	}
	
	public STOP getStop() {
		return this.currStop;
	}
	
	public void setStop(STOP stop) {
		this.currStop = stop;
	}
	
	public String getPrompt() {
		
		if (this.deck.getDeckSize() <= 0) {
			this.setStop(STOP.EMPTY_DECK);
		}
		
		switch (this.getStop()) {
		
			case RED_OR_BLACK: 	return "Red or Black?";
			case HIGHER_LOWER: 	return "Higher or lower.... or post?";
			case IN_OUT_POST : 	return "Inside or outside.... or post?";
			case GUESS_SUIT: 	return "Suit?";
			case WIN: 			return "Win! you went through "+((this.num_decks*52)-this.deck.getDeckSize())+" cards!";
			case EMPTY_DECK: 	return "No more cards! You Lose! You went through all "+((this.num_decks*52)-this.deck.getDeckSize())+" cards!";
			default:			return "ERROR";
		}
	}
	
	public void setPromptBasedOnStop(STOP stop) {
		this.setStop(stop);
	}
	
	public Card getFirstCard() {
		return this.first_card;
	}
	
	public void setFirstCard(Card card) {
		this.first_card = card;
	}
	
	public Card getSecondCard() {
		return this.second_card;
	}
	
	public void setSecondCard(Card card) {
		this.second_card = card;
	}
	
	public Card getThirdCard() {
		return this.third_card;
	}
	
	public void setThirdCard(Card card) {
		this.third_card = card;
	}
	
	public Card getLastCard() {
		return this.last_card;
	}
	
	public void setLastCard(Card card) {
		this.last_card = card;
	}
	
	
	public void setNumDecks(int numDecks) {
		this.num_decks = numDecks;
	}
	
	public int getNumDecks() {
		return this.num_decks;
	}
	
	
	public Card drawCard() {
		this.rand = new Random();
		int n = this.rand.nextInt(this.deck.getDeckSize());
		Card card = deck.getCard(n);
		deck.removeCard(n);
		return card;		
	}
	
	
	public double[] getPercentages() {
		
		int red   	= 0;
		int black 	= 0;
		int lower 	= 0;
		int higher 	= 0;
		int post2 	= 0;
		int inside 	= 0;
		int outside = 0;
		int post3 	= 0;
		int spade 	= 0;
		int club 	= 0;
		int heart 	= 0;
		int diamond = 0;
		
		double total = (double)this.deck.getDeckSize();
		
		
		for (int i = 0; i < deck.getDeckSize(); i++) {
			
			Card card = this.deck.getCard(i);
			
			// RED BLACK
			if (card.getCardColor() == Card.RED)
				red += 1;
			else
				black += 1;
			
			if (this.first_card != null) {
				// LOWER HIGHER
				if 		(card.getCardValue() < this.first_card.getCardValue())
					lower += 1;
				else if (card.getCardValue() > this.first_card.getCardValue())
					higher += 1;
				else if (card.getCardValue() == this.first_card.getCardValue())
					post2 += 2;
			}
			
			if (this.first_card != null && this.second_card != null) {
				// INSIDE OUTSIDE POST
				int high_card = ( this.first_card.getCardValue() >= this.second_card.getCardValue() ? this.first_card.getCardValue() : this.second_card.getCardValue() );
				int low_card = ( this.first_card.getCardValue() < this.second_card.getCardValue() ? this.first_card.getCardValue() : this.second_card.getCardValue() );
				
				if 		(card.getCardValue() > low_card && card.getCardValue() < high_card )
					inside += 1;
				else if (card.getCardValue() < low_card || card.getCardValue() > high_card)
					outside += 1;
				else if (card.getCardValue() == low_card || card.getCardValue() == high_card)
					post3 += 1;
			}
			
			// SUITE
			if 		(card.getCardSuitNum() == Card.SPADE)
				spade += 1;
			else if (card.getCardSuitNum() == Card.CLUB)
				club += 1;
			else if (card.getCardSuitNum() == Card.HEART)
				heart += 1;
			else if (card.getCardSuitNum() == Card.DIAMOND)
				diamond += 1;
			
			
		}
		
		double[] percentages = {(red/total),
								(black/total),
								(higher/total),
								(lower/total),
								(post2/total),
								(inside/total),
								(outside/total),
								(post3/total),
								(spade/total),
								(club/total),
								(heart/total),
								(diamond/total)};
		
		return percentages;
		
	}
	
	public double suitPercentage(int suit) {
		if ( suit >= 0 && suit <= 3) {
			int count = 0;
			for (int i = 0; i < deck.getDeckSize(); i++) {
				if (this.deck.getCard(i).getCardSuitNum() == suit)
					count++;
			}
			return ((double)count/deck.getDeckSize());
		} else {
			return -1.0;
		}
	}
	
	public GUESS_STATUS checkGuess(GUESS_OPTIONS guess) {
		
		if (this.deck.getDeckSize() <= 0) {
			this.setStop(STOP.EMPTY_DECK);
		}
		
		switch (this.getStop()) {
		
			case RED_OR_BLACK: {
				
				this.first_card = this.drawCard();
				
				System.out.println("Drew a "+this.first_card.getCardValueType()+" of "+this.first_card.getCardSuitType());
				
				if (	guess == GUESS_OPTIONS.RED    && this.first_card.getCardColor() == Card.RED   ||
						guess == GUESS_OPTIONS.BLACK  && this.first_card.getCardColor() == Card.BLACK )
				{
					System.out.println("Nice!");
					setResultText(RTBConstants.WON_AT_STOP);
					setStop(STOP.HIGHER_LOWER);
					return GUESS_STATUS.PASS;
				} else {
					System.out.println("Lolololol drink!");
					setResultText(RTBConstants.LOST_AT_STOP);
					setStop(STOP.RED_OR_BLACK);
					return GUESS_STATUS.FAIL;
				}
			}
			case HIGHER_LOWER: {
				
				this.second_card = this.drawCard();
				
				System.out.println("Drew a "+this.second_card.getCardValueType()+" of "+this.second_card.getCardSuitType());
				
				if (	guess == GUESS_OPTIONS.HIGHER && this.second_card.getCardValue() > this.first_card.getCardValue() ||
						guess == GUESS_OPTIONS.LOWER && this.second_card.getCardValue() < this.first_card.getCardValue() ||
						guess == GUESS_OPTIONS.POST && this.second_card.getCardValue() == this.first_card.getCardValue()) {
					System.out.println("Nice!");
					setResultText(RTBConstants.WON_AT_STOP);
					setStop(STOP.IN_OUT_POST);
					return GUESS_STATUS.PASS;
				} else {
					System.out.println("Lolololol drink!");
					setResultText(RTBConstants.LOST_AT_STOP);
					setStop(STOP.RED_OR_BLACK);
					return GUESS_STATUS.FAIL;
				}
			}
			case IN_OUT_POST: {
				
				this.third_card = this.drawCard();
				
				Card high_card = ( this.first_card.getCardValue() >= this.second_card.getCardValue() ? this.first_card : this.second_card );
				Card low_card = ( this.first_card.getCardValue() < this.second_card.getCardValue() ? this.first_card : this.second_card );
				
				System.out.println("Drew a "+this.third_card.getCardValueType()+" of "+this.third_card.getCardSuitType());
				
				if (	(guess == GUESS_OPTIONS.INSIDE && high_card.getCardValue() > this.third_card.getCardValue()   && low_card.getCardValue() < this.third_card.getCardValue()) ||
						(guess == GUESS_OPTIONS.OUTSIDE && (high_card.getCardValue() < this.third_card.getCardValue()  || low_card.getCardValue() > this.third_card.getCardValue())) ||
						(guess == GUESS_OPTIONS.POST && (this.third_card.getCardValue() == high_card.getCardValue() || this.third_card.getCardValue() == low_card.getCardValue()))) {
					System.out.println("Nice!");
					setResultText(RTBConstants.WON_AT_STOP);
					setStop(STOP.GUESS_SUIT);
					return GUESS_STATUS.PASS;
				} else {
					System.out.println("Lolololol drink!");
					setResultText(RTBConstants.LOST_AT_STOP);
					setStop(STOP.RED_OR_BLACK);
					return GUESS_STATUS.FAIL;
				}
				
			}
			case GUESS_SUIT: {
				
				this.last_card = this.drawCard();
				
				System.out.println("Drew a "+this.last_card.getCardValueType()+" of "+this.last_card.getCardSuitType());
				
				if (	guess == GUESS_OPTIONS.SPADE   && this.last_card.getCardSuitNum() == Card.SPADE   ||
						guess == GUESS_OPTIONS.CLUB    && this.last_card.getCardSuitNum() == Card.CLUB    ||
						guess == GUESS_OPTIONS.HEART   && this.last_card.getCardSuitNum() == Card.HEART   ||
						guess == GUESS_OPTIONS.DIAMOND && this.last_card.getCardSuitNum() == Card.DIAMOND ) {
					System.out.println("Nice!");
					setResultText(RTBConstants.WON_AT_STOP);
					setStop(STOP.WIN);
					return GUESS_STATUS.PASS;
				} else {
					System.out.println("Lolololol drink!");
					setResultText(RTBConstants.LOST_AT_STOP);
					setStop(STOP.RED_OR_BLACK);
					return GUESS_STATUS.FAIL;
				}
				
			}
			case WIN: {
				System.out.println("WIN! you went through "+(this.num_decks-this.deck.getDeckSize())+" cards!");
				setResultText(RTBConstants.WON_GAME);
				return GUESS_STATUS.PASS;
			}
			case EMPTY_DECK: {
				System.out.println("No more cards!");
				setResultText(RTBConstants.LOST_EMPTY_DECK);
				return GUESS_STATUS.FAIL;
			}
			default: {
				System.err.println("ERROR");
				return GUESS_STATUS.FAIL;
			}
		
		}
		
	}

	
	/*
	public void nextTurnCLI() {
		
		sc = new Scanner(System.in); 
		
		if (this.deck.getDeckSize() <= 0) {
			this.setStop(EMPTY_DECK);
		}
		
		switch (this.getStop()) {
		
			case RED_OR_BLACK: {
				
				System.out.println("Red("+Card.RED+") or Black("+Card.BLACK+")?");
				int pick = sc.nextInt();
				
				this.first_card = this.drawCard();
				
				System.out.println("Drew a "+this.first_card.getCardValueType()+" of "+this.first_card.getCardSuitType());
				
				if (pick == this.first_card.getCardColor()) {
					System.out.println("Nice!");
					setStop(HIGHER_LOWER);
				} else {
					System.out.println("Lolololol drink!");
					setStop(RED_OR_BLACK);
				}
				break;
				
			}
			case HIGHER_LOWER: {
				
				System.out.println("Higher(0) or lower(1).... or post(2)?");
				
				int pick = sc.nextInt();
				
				this.second_card = this.drawCard();
				
				System.out.println("Drew a "+this.second_card.getCardValueType()+" of "+this.second_card.getCardSuitType());
				
				if (	pick == 0 && this.second_card.getCardValue() > this.first_card.getCardValue() ||
						pick == 1 && this.second_card.getCardValue() < this.first_card.getCardValue() ||
						pick == 2 && this.second_card.getCardValue() == this.first_card.getCardValue()) {
					System.out.println("Nice!");
					setStop(IN_OUT_POST);
				} else {
					System.out.println("Lolololol drink!");
					setStop(RED_OR_BLACK);
				}
				break;
				
			}
			case IN_OUT_POST : {
				
				System.out.println("Inside(0) or outside(1).... or post(2)?");
				
				int pick = sc.nextInt();
				
				this.third_card = this.drawCard();
				
				Card high_card = ( this.first_card.getCardValue() >= this.second_card.getCardValue() ? this.first_card : this.second_card );
				Card low_card = ( this.first_card.getCardValue() < this.second_card.getCardValue() ? this.first_card : this.second_card );
				
				System.out.println("Drew a "+this.third_card.getCardValueType()+" of "+this.third_card.getCardSuitType());
				
				if (	(pick == 0 && high_card.getCardValue() > this.third_card.getCardValue()   && low_card.getCardValue() < this.third_card.getCardValue()) ||
						(pick == 1 && (high_card.getCardValue() < this.third_card.getCardValue()  || low_card.getCardValue() > this.third_card.getCardValue())) ||
						(pick == 2 && (this.third_card.getCardValue() == high_card.getCardValue() || this.third_card.getCardValue() == low_card.getCardValue()))) {
					System.out.println("Nice!");
					setStop(GUESS_SUIT);
				} else {
					System.out.println("Lolololol drink!");
					setStop(RED_OR_BLACK);
				}
				break;
			}
			case GUESS_SUIT: {
				
				System.out.println("Suit? SPADE(0)  CLUB(1)  HEART(2)  DIAMOND(3)");
				int pick = sc.nextInt();
				
				this.last_card = this.drawCard();
				
				System.out.println("Drew a "+this.last_card.getCardValueType()+" of "+this.last_card.getCardSuitType());
				
				if (pick == this.last_card.getCardSuitNum()) {
					System.out.println("Nice!");
					setStop(WIN);
				} else {
					System.out.println("Lolololol drink!");
					setStop(RED_OR_BLACK);
				}
				break;
			}
			case WIN: {
				System.out.println("WIN! you went through "+this.deck.getDeckSize()+" cards!");
				break;
			}
			case EMPTY_DECK: {
				System.out.println("No more cards!");
				break;
			}
			default: {
				System.err.println("ERROR");
			}
		}
		
		
	}
	*/
	
	public void generateNewDeck(int num_decks) {
		this.deck.generateNewDeck(num_decks);
	}
	
	public void showDeck() {
		System.out.println("Deck("+this.deck.getDeckSize()+") : "+this.deck.toString());
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RideTheBus rtb = new RideTheBus();
		
		for (int i = 0; i < 50; i++) {
			rtb.drawCard();
		}
		
//		while (rtb.getStop() != rtb.EMPTY_DECK) {
//			rtb.showDeck();
//			rtb.nextTurnCLI();
//		}
	}

}
