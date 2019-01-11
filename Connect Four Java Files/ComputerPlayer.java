import java.awt.*;
import javax.swing.*;

/**
 * A computer player in the game Connect Four.
 *
 * @author Bizzy Moore
 */
 
public class ComputerPlayer extends Player {
 	 
    /** 
     * A computer player of the game Connect Four.
     *
     * @param name the name of the computer player
     * @param color the color of the computer player
     */
 	  
    public ComputerPlayer(String name, Color color) {
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
	for ( ; ; ) {
	    int randomCol = (int)(Math.random()*7); // random column picked by
	                                            // computer
	    if ( board.isFull(randomCol) == false ) {
		return randomCol;
	    }
	    else {
		continue;
	    }
	}
    }
}
