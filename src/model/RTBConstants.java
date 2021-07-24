package model;

public interface RTBConstants {
	
	public final static String WON_AT_STOP   	= "Nice!";
	public final static String WON_GAME			= "You Win!";
	public final static String LOST_AT_STOP		= "lololol drink, start again!";
	public final static String LOST_EMPTY_DECK	= "No more cards! You Lose!";
	
	public final static String APP_TITLE = "Ride The Bus!";
	public final static String APP_ICON_FILEPATH = "/bus.png";
	
	public final static double CARD_SCALE = 0.2734375;
	
	public final static double HEADER_SCALE = 0.1;
	
	public final static String CARD_BACK_FILEPATH = "/card_back_729x1024.png";
	public final static String CARD_SET_FILEPATH = "/55_card_set_one_num.png";
	
	public final static String HONOR_HEART_FILEPATH = "/honor_heart-14.png";
	public final static String HONOR_SPADE_FILEPATH = "/honor_spade-14.png";
	
	public final static String GREEN_POKER_BACKGROUND_FILEPATH = "/poker_green_background.png";
	public final static String GREEN_POKER_BACKGROUND_DARK_FILEPATH = "/poker_green_background_dark.png";
	
	public static enum STOP {
		RED_OR_BLACK,
		HIGHER_LOWER,
		IN_OUT_POST,
		GUESS_SUIT,
		WIN,
		EMPTY_DECK,
	}

	public static enum GUESS_OPTIONS {
		RED,
		BLACK,
		LOWER,
		POST,
		HIGHER,
		INSIDE,
		OUTSIDE,
		SPADE,
		CLUB,
		HEART,
		DIAMOND
	}
	
	public static enum GUESS_STATUS {
		PASS,
		FAIL
	}
	
	
	public static final String[] WINNING_RESULT_TEXTS = 
			{ 		"Nice!",
					"Sweaty!",
					"Cheeky!",
					"Wow!",
					"Nice one!",
					"Sheeeesh!",
					"Good Job!"
			};
	
}
