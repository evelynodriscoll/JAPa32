package picross;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JPanel;


/**
 * Class controls the back end of Picross game
 * @author evely
 *
 */

public class GameController implements ActionListener {

	/**
	 *The view component of the Picross game. 
	 */
	private GameView view;
	
	/**
	 *The model component of the Picross game. 
	 */
	private GameModel model = new GameModel();
	
	/**
	 *The JPanel used for color selection.
	 */
	private JPanel panel;
	
	/**
	 * The current color used for coloring cells.
	 */
	private Color colour;
	
	/**
	 *The boolean 2D array representing the game board.
	 */
	private boolean[][] boolArray;
	
	/**
	 *A boolean indicating whether the "mark" mode is activated.
	 */
	private boolean markSet = false;
	
	/**
	 *The current point score of the player.
	 */
	private int points = 0;
	
	/**
	 *A boolean indicating whether the game is in play mode or design mode.
	 */
	private boolean playMode = false;
	
	/**
	 *A ControllableTimer object used to manage the game timer.
	 */
	ControllableTimer timer;
	
	/**
	 *The number of games played so far.
	 */
	private int numGames;
	
	/**
	 * A Client object used to handle communication with the server.
	 */
	private Client client = new Client();
	

	/**
	 * Default constructor
	 */
	public GameController() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Constructor that takes view object
	 * @param pView - instance of GameView
	 */
	public GameController(GameView pView) {
		view = pView;
	}
	
	/**
	 * Constructor that takes model and view
	 * @param model - GameModel instance
	 * @param view - GameView instance
	 */
	public GameController(GameModel model, GameView view) {
		this.model = model;
		this.view = view;
		
	}

	/**
	 * Constructor that takes view and color Panel
	 * @param view - gameView instance
	 * @param panel - colour panel
	 */
	 public GameController(GameView view, JPanel panel) {
	        this.view = view;
	        this.panel = panel;
	    }

	
	 /**
	  * Create default board array
	  * @return boolArray
	  */
	public boolean [][] configure() {
		
		boolean [][] boolArray = model.createBooleanArray();
		return boolArray;
	}
	
	/**
	 * Get existing board array
	 * @return boolArray
	 */
	public boolean [][] existing() {
		boolean [][] boolArray = model.getBoolArray();
		return boolArray;
	}
	
	/**
	 * Create new random board array
	 * @param dimension - dimensions of the board
	 * @return boolArray
	 */
	public boolean [][] configureRandom(int dimension) {
		
		String str = model.generateRandomString(dimension);
		boolean [][] boolArray = model.createRandomArray(str);
		
		return boolArray;
	}
	
	
	/**
	 * Start timer
	 */
	public void startPlayMode() {
		
		timer = new ControllableTimer(view);
		timer.start();
		
	}

	/**
	 * Reset timer
	 */
	public void resetTimer() {
		timer = new ControllableTimer(view);
		timer.start();
	}
	
	
	
	/**
	 * Change the colours
	 * @return new color to be set
	 */
	public Color colourChanger() {
		 Color newColor = JColorChooser.showDialog(null, "Choose Colour", panel.getBackground());
        if (newColor != null) {
            view.updatePanelColor(panel, newColor);
        }
        
        return newColor;
	}
	
	
	/**
	 * Gets what a perfect score in the game would be
	 * @return perfect score as an int
	 */
	public int perfectGame() {
		
		int perfectScore = 0;
		
		boolArray = existing();
		
		int numRows = boolArray.length;
		int numCols = boolArray[0].length;
		
		 for (int i = 0; i < numRows; i++) {
		        for (int j = 0; j < numCols; j++) {
		            if(boolArray[i][j]) {
		                perfectScore++;
		            }
		        }
		    }
		    return perfectScore;
		}
	
	
	/**
	 * Create a boolean array in model from string sent by server
	 * @param str game as ones and zeroes
	 */
	public void receiveGame(String str) {
		
		 model.createRandomArray(str);
		
	}
	
	
	/**
	 * Save current game in memory to a text file.
	 * @throws IOException - if writer doesn't work
	 */
	public void saveGame() throws IOException {
		boolean[][] boolArray = existing();
		int dim = boolArray.length;
		String filename = "game" + numGames + ".txt";
		
		
		numGames++;
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
		writer.write(dim + "\n");
		
		for(int i = 0; i < boolArray.length; i++) {
			for(int j = 0; j < boolArray[0].length; j++) {
				String str = "0";
				if(boolArray[i][j]) {
					str = "1";
				}
				writer.write(str);
			}
			writer.write("\n");
		}
		
			    
		writer.close();
		
	}
	
	
	/**
	 * Load a new game from a text file.
	 */
	public void loadGame() {
		
		   
		JFileChooser fileChooser = new JFileChooser();
		File selectedFile = null;
			      
	   int returnValue = fileChooser.showOpenDialog(null);
	   
	   fileChooser.setDialogTitle("Select a game: ");
		
	   if (returnValue == JFileChooser.APPROVE_OPTION) {
			        selectedFile = fileChooser.getSelectedFile();
			         
		}
	   
	   
	   model.readArrayFromFile(selectedFile.getName());
	   
	   
  
			   }
	
	



	@Override
    public void actionPerformed(ActionEvent e) {
		

        String command = e.getActionCommand();

        switch (command) {
            case "newGame":
                view.resetGame();
                points = 0;
                view.updatePoints(points);
                
                
                break;
                
            case "boardButton":
            	
            	Object source = e.getSource();
                if (source instanceof JButton) {
                    JButton button = (JButton) source;
                    String actionCommand = e.getActionCommand();
                    if ("boardButton".equals(actionCommand)) {
                        
                        int row = (int) button.getClientProperty("row");
                        int col = (int) button.getClientProperty("col");
                        boolArray = existing();
                        if((boolArray[row][col])&& markSet) {
                        	view.updateButton(row, col, true, true);
                        	if(playMode) {
                        		points--;
                        		if(points<-3) {
                        			view.loserDialog();
                        			view.resetGame();
                        			points =0;
                        		}
                        		client.updatePoints(points);
                        		view.updatePoints(points);
                        	}
                    	   
                       }
                        else if (boolArray[row][col] == true && markSet == false) {
                        	view.updateButton(row, col, true, false);
                        	if(playMode) {
                        	points++;
                        	int perfectScore = perfectGame();
                        	if(points == perfectScore)  {
                        		view.winnerDialog();
                        	}
                        	client.updatePoints(points);
                        	view.updatePoints(points);
                        	}
                        }
                        
                        else if (boolArray[row][col] == false && markSet == true) {
                        	view.updateButton(row, col, false, true);
                        	if(playMode) {
                        	points++;
                        	client.updatePoints(points);
                        	view.updatePoints(points);
                        	}
                        }
                        
                        else {
                        	view.updateButton(row, col, false, false);
                        	if(playMode) {
                        		points--;
                        		if(points<-3) {
                        			view.loserDialog();
                        			view.resetGame();
                        			points = 0;
                        		}
                        		client.updatePoints(points);
                        		view.updatePoints(points);
                        	}
                        }
                    }
                }
            	
            	break;
            case "solution":
                view.showSolution(existing());
                break;
            case "exit":
                System.exit(0);
                break;
                
            case "help":
            	view.showHelpDialog();
                break;
                
            case "Right" :
            		colour = colourChanger();
            		view.setRight(colour);
            	break;
            	
            case "Mark":
            	colour = colourChanger();
        		view.setMarkRight(colour);
            	
            	break;
            	
            case "Wrong":
            	colour = colourChanger();
        		view.setWrong(colour);
            	break;
            
            case "playMode":
                playMode = true;
                startPlayMode();
                break;
            case "designMode":
                playMode = false;
                break;
            case "dimensions":
                view.changeDimensions();
                points = 0;
                view.updatePoints(points);
                break;
            case "markMode":
                if(view.getMarkBox().isSelected()) {
                	markSet = true;
                }
                
                else {
                	markSet = false;
                }
                break;
            case "reset":
                view.resetGame();
                points = 0;
                view.updatePoints(points);
                break;
                
            case "load":
            	loadGame();
            	view.loadSavedGame();
            	break;   	
            	
            case "save":
			try {
				saveGame();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            	break; 
            default:
                break;
        }
   
	}
	
	
	/**
	 * Takes a boolean array of a game board and converts it to a string of labels 
	 * @param grid - bool Array game board
	 * @return - string of labels
	 */
	public String[] getRowLabels(boolean[][] grid) {
	    int numRows = grid.length;
	    int numCols = grid[0].length;
	    String[] rowLabels = new String[numRows];

	    for (int i = 0; i < numRows; i++) {
	        StringBuilder sb = new StringBuilder();
	        int count = 0;
	        for (int j = 0; j < numCols; j++) {
	            if (grid[i][j]) {
	                count++;
	            } else {
	                if (count > 0) {
	                    sb.append(count).append(" ");
	                    count = 0;
	                }
	            }
	        }
	        if (count > 0) {
	            sb.append(count);
	        }
	        rowLabels[i] = sb.toString().trim();
	    }

	    return rowLabels;
	}

	/**
	 * Takes a boolean array of a game board and converts it to a string of labels 
	 * @param grid - bool Array game board
	 * @return - string of labels
	 */
	
	public String[] getColLabels(boolean[][] grid) {
	    int numRows = grid.length;
	    int numCols = grid[0].length;
	    String[] colLabels = new String[numCols];

	    for (int j = 0; j < numCols; j++) {
	        StringBuilder sb = new StringBuilder();
	        int count = 0;
	        for (int i = 0; i < numRows; i++) {
	            if (grid[i][j]) {
	                count++;
	            } else {
	                if (count > 0) {
	                    sb.append(count).append("\n");
	                    count = 0;
	                }
	            }
	        }
	        if (count > 0) {
	            sb.append(count).append("\n");
	        }
	        colLabels[j] = sb.toString().trim();
	    }

	    return colLabels;
	}

	/**
	 * @return the model
	 */
	public GameModel getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(GameModel model) {
		this.model = model;
	}


	
	

	
	
	
}



	
	
