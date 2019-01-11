import java.awt.*;
import javax.swing.*;

/**
 * A player in the game Connect Four.
 * 
 * @author Bizzy Moore
 */
 
public abstract class Player {
 	 
    protected String name_; // name of player
    protected Color color_; // color of player
    protected int numWins_; // number of player wins 
    protected int numLosses_; // number of player losses 
 	 
    /**
     * A player of the game Connect Four.
     *
     * @param name the name of the player
     * @param color the color of the player
     */
 	  
    protected Player(String name, Color color) {
 	  	  
	name_ = name; 
	color_ = color;
	numWins_ = 0; 
	numLosses_ = 0;
    }
 	 
    /**
     * Get the color of the player.
     *
     * @return the color of the player
     */
 	  
    public Color getColor() {
	return color_;
    }
 	 
    /**
     * Get the name of the player.
     *
     * @return the name of the player
     */
 	  
    public String getName() {
	return name_;
    }
 	 
    /**
     * Get the number of wins by player.
     *
     * @return the number of wins by the player
     */
 	  
    public int getNumWins() {
	return numWins_;
    }
 	  
    /**
     * Get the number of losses by player.
     * 
     * @return the number of losses by the player
     */
 	  
    public int getNumLosses() {
	return numLosses_;
    }
 	 
    /**
     * Increase number of wins of player by one.
     */
 	  
    public void increaseWins() {
	numWins_++;
    }
 	  
    /** 
     * Increase number of losses of player by one.
     */
 	   
    public void increaseLosses() {
	numLosses_++;
    }
 	  
    /**
     * Determines where player wants to drop disc for their next move.
     *
     * @param board the Connect Four gameboard
     * @return the column number where the player wants to drop the disc
     * (column must not be full and be a legal column on the board)
     */ 
 	  
    public abstract int getNextMove(GameBoard board);
 	  
}
