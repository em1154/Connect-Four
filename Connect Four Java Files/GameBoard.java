import java.awt.*;
import javax.swing.*;

/**
 * A Connect Four gameboard.
 *
 * @author Bizzy Moore
 */
 
public class GameBoard {
 	 
    private Player[][] gameboard_; // gameboard with Connect Four pieces
    private int numGames_; // number of Connect Four games played
    private int numTies_; // number of ties that have taken place
 	 
    /**
     * A GameBoard.
     */
 	  
    public GameBoard() {
 	  	  
	gameboard_ = new Player[6][7];
	for (int row = 0; row < 6; row++) {
	    for (int col = 0; col < 7; col++) {
		gameboard_[row][col] = null;
	    }
	}
 	  	  
	numGames_ = 0;
	numTies_ = 0;
 	  	  
    }
 	 
    /**
     * Get the number of Connect Four games played.
     *
     * @return the number of Connect Four games played.
     */
 	  
    public int getNumGames() {
	return numGames_;
    }
 	  
    /**
     * Get the number of ties that have taken place.
     *
     * @return the number of ties
     */
 	  
    public int getNumTies() {
	return numTies_;
    }
 	  
    /**
     * Increase the number of games played by one.
     */
 	  
    public void increaseNumGames() {
	numGames_++;
    }
 	  
    /**
     * Increase the number of ties by one.
     */ 
 	  
    public void increaseNumTies() {
	numTies_++;
    }
 	 
    /**
     * Paint the gameboard.
     *
     */
 	   
    public void paint() {
 	   	   
	// draw blue rectangle piece
	Paint.setColor(Color.BLUE);
	Paint.fillRect(0,100,700,602);
 	   	   
	// draw circles
	for (int row = 0, y = 102; row < 6; row++, y = y + 100) {
	    for (int col = 0, x = 2; col < 7; col++, x = x + 100) {
		if (gameboard_[row][col] == null) {
		    // spot in gameboard does not have a player
		    Paint.setColor(Color.WHITE);
		}
		else {	   	   
		    // spot in gameboard does have a player
		    Paint.setColor(gameboard_[row][col].getColor());
		}
		Paint.fillOval(x,y,95,95);
		Paint.setColor(Color.BLACK);
		Paint.drawOval(x,y,95,95);
	    }
	}
 	   	   
    }
 	   
    /**
     * Clear the gameboard.
     */
 	   
    public void clear() {
	// clear all of the discs from the gameboard
	for (int row = 0; row < 6; row++) {
	    for (int col = 0; col < 7; col++) {
		gameboard_[row][col] = null;
	    }
	}
    }
 	   
    /**
     * Determines if column on gameboard is full.
     *
     * @param col the column of the gameboard (0 <= col <= 6)
     * @return true if the specified column is filled and false otherwise
     */
 	   
    public boolean isFull(int col) {
	if (col < 0) {
	    throw new IllegalArgumentException("Value of column must be "+
					      "greater than or equal to zero"+
					      " and less than 7.");
	}
	if (gameboard_[0][col] == null) {
	    // column is not full
	    return false;
	}
	else {
	    // column is full
	    return true;
	}
    }
 	   
    /**
     * Drops a disc for a specified player into a specified column.
     *
     * @param col the column number of the gameboard (0 =< col <= 6)
     * @param player a player of the game Connect Four
     */
 	   
    public void dropPiece(int col, Player player) {
	if (col < 0) {
	    throw new IllegalArgumentException("Value of column must be "+
					      "greater than or equal to "+
                                               "zero and less than 7.");
	}
	for (int row = 5; row >= 0; row--) {
	    if (gameboard_[row][col] == null) {
		// space in gameboard is empty... add player to space
		gameboard_[row][col] = player;
		break;
	    }
	    else{
		continue;
	    }
	}
    }
 	   
    /**
     * Draws an indicator marking specified column as the currently
     * selected column. 
     *
     * @param col the column number of the gameboard (0 =< col <= 6)
     * @param player a player of the game Connect Four
     */
 	   
    public void highlightColumn(int col, Player player) {
	if (col < 0 || col > 6) {
	    throw new IllegalArgumentException("Value of column must be "+
					       "greater than or equal to"+
					       " zero and less than 7.");
	}
	Paint.setColor(player.getColor());
	Paint.fillOval((col*100),2,95,95);
    }
 	   
    /**
     * Determines if the game of Connect Four is over or not.
     *
     * @return true if the game is over (win or tie) and false otherwise
     */
 	   
    public boolean gameOver() {
 	   	   
	Player winner = this.getWinner(); // gets winner of game
	                                  // (null if no player won)
 	   	   
	if (winner != null) {
	    // player was won game ... game is over
	    // state winner of game
	    Paint.setColor(Color.BLACK);
	    Paint.setFont("SansSerif", Font.BOLD, 40);
	    Paint.drawString(winner.getName()+" wins!",50,40);
	    // state winner's record
	    int wins; // number of wins by winner
	    wins = winner.getNumWins();
	    int losses; // number of losses by winner
	    losses = (this.getNumGames())-(wins);
	    int ties; // number of ties by winner
	    ties = this.getNumTies();
	    Paint.drawString("record: "+wins+"-"+losses+"-"+ties,50,90);
	    return true;
	}
	else if (this.isTie() == true) {
	    // there is a tie ... game is over
	    this.increaseNumTies();
	    Paint.setColor(Color.BLACK);
	    Paint.setFont("SansSerif", Font.BOLD, 40);
	    Paint.drawString("Game is a tie!",50,40);
	    return true;
	}
	else{
	    // no player has won and there is not a tie ... continue game
	    return false;
	}
    }
 	   
    /**
     * Determine if the game has ended in a tie or not.
     *
     * @return true if the game has ended in a tie and false otherwise
     */
 	   
    public boolean isTie() {
	for (int col = 0; col < 7; col++) {
	    if (gameboard_[0][col] == null) {
		// there is an empty space
		// gameboard is not full ... no tie
		return false;
	    }
	}
	// all spots are occupied by a player
	// gameboard is full ... there is a tie
	return true; 
    }
 	   
    /**
     * Determine if there is a win in a row.
     *
     * @param row the row that is to be checked (0 <= row <= 5)
     * @return the player that won (null if no player won)
     */
 	   
    private Player winRow(int row) {
	if (row < 0 || row > 5) {
	    throw new IllegalArgumentException("Value of row cannot be less"+
					       " than 0 or greater than 5.");
	}
	int chipCount = 1; // keeps track of how many chips one player has
	                   // consecutively in row
 	   	   
	for (int col = 0; col < 6; col++) {
	    if (gameboard_[row][col] == gameboard_[row][col+1]) {
		// player has two chips consecutively in row
		chipCount++;
		if (chipCount == 4) {
		    // player has four chips consecutively in row
		    // player won
		    return gameboard_[row][col];
		}
	    }
	    else {
		// player does not have two consecutively chips in row
		// reset counter
		chipCount = 1;
	    }
	}
	// no player has four chips consecutively in row 
	// no player won
	return null;   
    }
 	   
    /**
     * Determine if there is a win in a column.
     *
     * @param col the column that is to be checked (0 <= col <= 6)
     * @return the player that won (null if no player won)
     */
 	  
    private Player winCol(int col) {
	if (col < 0 || col > 6) {
	    throw new IllegalArgumentException("Value of row cannot be less"+
					       " than 0 or greater than 6.");
	}
	int chipCount = 1; // keeps track of how many chips one player has
	                   // consecutively in column
 	  	  
	for (int row = 5; row >= 1; row--) {
	    if (gameboard_[row][col] == gameboard_[row-1][col]) {
		// player has two chips consecutively in column 
		chipCount++;
		if (chipCount == 4) {
		    // player has four chips consecutively in column
		    // player won
		    return gameboard_[row][col];
		}
	    }
	    else {
		// player does not have two chips consecutively in column
		// reset counter
		chipCount = 1;
	    }
	}
	// no player has four chips consecutively in column
	// no player won
	return null;
    }
 	  
    /**
     * Determine if there is a win in a right diagonal.
     *
     * @param rowStart the starting row of right diagonal (0 <= rowStart <= 5)
     * @param colStart the starting column of right diagonal (0 <= colStart <=6)
     * @return the player that won (null if no player won)
     */
 	  
    private Player winRightDiag(int rowStart, int colStart) {
	if (rowStart < 0 || rowStart > 5) {
	    throw new IllegalArgumentException("Value of row cannot be less"+
					       " than 0 or greater than 5.");
	}
	if (colStart < 0 || colStart > 6) {
	    throw new IllegalArgumentException("Value of column cannot be less"+
					       " than 0 or greater than 6.");
	}
 	  	  
	int chipCount = 1; // keeps track of how many chips one player has
	                   // consecutively in right diagonal
 	  	  
	for (int row = rowStart,col = colStart; row > 0 && col < 6;
	     row--,col++) {
	    if (gameboard_[row][col] == gameboard_[row-1][col+1]) {
		// player has two chips consecutively in right diagonal 
		chipCount++;
		if (chipCount == 4) {
		    // player has four chips consecutively in right diagonal
		    // player won
		    return gameboard_[row][col];
		}
	    }
	    else {
		// player does not have two chips consecutively in right diag
		// reset counter
		chipCount = 1;
	    }
	}
	// no player has four chips consecutively in right diagonal
	// no player won
	return null;
    }
 	  
    /**
     * Determine if there is a win in a left diagonal.
     *
     * @param rowStart the starting row of left diagonal (0 <= rowStart <= 5)
     * @param colStart the starting column of left diagonal (0 <= colStart <=6)
     * @return the player that won (null if no player won)
     */
 	  
    private Player winLeftDiag(int rowStart, int colStart) {
	if (rowStart < 0 || rowStart > 5) {
	    throw new IllegalArgumentException("Value of row cannot be less"+
					       " than 0 or greater than 5.");
	}
	if (colStart < 0 || colStart > 6) {
	    throw new IllegalArgumentException("Value of column cannot be less"+
					       " than 0 or greater than 6.");
	}
 	  	  
	int chipCount = 1; // keeps track of how many chips one player has
	                   // consecutively in right diagonal
 	  	  
	for (int row = rowStart,col = colStart; row > 0 && col > 0;
	     row--,col--) {
	    if (gameboard_[row][col] == gameboard_[row-1][col-1]) {
		// player has two chips consecutively in left diagonal 
		chipCount++;
		if (chipCount == 4) {
		    // player has four chips consecutively in left diagonal
		    // player won
		    return gameboard_[row][col];
		}
	    }
	    else {
		// player does not have two chips consecutively in left diagonal
		// reset counter
		chipCount = 1;
	    }
	}
	// no player has four chips consecutively in left diagonal
	// no player won
	return null;
    }
 	   
    /**
     * Get the winner of the game of Connect Four if there is one.
     *
     * @return the winning player if there is one and null otherwise
     */
 	   
    public Player getWinner() {
 	   	   	   
	Player playerWin; // player that won game
 	   	   	   
	// determine if there is a win in any of the rows
	for (int row = 0; row < 6; row++) {
	    playerWin = winRow(row);
	    if (playerWin != null) {
		// player won in a row
		playerWin.increaseWins();
		return playerWin;
	    }
	}
 	   	   	   
	// determine if there is a win in any of the columns
	for (int col = 0; col < 7; col++) {
	    playerWin = winCol(col);
	    if (playerWin != null) {
		// player won in a column 
		playerWin.increaseWins();
		return playerWin;
	    }
	}
 	   	   	   
	// determine if there is a win in a right diagonal
	// test all right diagonals when starting column is 0
	for (int row = 5; row > 0; row--) {
	    playerWin = winRightDiag(row,0);
	    if (playerWin != null) {
		// player won in right diagonal
		playerWin.increaseWins();
		return playerWin;
	    }
	}
 	   	   	   
	// test all right diagonals when starting row is 5
	for (int col = 1; col < 6; col++) {
	    playerWin = winRightDiag(5,col);
	    if (playerWin != null) {
		// player won in right diagonal
		playerWin.increaseWins();
		return playerWin;
	    }
	}
 	   	   	   
	// determine if there is a win in a left diagonal
	// test all left diagonals when starting column is 6
	for (int row = 5; row > 0; row--) {
	    playerWin = winLeftDiag(row,6);
	    if (playerWin != null) {
		// player won in left diagonal
		playerWin.increaseWins();
		return playerWin;
	    }
	}
 	   	   	   
	// test all left diagonals when starting row is 5
	for (int col = 5; col > 0; col--) {
	    playerWin = winLeftDiag(5,col);
	    if (playerWin != null) {
		// player won in left diagonal
		playerWin.increaseWins();
		return playerWin;
	    }
	}
 	   	   	   
	// there were no wins throughout whole board
	return null; 
 	   	   
    }
 
}
