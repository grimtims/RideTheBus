package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import model.*;
import model.RTBConstants.*;

import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.awt.FlowLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.border.BevelBorder;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;


public class GameWindow {

	private JFrame frame;
	
	private Deck deck;
	private JPanel panelButtons;
	private JButton btnStartGame;
	private JLabel lblNewLabel;
	private JPanel panelHeader;
	private JPanel panelPrompt;
	private JLabel lblPrompt;
	private JPanel panelCards;
	private JMenuBar menuBar;
	private JMenu mnHelpMenu;
	private JMenuItem mntmAbout;
	private JPanel panel_card_1;
	private JPanel panel_card_2;
	private JPanel panel_card_3;
	private JPanel panel_card_4;
	
	private JButton btnRed;
	private JButton btnBlack;
	private JButton btnHigher;
	private JButton btnLower;
	private JButton btnInside;
	private JButton btnOutside;
	private JButton btnPost;
	
	private JButton btnSpade;
	private JButton btnClub;
	private JButton btnHeart;
	private JButton btnDiamond;
	
	private JButton btnPlayAgain;
	
	private RideTheBus rtb;
	private JPanel panelResult;
	private JLabel lblResult;
	
	private JLabel lblBackground;
	
	private ArrayList<JButton> listFirstButtons = new ArrayList<JButton>();
	private ArrayList<JButton> listSecondButtons = new ArrayList<JButton>();
	private ArrayList<JButton> listThirdButtons = new ArrayList<JButton>();
	private ArrayList<JButton> listLastButtons = new ArrayList<JButton>();
	private JLabel label_card_two;
	private JLabel label_card_one;
	private JLabel label_card_three;
	private JLabel label_card_four;
	private Component horizontalStrut;
	private Component horizontalStrut_1;
	private Component horizontalStrut_2;
	private Component horizontalStrut_3;
	private Component horizontalStrut_4;
	
	private BufferedImage biCardBack = null;
	private BufferedImage biCardSet = null;
	private BufferedImage biLeftHeader = null;
	private BufferedImage biRightHeader = null;
	
	private JLabel label_left_header_image;
	private JLabel label_right_header_image;
	
	private JLabel lblCardOneText;
	private JLabel lblCardTwoText;
	private JLabel lblCardThreeText;
	private JLabel lblCardFourText;
	
	private JMenu mnConfigMenu;
	private JMenu mnNumOfDecks;
	private JRadioButtonMenuItem rdbtnShowOdds;
	private JMenuItem mntmNumDecksOne;
	private JMenuItem mntmNumDecksTwo;
	private JMenuItem mntmNumDecksThree;
	private JMenuItem mntmNumDecksFour;
	
	private int num_of_decks = 1;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameWindow window = new GameWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GameWindow() {
		initializeComponents();
		addActionListeners();
		initImages();
		GameLoop(GUESS_OPTIONS.BLACK);
	}
	
	public void initGame() {
		this.rtb = new RideTheBus(num_of_decks);
	}
	
	public void initImages() {
		
		// header images
		try {
			biLeftHeader = ImageIO.read(this.getClass().getResource(RTBConstants.HONOR_HEART_FILEPATH));
			biRightHeader = ImageIO.read(this.getClass().getResource(RTBConstants.HONOR_SPADE_FILEPATH));
			
			this.label_left_header_image.setIcon(new ImageIcon(cropAndScaleImage(biLeftHeader, null, RTBConstants.HEADER_SCALE, RTBConstants.HEADER_SCALE)));
			this.label_right_header_image.setIcon(new ImageIcon(cropAndScaleImage(biRightHeader, null, RTBConstants.HEADER_SCALE, RTBConstants.HEADER_SCALE)));
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		// card back
		try {
			biCardBack = ImageIO.read(this.getClass().getResource(RTBConstants.CARD_BACK_FILEPATH));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// card set
		biCardSet = null;
		try {
			biCardSet = ImageIO.read(this.getClass().getResource(RTBConstants.CARD_SET_FILEPATH));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		resetCardImages();
	}
	
	private BufferedImage cropAndScaleImage(BufferedImage bimage, Rectangle rect, double scalex, double scaley) {
		
		// Create a buffered image with transparency
//	    BufferedImage bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
	    
	    // Paint image on buffered image
//	    Graphics g = bimage.getGraphics();
//	    g.drawImage(image, 0, 0, null);
//	    g.dispose();
		
		// crop image
	    BufferedImage crop_bimage;
		
		if (rect == null) {
			crop_bimage = bimage;
		} else {
			// crop image
		    crop_bimage = bimage.getSubimage(rect.x, rect.y, rect.width, rect.height);
		}
	    
	    int w = crop_bimage.getWidth();
	    int h = crop_bimage.getHeight();
	    // Create a new image of the proper size
	    int w2 = (int) (w * scalex);
	    int h2 = (int) (h * scaley);
	    
	    // scale image
	    BufferedImage scaled_cropped_bimage = new BufferedImage(w2, h2, BufferedImage.TYPE_INT_ARGB);
	    AffineTransform scaleInstance = AffineTransform.getScaleInstance(scalex, scaley);
	    AffineTransformOp scaleOp = new AffineTransformOp(scaleInstance, AffineTransformOp.TYPE_BILINEAR);

	    scaleOp.filter(crop_bimage, scaled_cropped_bimage);

	   // Draw the image on to the buffered image
//	    Graphics2D bGr = dest.createGraphics();
//	    bGr.drawImage(image, 0, 0, null);
//	    bGr.dispose();
	    
		return scaled_cropped_bimage; 
	}
	
	boolean isCardSheet = false;
	private BufferedImage getCardImage(Card card) {
		
		if (isCardSheet) {
		
			int width = 200;
			int height = 280;
			
			int x_offset = (card.getCardValue()-2) * width;
			int y_offset = card.getCardSuitNum() * height;
			
			return cropAndScaleImage(biCardSet, new Rectangle(x_offset,y_offset,width,height), 1.0, 1.0);
		} else {
			
			BufferedImage biCard = null;
			try {
				
				String val = card.getCardValueType();
				String suit = card.getCardSuitType();
				String path = "/CARD_SET/"+val+suit+".png";
				
				biCard = ImageIO.read(this.getClass().getResource(path));
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			
			return cropAndScaleImage(biCard, null, 0.29, 0.265);
		}
	}
		

	/**
	 * Initialize the contents of the frame.
	 */
	private void initializeComponents() {
		
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(46, 139, 87));
		
		// Set Background Image
		try {
			lblBackground = new JLabel(new ImageIcon(ImageIO.read(this.getClass().getResource(RTBConstants.GREEN_POKER_BACKGROUND_DARK_FILEPATH))));
			frame.setContentPane(lblBackground);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		frame.setBackground(Color.LIGHT_GRAY);
		frame.setResizable(false);
		frame.setTitle(RTBConstants.APP_TITLE);
		
		Image imgAppIcon = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource(RTBConstants.APP_ICON_FILEPATH));
		frame.setIconImage(imgAppIcon);
		
		frame.setBounds(100, 100, 1240, 821);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[grow]", "[][grow][][][]"));
		
		panelHeader = new JPanel();
		panelHeader.setBackground(Color.WHITE);
		panelHeader.setOpaque(false);
		FlowLayout flowLayout = (FlowLayout) panelHeader.getLayout();
		flowLayout.setVgap(20);
		flowLayout.setHgap(20);
		frame.getContentPane().add(panelHeader, "flowx,cell 0 0,alignx center");
		
		label_left_header_image = new JLabel("");
		panelHeader.add(label_left_header_image);
		
		lblNewLabel = new JLabel("Ride The Bus!");
		lblNewLabel.setBackground(new Color(102, 204, 153));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		panelHeader.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Franklin Gothic Demi Cond", Font.BOLD, 40));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		label_right_header_image = new JLabel("");
		panelHeader.add(label_right_header_image);
		
		panelCards = new JPanel();
		panelCards.setBackground(new Color(46, 139, 87));
		panelCards.setOpaque(false);
		frame.getContentPane().add(panelCards, "cell 0 1,grow");
		panelCards.setLayout(new MigLayout("", "[][grow][][grow][][grow][][grow][]", "[][grow][]"));
		
		horizontalStrut_3 = Box.createHorizontalStrut(20);
		panelCards.add(horizontalStrut_3, "cell 0 1");
		
		panel_card_1 = new JPanel();
		panel_card_1.setOpaque(false);
		panel_card_1.setBackground(new Color(46, 139, 87));
		FlowLayout flowLayout_1 = (FlowLayout) panel_card_1.getLayout();
		flowLayout_1.setVgap(10);
		flowLayout_1.setHgap(10);
		panelCards.add(panel_card_1, "cell 1 1,alignx center,aligny center");
		
		label_card_one = new JLabel("");
		panel_card_1.add(label_card_one);
		
		horizontalStrut = Box.createHorizontalStrut(20);
		panelCards.add(horizontalStrut, "cell 2 1");
		
		panel_card_2 = new JPanel();
		panel_card_2.setOpaque(false);
		panel_card_2.setBackground(new Color(46, 139, 87));
		FlowLayout flowLayout_2 = (FlowLayout) panel_card_2.getLayout();
		flowLayout_2.setVgap(10);
		flowLayout_2.setHgap(10);
		panelCards.add(panel_card_2, "cell 3 1,alignx center,aligny center");
		
		label_card_two = new JLabel("");
		panel_card_2.add(label_card_two);
		
		horizontalStrut_1 = Box.createHorizontalStrut(20);
		panelCards.add(horizontalStrut_1, "cell 4 1");
		
		panel_card_3 = new JPanel();
		panel_card_3.setOpaque(false);
		panel_card_3.setBackground(new Color(46, 139, 87));
		FlowLayout flowLayout_3 = (FlowLayout) panel_card_3.getLayout();
		flowLayout_3.setVgap(10);
		flowLayout_3.setHgap(10);
		panelCards.add(panel_card_3, "cell 5 1,alignx center,aligny center");
		
		label_card_three = new JLabel("");
		panel_card_3.add(label_card_three);
		
		horizontalStrut_2 = Box.createHorizontalStrut(20);
		panelCards.add(horizontalStrut_2, "cell 6 1");
		
		panel_card_4 = new JPanel();
		panel_card_4.setOpaque(false);
		panel_card_4.setBackground(new Color(46, 139, 87));
		FlowLayout flowLayout_4 = (FlowLayout) panel_card_4.getLayout();
		flowLayout_4.setVgap(10);
		flowLayout_4.setHgap(10);
		panelCards.add(panel_card_4, "cell 7 1,alignx center,aligny center");
		
		label_card_four = new JLabel("");
		panel_card_4.add(label_card_four);
		
		horizontalStrut_4 = Box.createHorizontalStrut(20);
		panelCards.add(horizontalStrut_4, "cell 8 1");
		
		lblCardOneText = new JLabel("");
		lblCardOneText.setForeground(new Color(255, 255, 255));
		panelCards.add(lblCardOneText, "cell 1 2,alignx center,aligny center");
		lblCardOneText.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		lblCardTwoText = new JLabel("");
		lblCardTwoText.setForeground(new Color(255, 255, 255));
		panelCards.add(lblCardTwoText, "cell 3 2,alignx center,aligny center");
		lblCardTwoText.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		lblCardThreeText = new JLabel("");
		lblCardThreeText.setForeground(new Color(255, 255, 255));
		panelCards.add(lblCardThreeText, "cell 5 2,alignx center,aligny center");
		lblCardThreeText.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		lblCardFourText = new JLabel("");
		lblCardFourText.setForeground(new Color(255, 255, 255));
		panelCards.add(lblCardFourText, "cell 7 2,alignx center,aligny center");
		lblCardFourText.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		panelResult = new JPanel();
		panelResult.setOpaque(false);
		panelResult.setBackground(new Color(46, 139, 87));
		FlowLayout fl_panelResult = (FlowLayout) panelResult.getLayout();
		fl_panelResult.setVgap(20);
		fl_panelResult.setHgap(20);
		frame.getContentPane().add(panelResult, "cell 0 2,grow");
		
		lblResult = new JLabel("");
		lblResult.setVisible(false);
		lblResult.setBackground(new Color(46, 139, 87));
		lblResult.setForeground(new Color(255, 255, 255));
		lblResult.setFont(new Font("Tahoma", Font.BOLD, 15));
		panelResult.add(lblResult);
		
		panelPrompt = new JPanel();
		panelPrompt.setOpaque(false);
		panelPrompt.setBackground(new Color(46, 139, 87));
		FlowLayout fl_panelPrompt = (FlowLayout) panelPrompt.getLayout();
		fl_panelPrompt.setVgap(20);
		fl_panelPrompt.setHgap(20);
		frame.getContentPane().add(panelPrompt, "cell 0 3,growx");
		
		lblPrompt = new JLabel("Welcome!");
		lblPrompt.setBackground(new Color(46, 139, 87));
		lblPrompt.setForeground(new Color(255, 255, 255));
		lblPrompt.setFont(new Font("Tahoma", Font.BOLD, 15));
		panelPrompt.add(lblPrompt);
		
		panelButtons = new JPanel();
		panelButtons.setOpaque(false);
		panelButtons.setBorder(null);
		panelButtons.setBackground(new Color(0, 0, 0));
		FlowLayout fl_panelButtons = (FlowLayout) panelButtons.getLayout();
		fl_panelButtons.setVgap(20);
		fl_panelButtons.setHgap(20);
		frame.getContentPane().add(panelButtons, "cell 0 4,grow");
		
		btnStartGame = new JButton("Start Game!");
		btnStartGame.setFocusPainted(false);
		btnStartGame.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panelButtons.add(btnStartGame);
		
		btnRed = new JButton("Red");
		btnRed.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		btnBlack = new JButton("Black");
		btnBlack.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		btnHigher = new JButton("Higher");
		btnHigher.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		btnLower = new JButton("Lower");
		btnLower.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		btnInside = new JButton("Inside");
		btnInside.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		btnOutside = new JButton("Outside");
		btnOutside.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		btnPost = new JButton("Post");
		btnPost.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		btnSpade = new JButton("Spade");
		btnSpade.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		btnClub = new JButton("Club");
		btnClub.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		btnHeart = new JButton("Heart");
		btnHeart.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		btnDiamond = new JButton("Diamond");
		btnDiamond.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		btnPlayAgain = new JButton("Play Again");
		btnPlayAgain.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		listFirstButtons.add(btnRed);
		listFirstButtons.add(btnBlack);
		
		listSecondButtons.add(btnHigher);
		listSecondButtons.add(btnLower);
		listSecondButtons.add(btnPost);
		
		listThirdButtons.add(btnInside);
		listThirdButtons.add(btnOutside);
		listThirdButtons.add(btnPost);
		
		listLastButtons.add(btnSpade);
		listLastButtons.add(btnClub);
		listLastButtons.add(btnHeart);
		listLastButtons.add(btnDiamond);
		
		
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		mnConfigMenu = new JMenu("Config");
		menuBar.add(mnConfigMenu);
		
		mnNumOfDecks = new JMenu("# of Decks");
		mnConfigMenu.add(mnNumOfDecks);
		
		mntmNumDecksOne = new JMenuItem("1");
		mntmNumDecksOne.setHorizontalAlignment(SwingConstants.CENTER);
		mnNumOfDecks.add(mntmNumDecksOne);
		
		mntmNumDecksTwo = new JMenuItem("2");
		mnNumOfDecks.add(mntmNumDecksTwo);
		
		mntmNumDecksThree = new JMenuItem("3");
		mnNumOfDecks.add(mntmNumDecksThree);
		
		mntmNumDecksFour = new JMenuItem("4");
		mnNumOfDecks.add(mntmNumDecksFour);
		
		rdbtnShowOdds = new JRadioButtonMenuItem("Show Odds");
		mnConfigMenu.add(rdbtnShowOdds);
		
		mnHelpMenu = new JMenu("Help");
		menuBar.add(mnHelpMenu);
		
		mntmAbout = new JMenuItem("About");
		mnHelpMenu.add(mntmAbout);
		
		
	}
	
	private void addActionListeners() {
		
		
		btnStartGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				initGame();
				
				lblPrompt.setText(rtb.getPrompt());
				
				resetCardImages();
				
				resetCardText();
				
				removeAllButtonsFromPanel();
				
				addButtonsToPanel(listFirstButtons);
				
				updatePercentages();
				
			}
		});
		
		btnPlayAgain.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				initGame();
				
				lblPrompt.setText(rtb.getPrompt());
				
				resetCardImages();
				
				resetCardText();
				
				removeAllButtonsFromPanel();
				
				addButtonsToPanel(listFirstButtons);
				
				updatePercentages();
				
			}
			
		});
		
		btnRed.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (rtb.getStop() != STOP.RED_OR_BLACK) {
					System.out.println("ERROR");
					return;
				}
				
				resetCardImages();
				
				resetCardText();
				
				if ( rtb.checkGuess(GUESS_OPTIONS.RED) == GUESS_STATUS.PASS ) {					
					removeAllButtonsFromPanel();
					addButtonsToPanel(listSecondButtons);				
				} else {
					lostRoundReset();
				}
				
				label_card_one.setIcon(new ImageIcon(getCardImage(rtb.getFirstCard())));
				lblPrompt.setText(rtb.getPrompt());
				lblCardOneText.setText(rtb.getResultText());
				
				updatePercentages();
				
				checkResetEmptyDeck();
				
			}
		});
		
		btnBlack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (rtb.getStop() != STOP.RED_OR_BLACK) {
					System.out.println("ERROR");
					return;
				}
				
				resetCardImages();
				
				resetCardText();
				
				if ( rtb.checkGuess(GUESS_OPTIONS.BLACK) == GUESS_STATUS.PASS ) {
					removeAllButtonsFromPanel();
					addButtonsToPanel(listSecondButtons);
					lblCardOneText.setText("");
					
				} else {
					lostRoundReset();
				}
				
				label_card_one.setIcon(new ImageIcon(getCardImage(rtb.getFirstCard())));
				lblPrompt.setText(rtb.getPrompt());
				lblCardOneText.setText(rtb.getResultText());
				
				updatePercentages();
				
				checkResetEmptyDeck();
				
			}
		});
		
		btnHigher.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if (rtb.getStop() != STOP.HIGHER_LOWER) {
					System.out.println("ERROR");
					return;
				}
				
				if ( rtb.checkGuess(GUESS_OPTIONS.HIGHER) == GUESS_STATUS.PASS ) {					
					removeAllButtonsFromPanel();
					addButtonsToPanel(listThirdButtons);
					
				} else {
					lostRoundReset();
				}
				
				label_card_two.setIcon(new ImageIcon(getCardImage(rtb.getSecondCard())));
				lblPrompt.setText(rtb.getPrompt());
				lblCardTwoText.setText(rtb.getResultText());
				
				updatePercentages();
				
				checkResetEmptyDeck();
				
			}
			
		});
		
		btnLower.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (rtb.getStop() != STOP.HIGHER_LOWER) {
					System.out.println("ERROR");
					return;
				}
				
				if ( rtb.checkGuess(GUESS_OPTIONS.LOWER) == GUESS_STATUS.PASS ) {
					removeAllButtonsFromPanel();
					addButtonsToPanel(listThirdButtons);
					
				} else {
					lostRoundReset();
				}
				
				label_card_two.setIcon(new ImageIcon(getCardImage(rtb.getSecondCard())));
				lblPrompt.setText(rtb.getPrompt());
				lblCardTwoText.setText(rtb.getResultText());
				
				updatePercentages();
				
				checkResetEmptyDeck();
				
			}
			
		});
		
		btnPost.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (rtb.getStop() != STOP.HIGHER_LOWER && rtb.getStop() != STOP.IN_OUT_POST) {
					System.out.println("ERROR");
					return;
				}
				
				if ( rtb.checkGuess(GUESS_OPTIONS.POST) == GUESS_STATUS.PASS ) {
					removeAllButtonsFromPanel();
					addButtonsToPanel(listThirdButtons);
				} else {
					lostRoundReset();
				}
				
				if (rtb.getStop() == STOP.HIGHER_LOWER) {
					label_card_two.setIcon(new ImageIcon(getCardImage(rtb.getSecondCard())));
					lblCardTwoText.setText(rtb.getResultText());
				} else if (rtb.getStop() == STOP.IN_OUT_POST) {
					label_card_three.setIcon(new ImageIcon(getCardImage(rtb.getThirdCard())));
					lblCardThreeText.setText(rtb.getResultText());
				}
				lblPrompt.setText(rtb.getPrompt());
				
				updatePercentages();
				
				checkResetEmptyDeck();
				
			}
			
		});
		
		btnInside.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (rtb.getStop() != STOP.IN_OUT_POST) {
					System.out.println("ERROR");
					return;
				}
				
				if ( rtb.checkGuess(GUESS_OPTIONS.INSIDE) == GUESS_STATUS.PASS ) {
					removeAllButtonsFromPanel();
					addButtonsToPanel(listLastButtons);
				} else {
					lostRoundReset();
				}
				
				label_card_three.setIcon(new ImageIcon(getCardImage(rtb.getThirdCard())));
				lblPrompt.setText(rtb.getPrompt());
				lblCardThreeText.setText(rtb.getResultText());
				
				updatePercentages();
				
				checkResetEmptyDeck();
				
			}
			
		});
		
		btnOutside.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (rtb.getStop() != STOP.IN_OUT_POST) {
					System.out.println("ERROR");
					return;
				}
				
				if ( rtb.checkGuess(GUESS_OPTIONS.OUTSIDE) == GUESS_STATUS.PASS ) {
					removeAllButtonsFromPanel();
					addButtonsToPanel(listLastButtons);
				} else {
					lostRoundReset();
				}
				
				label_card_three.setIcon(new ImageIcon(getCardImage(rtb.getThirdCard())));
				lblPrompt.setText(rtb.getPrompt());
				lblCardThreeText.setText(rtb.getResultText());
				
				updatePercentages();
				
				checkResetEmptyDeck();
				
			}
			
		});
		
		btnSpade.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if (rtb.getStop() != STOP.GUESS_SUIT) {
					System.out.println("ERROR");
					return;
				}
				
				if ( rtb.checkGuess(GUESS_OPTIONS.SPADE) == GUESS_STATUS.PASS ) {
					removeAllButtonsFromPanel();
					addButtonToPanel(btnPlayAgain);
				} else {
					lostRoundReset();
				}
				
				label_card_four.setIcon(new ImageIcon(getCardImage(rtb.getLastCard())));
				lblPrompt.setText(rtb.getPrompt());
				lblCardFourText.setText(rtb.getResultText());
				
				updatePercentages();
				
				checkResetEmptyDeck();
				
			}
			
		});
		
		btnClub.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if (rtb.getStop() != STOP.GUESS_SUIT) {
					System.out.println("ERROR");
					return;
				}
				
				if ( rtb.checkGuess(GUESS_OPTIONS.CLUB) == GUESS_STATUS.PASS ) {
					removeAllButtonsFromPanel();
					addButtonToPanel(btnPlayAgain);
				} else {
					lostRoundReset();
				}
				
				label_card_four.setIcon(new ImageIcon(getCardImage(rtb.getLastCard())));
				lblPrompt.setText(rtb.getPrompt());
				lblCardFourText.setText(rtb.getResultText());
				
				updatePercentages();
				
				checkResetEmptyDeck();
				
			}
			
		});
		
		btnHeart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if (rtb.getStop() != STOP.GUESS_SUIT) {
					System.out.println("ERROR");
					return;
				}
				
				if ( rtb.checkGuess(GUESS_OPTIONS.HEART) == GUESS_STATUS.PASS ) {
					removeAllButtonsFromPanel();
					addButtonToPanel(btnPlayAgain);
				} else {
					lostRoundReset();
				}
				
				label_card_four.setIcon(new ImageIcon(getCardImage(rtb.getLastCard())));
				lblPrompt.setText(rtb.getPrompt());
				lblCardFourText.setText(rtb.getResultText());
				
				updatePercentages();
				
				checkResetEmptyDeck();
				
			}
			
		});
		
		btnDiamond.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if (rtb.getStop() != STOP.GUESS_SUIT) {
					System.out.println("ERROR");
					return;
				}
				
				if ( rtb.checkGuess(GUESS_OPTIONS.DIAMOND) == GUESS_STATUS.PASS ) {
					removeAllButtonsFromPanel();
					addButtonToPanel(btnPlayAgain);
				} else {
					lostRoundReset();
				}
				
				label_card_four.setIcon(new ImageIcon(getCardImage(rtb.getLastCard())));
				lblPrompt.setText(rtb.getPrompt());
				lblCardFourText.setText(rtb.getResultText());
				
				updatePercentages();
				
				checkResetEmptyDeck();
				
			}
			
		});
		
		
		/************* MENU ITEM LISTENER *****************/
		
		mntmNumDecksOne.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				num_of_decks = 1;
			}
			
		});
		
		mntmNumDecksTwo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				num_of_decks = 2;
			}
			
		});
		
		mntmNumDecksThree.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				num_of_decks = 3;
			}
			
		});
		
		mntmNumDecksFour.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				num_of_decks = 4;
			}
			
		});
		
		rdbtnShowOdds.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				checkShowPercentages();						
			}
		});
		
		mntmAbout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {				
				JOptionPane.showMessageDialog(frame,
					    RTBConstants.ABOUT_TEXT,
					    "About",
					    JOptionPane.NO_OPTION);
			}
		});	
		
		
		
	}
	
	private void updatePercentages() {
		
		double[] per = rtb.getPercentages();
		
		DecimalFormat formatter = new DecimalFormat("#0.0");
		
		String formattedStopOnePercentages = 	"Red: "+formatter.format(per[0]*100)+"    Black: "+formatter.format(per[1]*100);
		String formattedStopTwoPercentages =    "Higher: "+formatter.format(per[2]*100)+"    Lower: "+formatter.format(per[3]*100)+"    Post: "+formatter.format(per[4]*100);
		String formattedStopThreePercentages =	"Inside: "+formatter.format(per[5]*100)+"    Outside: "+formatter.format(per[6]*100)+"    Post: "+formatter.format(per[7]*100);
		String formattedStopFourPercentages = 	"Spade: "+formatter.format(per[8]*100)+"    Club: "+formatter.format(per[9]*100)+"    High: "+formatter.format(per[10]*100)+"    Diamond: "+formatter.format(per[11]*100);
		
		if 		(rtb.getStop() == STOP.RED_OR_BLACK)
			lblResult.setText(formattedStopOnePercentages);
		else if (rtb.getStop() == STOP.HIGHER_LOWER)
			lblResult.setText(formattedStopTwoPercentages);
		else if (rtb.getStop() == STOP.IN_OUT_POST)
			lblResult.setText(formattedStopThreePercentages);
		else if (rtb.getStop() == STOP.GUESS_SUIT)
			lblResult.setText(formattedStopFourPercentages);
	}
	
	private void checkShowPercentages() {
		lblResult.setVisible(rdbtnShowOdds.isSelected());
	}
	
	private void checkResetEmptyDeck() {
		if (rtb.getStop() == STOP.EMPTY_DECK) {
			removeAllButtonsFromPanel();
			addButtonToPanel(btnPlayAgain);
		}
	}

	private void lostRoundReset() {
		removeAllButtonsFromPanel();
		addButtonsToPanel(listFirstButtons);		
	}
	
	private void removeButtonsFromPanel(ArrayList<JButton> listButtons) {
		for (int i = 0; i < listButtons.size(); i++) {
			this.panelButtons.remove(listButtons.get(i));
		}
		this.panelButtons.revalidate();
		this.panelButtons.repaint();
	}
	
	private void removeButtonFromPanel(JButton btn) {
		this.panelButtons.remove(btn);
		this.panelButtons.revalidate();
		this.panelButtons.repaint();
	}
	
	private void addButtonsToPanel(ArrayList<JButton> listButtons) {
		for (int i = 0; i < listButtons.size(); i++) {
			this.panelButtons.add(listButtons.get(i));
		}
		this.panelButtons.revalidate();
		this.panelButtons.repaint();
	}
	
	private void addButtonToPanel(JButton btn) {
		this.panelButtons.add(btn);
		this.panelButtons.revalidate();
		this.panelButtons.repaint();
	}
	
	private void removeAllButtonsFromPanel() {
		this.panelButtons.removeAll();
		this.panelButtons.revalidate();
		this.panelButtons.repaint();
	}
	
	private void resetCardImages() {
		label_card_one.setIcon(new ImageIcon(cropAndScaleImage(biCardBack, null, RTBConstants.CARD_SCALE, RTBConstants.CARD_SCALE)));
		label_card_two.setIcon(new ImageIcon(cropAndScaleImage(biCardBack, null, RTBConstants.CARD_SCALE, RTBConstants.CARD_SCALE)));
		label_card_three.setIcon(new ImageIcon(cropAndScaleImage(biCardBack, null, RTBConstants.CARD_SCALE, RTBConstants.CARD_SCALE)));
		label_card_four.setIcon(new ImageIcon(cropAndScaleImage(biCardBack, null, RTBConstants.CARD_SCALE, RTBConstants.CARD_SCALE)));
	}
	
	private void resetCardText() {
		lblCardOneText.setText("");
		lblCardTwoText.setText("");
		lblCardThreeText.setText("");
		lblCardFourText.setText("");
	}
	
	private void enableChangeDeckSize(){
		mntmNumDecksOne.setEnabled(true);
		mntmNumDecksTwo.setEnabled(true);
		mntmNumDecksThree.setEnabled(true);
		mntmNumDecksFour.setEnabled(true);
	}
	
	private void disableChangeDeckSize(){
		mntmNumDecksOne.setEnabled(false);
		mntmNumDecksTwo.setEnabled(false);
		mntmNumDecksThree.setEnabled(false);
		mntmNumDecksFour.setEnabled(false);
	}
	
	public void notifyCardChanges() {
		
	}
	
	public void GameLoop(GUESS_OPTIONS guess) {
		// TODO remove code for processing outside of buttons and into state machine here
	}

}
