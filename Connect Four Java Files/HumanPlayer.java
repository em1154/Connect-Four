import java.awt.*;
import javax.swing.*;

/**
 * A human player in the game Connect Four.
 *
 * @author Bizzy Moore
 */
 
public class HumanPlayer extends Player {
 	 
    /** 
     * A human player of the game Connect Four.
     *
     * @param name the name of the human player
     * @param color the color of the human player
     */
 	  
    public HumanPlayer(String name, Color color) {
	super(name,color);
    }
 	  
    /**
     * Determines where player wants to drop disc for their next move.
     *
     * @param board the Connect Four gameboard
     * @return the column number where the player wants to drop the disc
     * (column must not be full and be a legal column on the board)
     */ 
 	  
    public int getNextMove(GameBoard board) {
 	  	  
	int col = 0; // current column that player is in
 	  	  
	for ( ; ; ) {
 	  	  	  
	    // paint cursor
	    Paint.clear();
	    board.paint();
	    board.highlightColumn(col,this);
 	  	  	  
	    // get player's key press
	    int arrow = Paint.getArrow(); // player's key press
	    if (arrow == Paint.RIGHT) {
		// move cursor to the right
		if (col < 6) {
		    col++;
		    // paint cursor
		    Paint.clear();
		    board.paint();
		    board.highlightColumn(col,this); 
		}
	    }
	    else if (arrow == Paint.LEFT) {
		// move cursor to the left
		if (col > 0) {
		    col--;
		    // paint cursor
		    Paint.clear();
		    board.paint();
		    board.highlightColumn(col,this);
		}
	    }
	    else if (arrow == Paint.UP || arrow == Paint.DOWN) {
		// do not move cursor
		continue;
	    }
	    else if (arrow == Paint.OTHER) {
		// user selected column
		if (board.isFull(col) == false){
		    return col;
		}
	    } 
	}
    }
}
