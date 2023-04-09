package picross;

	
	import java.awt.*;
	import java.awt.event.*;
	import java.util.ArrayList;
	import java.util.Locale;
	import java.util.ResourceBundle;

	import javax.swing.*;
	import javax.swing.border.TitledBorder;
	


	/**
	 * Class Game extends Jframe, holds all methods that create the visuals and some functionality of the game
	 */
	/**
	 * @author evely
	 *
	 */
	/**
	 * @author evely
	 *
	 */
	/**
	 * @author evely
	 *
	 */
	/**
	 * @author evely
	 *
	 */
	/**
	 * @author evely
	 *
	 */
	/**
	 * @author evely
	 *
	 */
	public class GameView extends JFrame {
		
		

		//create dialog window
		private JFrame f = new JFrame();

		/** String variable for message asking user to select language */
		private String selectLang = "";

		/** String variable for language 1 (English) */
		private String lang1 = "english";

		/** String variable for language 2 (French) */
		private String lang2 = "french";

		/** String variable label for button selected*/
		private String selected = "selected";

		/**
		 * Long that indicates the serial version ID
		 */
		private static final long serialVersionUID = 1L;

		/** String variable for design mode label*/
		private String designMode = "design mode";

		/** String variable for play mode label*/
		private String playMode = "play mode";

		/** String variable for show mark label*/
		private String mark = "mark mode";

		//TODO internationalize mark

		/** String variable dimensions label*/
		private String DIMLABEL = "dimensions";

		/** String variable label for language 1 (English)*/
		private String LANG1;

		/** String variable label for language 2 (French)*/
		private String LANG2;

		/** String variable label for move history log*/
		private String historyLabel = "history";

		/** String variable label for timer*/
		private String timeElapsed = "timer";

		/** String variable label for the scoreboard*/
		private String scoreboard = "scoreboard";

		/** String variable label for number of correct tiles*/
		@SuppressWarnings("unused")
		private String correctTiles = "points";
		//TODO change to points

		/** String variable label for the final score*/
		@SuppressWarnings("unused")
		private String scoreTally = "";

		/** String variable label for the save button*/
		private String save = "save";

		/** String variable label for the load button*/
		private String load = "load";

		/** String variable label for the reset button*/
		private String reset = "reset";

		/** JPanel for north section of the game */
		private JPanel north = new JPanel();

		/** Title for the game */
		private JLabel title = new JLabel("PICROSS");

		/** JPanel for west section of the game */
		private JPanel west = new JPanel(new BorderLayout());

		/** JPanel for game mode selection */
		private JPanel gamePlay = new JPanel(new FlowLayout(FlowLayout.LEFT));

		/** ButtonGroup of radio buttons for game mode */
		private ButtonGroup mode = new ButtonGroup();

		/** Design mode button */
		private JRadioButton design;

		/** Play mode button */
		private JRadioButton play;

		/** Tickbox to setMarkMode */
		private JCheckBox markBox;

		/** Area where user sets dimensions */
		private JPanel dimensions = new JPanel();

		/** Label for dimensions dropdown */
		private JLabel dLabel = new JLabel(DIMLABEL);

		/** Int array holds board dimension options */
		private Integer[] dimOptions = { 3, 4, 5, 6, 7, 8 };

		/**Ddimensions dropdown */
		private JComboBox<Integer> setDim = new JComboBox<Integer>(dimOptions);

		/** JPanel that shows the history of moves user has made */
		private JPanel moveLog = new JPanel();

		private ArrayList<String> messages;

		// Define a JTextArea to display the messages in a scroll pane
		private JTextArea messageArea;

		// Create a JScrollPane to hold the JTextArea
		private JScrollPane scrollPane;

		/** JPanel for east section of the game */
		private JPanel east = new JPanel(new BorderLayout());

		/** JPanel for language selection, number of moves made, and timer */
		private JPanel trackers = new JPanel(new FlowLayout());

		/** String array holds available language options */
		private String[] languages = { LANG1, LANG2 };

		/** JPanel for tracking the time elapsed during gameplay */
		private JPanel timeTracker;

		/** Label for the timer */
		private JLabel timerLabel;

		/** Timer value */
		private JLabel timer = new JLabel("0:00");

		/** JPanel that shows scoreboard title */
		private JPanel score = new JPanel();

		/** Scoreboard label */
		private JLabel scoreLabel;

		/**JPanel to display scoring information */
		private JPanel scoreBoard = new JPanel(new FlowLayout());
		
		 

		
		/** JPanel for south section of the game */
		private JPanel south;

		/** Save button */
		private JButton saveButton;

		/** Load button */
		private JButton loadButton;

		/** Reset button */
		private JButton resetButton;

		/** Center panel of the game */
		private JPanel center = new JPanel(new BorderLayout());;

		/** Top row of labels for the squares */
		private JPanel topLabels;

		/** Left side row of labels for the squares */
		private JPanel leftLabels;



		/**Locale*/
		private Locale currentLocale;

		/**
		 * Resource bundle
		 */
		private static ResourceBundle texts;

		/**
		 * Resource file name.
		 */
		private static String SYSTEMMESSAGES = "resources/texts";

		/**
		 * String to indicate button id.
		 */
		private String but = "button";

		/** Actual gameplay board panel */
		private JPanel board;

		/**
		 * menuBar
		 */
		private static JMenuBar menuBar;

		/**
		 * menu
		 */
		private static JMenu menu;

		/**
		 * Menu items new game, display solution, exit and help
		 */
		private static JMenuItem newGame, displaySolution, exit, help;

		/**
		 * Menu label
		 */
		private static final String menuLabel = "Menu";

		/**
		 * Points label
		 */
		private JLabel points = new JLabel("0");
		
		/**
		 * Color for wrong square
		 */
		private Color wrong = new Color(191, 62, 98);

		/**
		 * Color for right square 
		 */
		private Color right = new Color(149, 224, 188);
		
		/**
		 * Color for marked square
		 */
		private Color markRight = Color.yellow;
		
		/**
		 * Array for jButtons
		 */
		private JButton[][] buttons;
		
		/**
		 * Panel to display color choices
		 */
		private JPanel colourPanel;
		@SuppressWarnings("unused")
		
		/**
		 * Right color panel
		 */
		private JPanel rightPanel;
		@SuppressWarnings("unused")
		/**
		 * Marked color panel
		 */
		private JPanel markPanel;
		@SuppressWarnings("unused")
		
		/**
		 * Wrong color panel
		 */
		private JPanel wrongPanel;
		
		/**
		 * Reference to game controller
		 */
		GameController c = new GameController(this);


		 

		/**
		 * No-arg constructor for Game.
		 */
		
		public GameView() {
			
			
		
		}
		
		
		
		/**
		 * Method to update interface
		 * 
		 * @param langIndex Language index
		 */
		public void updateInterface(int langIndex) {
			String language = "";
			String country = "";
			switch (langIndex) {
			case 0:
				language = "en";
				country = "CAN";
				break;
			case 1:
				language = "fr";
				country = "FR";
				break;
				
			default:
				language = "en";
				country = "CAN";
				break;
		
			}
			try {
				currentLocale = new Locale(language, country);
				texts = ResourceBundle.getBundle(SYSTEMMESSAGES, currentLocale);
				// Uploading properties
				LANG1 = texts.getString("LANG1");
				LANG2 = texts.getString("LANG2");
				designMode = texts.getString("DESIGNMODE");
				playMode = texts.getString("PLAYMODE");
				DIMLABEL = texts.getString("DIMLABEL"); 
				mark = texts.getString("mark");
				historyLabel = texts.getString("HISTORYLABEL");
				timeElapsed = texts.getString("TIMEELAPSED");
				scoreboard = texts.getString("SCOREBOARD");
				correctTiles = texts.getString("CORRECTTILES");
				scoreTally = texts.getString("SCORETALLY");
				save = texts.getString("SAVE");
				load = texts.getString("LOAD");
				reset = texts.getString("RESET");
				selected = texts.getString("SELECTED");
				but = texts.getString("BUT");
				languages[0] = LANG1;
				languages[1] = LANG2;
				
			} catch (Exception e) {
				;
			}
		}
		
		/**
		 * create Game creates the gui/game board
		 * @param dim - dimensions of the game board
		 * @param boolArray - boolean array to get if buttons are right or wrong
		 */
		public void createGame(int dim, boolean [][] boolArray) {
			
			
			// block to load the window
			setTitle("PICROSS - Evelyn O'Driscoll");
			setSize(800, 600);
			setLocationRelativeTo(null);
			setVisible(true);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			// create a menubar
	        menuBar = new JMenuBar();
	 
	        // create a menu
	        
	        //TODO make the menu international
	   
	        menu = new JMenu(menuLabel);
	        menu.setHorizontalAlignment(JMenu.CENTER);
	        menuBar.setBackground(Color.pink);
	        
	 
	        // create menu items
	        newGame = new JMenuItem("New");
	        displaySolution = new JMenuItem("Solution");
	        exit = new JMenuItem("Exit");
	        help = new JMenuItem("Help");
	 
	        // add menu items to menu
	        menu.add(newGame);
	        menu.add(displaySolution);
	        menu.add(exit);
			menu.add(help);
	 
	        // add menu to menu bar
	        menuBar.add(menu);
	 
	        // add menubar to frame
	        f.setJMenuBar(menuBar);
	        
	        help.addActionListener(c);
	        
	        newGame.addActionListener(c);
	        
	        
	        exit.addActionListener(c);
			
	        displaySolution.addActionListener(c);
		
			//set layout for the whole window
			setLayout(new BorderLayout());

			// setting up the north panel (title view)
			menuBar.setPreferredSize(new Dimension(150, 50));
			north.setLayout(new BorderLayout());
			north.add(title, BorderLayout.CENTER);
			north.add(menuBar, BorderLayout.EAST);
			title.setFont(new Font("Arial", Font.BOLD, 24)); // set font size to 24 and bold
			title.setHorizontalAlignment(JLabel.CENTER);
			north.setPreferredSize(new Dimension(800, 50));
			north.setBackground(Color.pink);
			add(north, BorderLayout.NORTH);

			// setting up west panel (controls)
			design = new JRadioButton(designMode);
			play = new JRadioButton(playMode);
			markBox = new JCheckBox(mark);
			markBox.addActionListener(c);
			west.setBackground(Color.pink);
			west.setPreferredSize(new Dimension(150, 550));
			add(west, BorderLayout.WEST);

			design.setBackground(Color.pink);
			play.setBackground(Color.pink);
			play.addActionListener(c);;
			design.setSelected(true);
			markBox.setBackground(Color.pink);
			markBox.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

			mode.add(design);
			mode.add(play);
			gamePlay.add(design);
			gamePlay.add(play);
			gamePlay.add(Box.createVerticalStrut(30));
			gamePlay.add(markBox);
			gamePlay.setBackground(Color.pink);
			gamePlay.setPreferredSize(new Dimension(150, 125));
			west.add(gamePlay, BorderLayout.NORTH);

			//allow user to select dimensions of the game 
			dimensions.setPreferredSize(new Dimension(150, 75));
			dimensions.setBackground(Color.pink);
			dimensions.add(dLabel);
			dimensions.add(setDim);
			setDim.setSelectedIndex(2);
			
			setDim.addActionListener(c);
			west.add(dimensions, BorderLayout.CENTER);

			moveLog = createMoveLog();
			west.add(moveLog, BorderLayout.SOUTH);

			// setting up east panel (scoring)
			east.setPreferredSize(new Dimension(150, 550));
			east.setBackground(Color.pink);
			add(east, BorderLayout.EAST);

			trackers.setPreferredSize(new Dimension(150, 100));
			trackers.setBackground(Color.pink);

			timeTracker = new JPanel(new FlowLayout());
			timeTracker.setBackground(Color.pink);

			//track time elapsed since first move made
			
			timerLabel = new JLabel(timeElapsed);
			timeTracker.add(timerLabel);
			timeTracker.add(timer);
			
			
			trackers.add(timeTracker);

			//create label for scoreboard
			scoreLabel = new JLabel(scoreboard);
			score.add(scoreLabel);
			score.setPreferredSize(new Dimension(150, 25));
			score.setBackground(Color.pink);

			
			//display scoring information
		
			
			scoreBoard.setPreferredSize(new Dimension(150, 325));
			scoreBoard.setBackground(Color.pink);
			points.setFont(new Font("Sans-Serif", Font.PLAIN, 24));
			scoreBoard.add(points);
	

			east.add(trackers, BorderLayout.NORTH);
			east.add(score, BorderLayout.CENTER);
			east.add(scoreBoard, BorderLayout.SOUTH);

			// setting up south panel (save/load/reset)
			south = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
			south.setBackground(Color.pink);
			south.setPreferredSize(new Dimension(500, 50));
			add(south, BorderLayout.SOUTH);
			
			saveButton = new JButton(save);
			loadButton = new JButton(load);
			resetButton = new JButton(reset);
			resetButton.addActionListener(c);
			saveButton.addActionListener(c);
			loadButton.addActionListener(c);
			
			south.add(saveButton);
			south.add(loadButton);
			south.add(resetButton);
			

			// setting up center panel (board and board labels)
			
			center.setBackground(Color.pink);
			center.setPreferredSize(new Dimension(500, 500));
			add(center, BorderLayout.CENTER);
			
			leftLabels = createRowLabels(boolArray);
			topLabels = createColLabels(boolArray);
			

			
			board = createBoard(dim, boolArray);
			center.add(board, BorderLayout.CENTER);
			center.add(topLabels, BorderLayout.NORTH);
			center.add(leftLabels, BorderLayout.WEST);

			
			
			newGame.setActionCommand("newGame");
			displaySolution.setActionCommand("solution");
			exit.setActionCommand("exit");
			help.setActionCommand("help");
			play.setActionCommand("playMode");
			design.setActionCommand("designMode");
			setDim.setActionCommand("dimensions");
			markBox.setActionCommand("markMode");
			resetButton.setActionCommand("reset");
			saveButton.setActionCommand("save");
			loadButton.setActionCommand("load");
			
			
		}
		
		
		/**
		 * createMoveLog creates a jPanel that shows the move history log
		 * @return JPane; moveLog
		 */
		
		public JPanel createMoveLog() {
			
			moveLog.setPreferredSize(new Dimension(150, 400));
			messages = new ArrayList<>();
			messageArea = new JTextArea();
			scrollPane = new JScrollPane(messageArea);
			messageArea.setEditable(false);
			
			//show history of moves made in the game
			moveLog.setPreferredSize(new Dimension(150, 300));
			moveLog.setBackground(Color.pink);
			moveLog.setBorder(BorderFactory.createTitledBorder(null, historyLabel, TitledBorder.CENTER,
					TitledBorder.DEFAULT_JUSTIFICATION, getFont(), Color.black));

			scrollPane.setPreferredSize(new Dimension(150, 300));
			scrollPane.setBackground(Color.pink);
			
			messageArea.setCaretPosition(messageArea.getDocument().getLength());

			
			moveLog.add(scrollPane);
			
			moveLog.revalidate();
	    	moveLog.repaint();
	    	scrollPane.revalidate();
	    	scrollPane.repaint();
	    	west.revalidate();
	    	west.repaint();
			
			
			return moveLog;
		}
		
		
		
		/**
		 * createButtons creates an array of jbuttons that represent the game board
		 * @param dim - dimensions of the board
		 * @return buttons - array of JButtons
		 */
		public JButton[][] createButtons(int dim) {
		    buttons = new JButton[dim][dim];
		    
		    for (int row = 0; row < dim; row++) {
		        for (int col = 0; col < dim; col++) {
		            JButton button = new JButton();
		            
		            buttons[row][col] = button;
		          
		            button.putClientProperty("row", row);
		            button.putClientProperty("col", col);
		            button.setBackground(Color.white);
		            
		            button.addActionListener(c); 
		            button.setActionCommand("boardButton");
		            
		            button.setBorder(BorderFactory.createLineBorder(Color.black));
		        }
		    }
		    
		    return buttons;
		}
		
		/**
		 * createBoard creates adds the buttons to the board pane;
		 * @param dim - dimensions of the board
		 * @param boolArray - array that shows if buttons are true or false
		 * @return board - game board panel
		 */

		public JPanel createBoard(int dim, boolean[][] boolArray) {
		    board  = new JPanel(new GridLayout(dim, dim));

		    JButton[][] buttons = createButtons(dim);

		    for (int row = 0; row < dim; row++) {
		        for (int col = 0; col < dim; col++) {
		            board.add(buttons[row][col]);
		        }
		    }

		    return board;
		}

		
		/** createRowLabels calls a method in controller that takes the bool array and returns a string of the label values
		 * @param boolArray - true or false array for labels
		 * @return leftLabels - jPanel of row labels
		 */
		public JPanel createRowLabels(boolean [][] boolArray) {
			
			
			String [] labels = c.getRowLabels(boolArray);
			
			leftLabels = new JPanel(new GridLayout(labels.length, 1));
			
			for(int i = 0; i < labels.length; i++) {
				JLabel label = new JLabel(labels[i]);
			
				label.setVerticalAlignment(JLabel.CENTER);
				label.setHorizontalAlignment(JLabel.CENTER);
				leftLabels.add(label);		
			}
			
			leftLabels.setBorder(BorderFactory.createLineBorder(Color.black));
			leftLabels.setPreferredSize(new Dimension(50, 500));
			leftLabels.setBackground(Color.pink);
			
			return leftLabels;
			
		}
		
		/** createColLabels calls a method in controller that takes the bool array and returns a string of the label values
		 * @param boolArray - true or false array for labels
		 * @return leftLabels - jPanel of column labels
		 */
		
		public JPanel createColLabels(boolean [][] boolArray) {
			
			
			String [] labels = c.getColLabels(boolArray);
			
			topLabels = new JPanel(new GridLayout( 1, labels.length));
			
			for(int i = 0; i < labels.length; i++) {
				JLabel label = new JLabel(labels[i]);
			
				label.setVerticalAlignment(JLabel.CENTER);
				label.setHorizontalAlignment(JLabel.CENTER);
				topLabels.add(label);		
			}
			
			topLabels.setBorder(BorderFactory.createLineBorder(Color.black));
			topLabels.setPreferredSize(new Dimension(500, 50));
			topLabels.setBackground(Color.pink);
			
			
			return topLabels;
			
		}
		
		
		/**
		 * resetGame creates a new game and resets the board
		 */
		public void resetGame() {
			boolean[][] boolArray = c.configureRandom(5);
			center.remove(board);
			center.remove(topLabels);
			center.remove(leftLabels);
			topLabels = createColLabels(boolArray);
			board = createBoard(5, boolArray);
			leftLabels = createRowLabels(boolArray);
			
			center.add(board, BorderLayout.CENTER);
			center.add(topLabels, BorderLayout.NORTH);
			center.add(leftLabels, BorderLayout.WEST);
			center.repaint();
			center.revalidate();

		}
		
		/**
		 * loadSavedGame - loads  a game from a file and updates the board
		 */
		
		public void loadSavedGame() {
			boolean[][] boolArray = c.existing();
			center.remove(board);
			center.remove(topLabels);
			center.remove(leftLabels);
			topLabels = createColLabels(boolArray);
			board = createBoard(5, boolArray);
			leftLabels = createRowLabels(boolArray);
			
			center.add(board, BorderLayout.CENTER);
			center.add(topLabels, BorderLayout.NORTH);
			center.add(leftLabels, BorderLayout.WEST);
			center.repaint();
			center.revalidate();
		
		}
		
	
		/**
		 * changeDimensions - changes the dimensions of the board and resets it
		 */


		public void changeDimensions() {
			
	    	
	    	int newDim = (int) setDim.getSelectedItem();
	    	boolean[][] boolArray = c.configureRandom(newDim);
			center.remove(board);
			center.remove(topLabels);
			center.remove(leftLabels);
			topLabels = createColLabels(boolArray);
			board = createBoard(newDim, boolArray);
			leftLabels = createRowLabels(boolArray);
			
			center.add(board, BorderLayout.CENTER);
			center.add(topLabels, BorderLayout.NORTH);
			center.add(leftLabels, BorderLayout.WEST);
			center.repaint();
			center.revalidate();
			c.startPlayMode();
	    	
		}
		
		
		
		

		/**
		 * showSolution - colours all squares to display the solution
		 * @param center - center panel 
		 * @param board - board panel
		 * @param setDim - dimensions selection box
		 */
		public void showSolution(JPanel center, JPanel board, JComboBox<Integer> setDim) {
			int newDim = (int)(setDim.getSelectedItem());
			center.removeAll();
			board = createBoard(newDim, c.existing());
	    	center.add(board, BorderLayout.CENTER);
			
		}

	
		/**
		 * Creates the main window for the Picross game.
		 * @param gameMem a boolean value indicating whether to start a new game or use an existing game.
		 */
	public void createWindow(boolean gameMem) {
		

		EventQueue.invokeLater(new Runnable() {
			
			/**
			 * run method creates a new frame, creates the initial dialog window, and then runs the game
			 */ 		 
			public void run() {

				
			
					
				
				//settings for dialog window
			    f.setTitle("PICROSS - Evelyn O'Driscoll");
			    f.setSize(300, 150);
			    f.setLocationRelativeTo(null);
			    f.setVisible(true);
			    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			    
				//create dropdown for language selection
			    JPanel langSelect = new JPanel(new FlowLayout());
			    langSelect.setBorder(BorderFactory.createEmptyBorder(25, 0, 0, 25));
			    JLabel lang = new JLabel (selectLang);
				String[] language = { lang1, lang2};
				JComboBox<String> setLang = new JComboBox<String>(language);

				
				//create ok button
				JButton ok = new JButton("OK");
				
				//add listener to ok button to check if clicked, if yes, run the game
				ok.addActionListener(new ActionListener() {

					//TODO change this to the right action listener
					public void actionPerformed(ActionEvent e) {
						  Object src = e.getSource();
						    if (src == ok) {
					
						    	updateInterface((int)setLang.getSelectedIndex());
						    	f.setVisible(false);
						    	
						    	if (!gameMem) {
						    		createGame(5, c.configure());
						    		
								}
								
								else {
									createGame(5, c.existing());
								
								
								}
						    	
						    	
						    	
						    
					}
					
					
				}
			}
			);
				//add dropdown and buttons to dialog box
				
				langSelect.add(lang);
				langSelect.add(setLang);
				langSelect.add(ok);
				f.add(langSelect);
				
			
				
			}
		});		
	}

	
	/**
	 * showSplashScreen - displays a loading screen with a progress bar
	 */
	public void showSplashScreen() {
	    // Create the content pane to hold components of the splash screen
	    JPanel content = new JPanel(new BorderLayout());
	    content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	    content.setBackground(Color.PINK);

	    // Create the panel to hold the progress bar
	    JPanel panel = new JPanel(new BorderLayout());
	    panel.setOpaque(false);

	    // Create the progress bar
	    JProgressBar progressBar = new JProgressBar();
	    progressBar.setMinimum(0);
	    progressBar.setMaximum(100);
	    progressBar.setValue(0);
	    progressBar.setStringPainted(true);
	    progressBar.setForeground(Color.pink);

	    // Create the label to display the progress
	    JLabel progressLabel = new JLabel("Loading... please wait");
	    progressLabel.setForeground(Color.pink);
	    progressLabel.setHorizontalAlignment(JLabel.CENTER);
	    progressLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));

	    // Add the progress bar and label to the panel
	    panel.add(progressBar, BorderLayout.CENTER);
	    panel.add(progressLabel, BorderLayout.SOUTH);

	    // Add the panel and logo to the content pane
	    content.add(panel, BorderLayout.SOUTH);
	    JLabel logoLabel = new JLabel("PICROSS");
	    content.add(logoLabel, BorderLayout.CENTER);

	    // Create the window and center it on the screen
	    JWindow window = new JWindow();
	    window.setContentPane(content);
	    window.pack();
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (screenSize.width - window.getWidth()) / 2;
	    int y = (screenSize.height - window.getHeight()) / 2;
	    window.setLocation(x, y);

	    // Show the window
	    window.setVisible(true);

	    // Simulate loading
	    for (int i = 0; i <= 100; i++) {
	        try {
	            Thread.sleep(20);
	        } catch (InterruptedException e) {
	            // Do nothing
	        }
	        progressBar.setValue(i);
	    }

	    // Close the window
	    window.dispose();
	}
	
	
	/**
	 * setTimeDisplay update the timer display if in play mode
	 * @param elapsedSeconds - seconds since game began
	 */
	public void setTimeDisplay(int elapsedSeconds) {
	    int minutes = elapsedSeconds / 60;
	    int seconds = elapsedSeconds % 60;
	    String timeString = String.format("%02d:%02d", minutes, seconds);
	    timeTracker.removeAll();
	    timer.setText(timeString); 
	    
	
		timeTracker.add(timer);
		timeTracker.getParent().revalidate();
		timeTracker.getParent().repaint();
	    
	    
	    
	}
	

	/**
	 * upDate button - change colour of button if clicked
	 * @param row - button row
	 * @param col - button column
	 * @param correct - is the button right?
	 * @param mark - is mark mode set?
	 */
	public void updateButton(int row, int col, boolean correct, boolean mark) {
		
		
		if (correct&&mark) {
			buttons[row][col].setBackground(wrong);
		}
		
		else if(correct==true && mark== false) {
			buttons[row][col].setBackground(right);
		}
		
		else if (correct== false & mark == true) {
			buttons[row][col].setBackground(markRight);
		}
		
		else {
			buttons[row][col].setBackground(wrong);
		}
		
		buttons[row][col].setEnabled(false);
        String message = but  + row + ", " + col + " " + selected;
        messages.add(message);
        messageArea.setText(String.join("\n", messages));
		

		
	}
	
	/**
	 * change points label based on current score
	 * @param newPoints - new value of score
	 */
	public void updatePoints(int newPoints) {
		
		scoreBoard.removeAll();
		String s = String.valueOf(newPoints);
		points.setText(s);
		scoreBoard.add(points);
		scoreBoard.getParent().revalidate();
	    scoreBoard.getParent().repaint();
		
		
		
		
	}
	
	


	/**
	 * showSolution - press all buttons to color them and show solution
	 * @param boolArray - true or false array to determine button color
	 */
	public void showSolution(boolean [][] boolArray) {
		
		
		
		int dim = (int) setDim.getSelectedItem();
		
		
		
		 for (int row = 0; row < dim; row++) {
		        for (int col = 0; col < dim; col++) {
		        	
		        	
		          	  
		                    if (boolArray[row][col]) {
		                 
		                    		  buttons[row][col].setBackground(right);
		                    		 
				                        //TODO increment points
		                    		
		                 
		                        
		                    } else {
		                    	
		         
		                    		buttons[row][col].setBackground(wrong);
			                       
			                        //TODO decrement points
		                    	}
		                   
		                        buttons[row][col].setEnabled(false);
		                       
		                       
		        	}                 
	}

	}
	
	
		
		

	  
	    /**
	     * showHelpDialog - open window to change board colours
	     */
	    public void showHelpDialog() {
	        colourPanel = new JPanel(new GridLayout(3, 1));
	        JDialog helpDialog = new JDialog(f, "Help Dialog", Dialog.ModalityType.APPLICATION_MODAL);
	        helpDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	        
	        rightPanel = addColourPanel(colourPanel, "Right", right);
	        markPanel = addColourPanel(colourPanel, "Mark", markRight);
	        wrongPanel = addColourPanel(colourPanel, "Wrong", wrong);
	   
	        helpDialog.add(colourPanel);
	        helpDialog.pack();
	        helpDialog.setLocationRelativeTo(f);
	        helpDialog.setVisible(true);
	    }
	    
	    
	    /**
	     * addColourPanel show current colpours for right and wrong
	     * @param parentPanel - component holding colour panel
	     * @param name - color name i.e. right wrong
	     * @param color - current color
	     * @return - individual color panel
	     */
	    public JPanel addColourPanel(JPanel parentPanel, String name, Color color) {
	        JPanel indColourPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	        indColourPanel.setPreferredSize(new Dimension(250, 60));
	        indColourPanel.setBackground(color);

	        JLabel nameLabel = new JLabel(name, SwingConstants.CENTER);
	        nameLabel.setPreferredSize(new Dimension(50, 20));

	        JButton colourButton = new JButton("Choose Colour");
	        colourButton.addActionListener(new GameController(this, indColourPanel));
	        colourButton.setActionCommand(name);

	        indColourPanel.add(nameLabel);
	        indColourPanel.add(colourButton);
	        
	        parentPanel.add(indColourPanel);
	        
	        return indColourPanel;
	    }
	    
	    /**
	     * Change the background color of the panel for new color
	     * @param panel - color panel
	     * @param color - color value
	     */
	    public void updatePanelColor(JPanel panel, Color color) {
	        panel.setBackground(color);
	    }
	    
	
	    /**
	     * Show dialog if user goes below -3 points.
	     */
	    public  void loserDialog() {
	        JDialog dialog = new JDialog(f, "You Lose!", true);
	        JLabel label = new JLabel("LOSER!", SwingConstants.CENTER);
	        label.setFont(new Font("Arial", Font.BOLD, 36));
	        label.setForeground(Color.WHITE);
	        JPanel panel = new JPanel(new BorderLayout());
	        panel.add(label, BorderLayout.CENTER);
	        panel.setBackground(Color.PINK);
	        dialog.add(panel);
	        dialog.setSize(300, 200);
	        dialog.setLocationRelativeTo(f);
	        dialog.setVisible(true);
	    }
	    
	    /**
	     * SHow dialog if user scores a perfect game
	     */
	    public  void winnerDialog() {
	        JDialog dialog = new JDialog(f, "Perfect game!", true);
	        JLabel label = new JLabel("WINNER!", SwingConstants.CENTER);
	        label.setFont(new Font("Arial", Font.BOLD, 36));
	        label.setForeground(Color.WHITE);
	        JPanel panel = new JPanel(new BorderLayout());
	        panel.add(label, BorderLayout.CENTER);
	        panel.setBackground(Color.PINK);
	        dialog.add(panel);
	        dialog.setSize(300, 200);
	        dialog.setLocationRelativeTo(f);
	        dialog.setVisible(true);
	    }
	    
	    
	    /**
	     * Call controller to create a new game if selected in the client window
	     * @return boolArray of new game board
	     */
	    public boolean[][] clientGame() {
	    	
	    	boolean[][] boolArray = c.configureRandom(5);
	    	
			return boolArray;
	    	
	    }
	    
	    /**
	     * Send current game to server via client if it exists
	     * @param gameMem - is there a game already in memory?
	     * @return boolean array of game board
	     */
	    public boolean[][] sendCurrentGame (boolean gameMem) {
	    	boolean[][] boolArray;
	    	if(gameMem) {
	    	boolArray = c.existing();
	    	}
	    	
	    	else {
	    		boolArray = c.configureRandom(5);
	    	}
	    	return boolArray;
	    	
	    }
	    
	    /**
	     * Receive current game string and send it to the controller.
	     * @param str - game to be received from server
	     */
	    public void receiveGame (String str) {
	    	c.receiveGame(str);
	    	
	    }
	    



		/**
		 * @param wrong the wrong color to set
		 */
		public void setWrong(Color wrong) {
			this.wrong = wrong;
		}



		/**
		 * @param right the right color to set
		 */
		public void setRight(Color right) {
			this.right = right;
		}



		/**
		 * @param markRight the markRight to set
		 */
		public void setMarkRight(Color markRight) {
			this.markRight = markRight;
		}
	    
		/**
		 * @param markBox get if markBox is checked
		 */
		public JCheckBox getMarkBox() {
			return markBox;
		}
 
	    
	    
	}
   
   

	
	




	   
	   
	   
	   
	
	
	
	
	



