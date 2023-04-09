package picross;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;


/**
 * Contains the logic of the gameboards
 * @author evely
 *
 */
public class GameModel {
	
	/**
	 * Array containing values of the buttons
	 */
	boolean[][] boolArray;
	


	/**
	 * creates the default game board
	 * @return boolArray
	 */
	public boolean[][] createBooleanArray() {
		
		String defaultString = "00100,00100,11111,01110,01010";
		
		String[] rows = defaultString.split(",");
		int numRows = rows.length;
		int numCols = rows[0].length();

		
	boolArray  = new boolean[numRows][numCols];
	for (int i = 0; i < numRows; i++) {
	    for (int j = 0; j < numCols; j++) {
	        if (rows[i].charAt(j) == '1') {
	            boolArray[i][j] = true;
	        } else {
	            boolArray[i][j] = false;
	        }
	    }
	}
	setBoolArray(boolArray);
	return boolArray;
	}
	

	/**
	 * Creates a random game board
	 * @param random - String to turn into boolean array
	 * @return boolArray
	 */
public boolean[][] createRandomArray(String random) {
		
		String[] rows = random.split(",");
		int numRows = rows.length;
		int numCols = rows[0].length();

		
	boolArray  = new boolean[numRows][numCols];
	for (int i = 0; i < numRows; i++) {
	    for (int j = 0; j < numCols; j++) {
	        if (rows[i].charAt(j) == '1') {
	            boolArray[i][j] = true;
	        } else {
	            boolArray[i][j] = false;
	        }
	    }
	}
	setBoolArray(boolArray);
	return boolArray;
	}



/**
 * Turns a string from a file into a boolean array game board
 * @param filename - name of text file
 * @return boolArray
 */
public boolean[][] readArrayFromFile(String filename) {
	
   try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
        String line = null;
        
        line = reader.readLine();
        int dim = Integer.parseInt(line);
        
        boolArray = new boolean[dim][dim];
        
        for(int i = 0; i <dim; i++) {
        	line = reader.readLine();
        	for(int j = 0; j < dim; j++) {
        		boolean b = false;
        		
        		
        		 if(line.charAt(j) == '1') {
                     b = true;
                 }
                 boolArray[i][j] = b;
	
        		}
        		
        	}
        }

     catch (IOException e) {
        e.printStackTrace();
    }
    
    setBoolArray(boolArray);
	return boolArray;
}





/**
 * generates random string to become randomized game board	
 * @param dimension - dimensions of the board
 * @return boolArray
 */
public String generateRandomString(int dimension) {
    StringBuilder sb = new StringBuilder();
    Random rand = new Random();
    
    

    for (int i = 0; i < dimension; i++) {
        for (int j = 0; j < dimension; j++) {
            sb.append(rand.nextInt(2));
        }
        if (i < dimension - 1) {
            sb.append(", ");
        }
    }

    return sb.toString();
}



	/**
	 * @return the boolArray
	 */
	public boolean[][] getBoolArray() {
		return boolArray;
	}



	/**
	 * @param boolArray the boolArray to set
	 */
	public void setBoolArray(boolean[][] boolArray) {
		this.boolArray = boolArray;
	}
	
	
	
	



	
	
	
	
	
	
	
}


