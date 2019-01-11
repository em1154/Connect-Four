import java.awt.*;
import javax.swing.*;

/**
 * Plays the game Connect Four.
 *
 * @author Bizzy Moore
 */
 
public class ConnectFour {
 	 
    public static void main ( String[] args ) {
 	 	 
	// ask user if players are human or computer
	// if human ... ask user for player's name
 	 	 
	// player 1
	String player1Type = playerType(1); // type of player 1
	String player1Name = playerName(1,player1Type); // name of player 1

	// create player1
	Player player1; // player one
 	 	 
	if (player1Type.equalsIgnoreCase("h")) {
	    // player 1 is human
	    player1 = new HumanPlayer(player1Name,Color.YELLOW);
	}
	else {
	    // player 1 is computer
	    player1 = new ComputerPlayer(player1Name,Color.YELLOW);
	}
 	 	 
	// player 2
	String player2Type = playerType(2); // type of player 1
	String player2Name = playerName(2,player2Type); // name of player 2
 	 	 
	// create player 2
	Player player2; // player two
 	 	 
	if (player2Type.equalsIgnoreCase("h")) {
	    // player 2 is human
	    player2 = new HumanPlayer(player2Name,Color.RED);
	}
	else {
	    // player 2 is computer
	    player2 = new ComputerPlayer(player2Name,Color.RED);
	}
 	 	 
	// create drawing window
	Paint.buildWindow("Connect Four",0,0,700,700,Color.WHITE);
 	 	 
	// draw start screen
	Paint.setColor(Color.BLACK);
	Paint.fillRect(0,0,700,700);
	Paint.setColor(Color.YELLOW);
	Paint.fillOval(240,240,100,100);
	Paint.setFont("SansSerif", Font.BOLD, 20);
	Paint.drawString(player1Name,360,295);
	Paint.setColor(Color.RED);
	Paint.fillOval(240,360,100,100);
	Paint.drawString(player2Name,360,415);
	Paint.setColor(Color.WHITE);
	Paint.drawString("press any key to play...",20,650);
 	 	 
	// get user input to switch to gameboard screen
	int arrow = Paint.getArrow(); // user's key press
	if (arrow == Paint.RIGHT || arrow == Paint.LEFT ||
	    arrow == Paint.UP || arrow == Paint.DOWN || 
	    arrow == Paint.OTHER) {
	    // user pressed a key ... clear the gameboard
	    Paint.clear();
	}	 
 	 	 
	// create Connect Four gameboard
	GameBoard gameboard = new GameBoard();
	//gameboard.setPlayer(player2,5,2);
	//gameboard.setPlayer(player1,0,5);
 	 	 
	// draw gameboard
	gameboard.paint();
 	 	 
	//play Connect Four
 	 	 
	boolean keepPlaying = true; // false when user does not want to play
	                            // any more games

	for ( ; keepPlaying == true; ){
 	 	 	 
	    // play one game
	    // repeat until a player wins or the gameboard is full (tie)
 	 	 	
	    // update number of games played
	    gameboard.increaseNumGames();
 	 	 	
	    boolean gameOver = false; // true when game is over (win or tie)
	    for ( ; gameOver == false; ) {
 	 	 		
		// player 1 turn
		int movePlayer1 = player1.getNextMove(gameboard); // player1's
		                                                  // next move
		// add player 1 piece to gameboard
		gameboard.dropPiece(movePlayer1,player1);
 	 	 	    
		// player 2 turn
		int movePlayer2 = player2.getNextMove(gameboard); // player2's
		                                                  // next move
		// add player 2 piece to gameboard
		gameboard.dropPiece(movePlayer2,player2);
 	 	 	    
		// clear the gameboard display
		Paint.clear();
 	 	 	    
		// draw the updated gameboard
		gameboard.paint();
 	 	 	    
		// check to see if game is over
		gameOver = gameboard.gameOver();
	    }
 	 	 	
	    for ( ; ; ) {
 	 	 		
		// ask user if they want to play again
		System.out.print("Do you want to play another game? [y/n] ");
		String playAgain; // determines if user wants to play again
		playAgain = TextIO.getln();
 	 	 		
		if (playAgain.equalsIgnoreCase("y")) {
		    // user wants to play again
		    // reset the gameboard
		    gameboard.clear();
		    break;
		}
		else if (playAgain.equalsIgnoreCase("n")) {
		    // user does not want to play again
		    // print out number of wins for each player
		    System.out.println(player1Name+": "+player1.getNumWins()+
				       " wins");
		    System.out.println(player2Name+": "+player2.getNumWins()+
				       " wins");
		    keepPlaying = false;
		    break;
		}
		else {
		    // user input is illegal
		    System.out.println("Please enter 'y' or 'n'.");
		    continue;
		}
	    }
	} 	 
    }
 	 
    /** 
     * Asks user what kind of player they want for the game of Connect Four.
     *
     * @param i the ith player of the game (i >= 0)
     * @return the type of player (human or computer)  
     */
 	  
    private static String playerType(int i) {
	if (i < 0) {
	    throw new IllegalArgumentException("i cannot be less than zero."+
					       "You entered "+i+".");
	}
	for ( ; ; ) {
	    System.out.print("Is player "+i+" human or computer? "+
			     "[h/c] ");
	    String player = TextIO.getln(); // type of player user wants
	    if (player.equalsIgnoreCase("h")) {
		// user selected human player
		return player; 
	    }
	    else if (player.equalsIgnoreCase("c")) {
		// user selected computer player
		return player;
	    }
	    else {
		// user input is illegal
		System.out.println("Please enter 'h' or 'c'.");
		continue;
	    }
	}
    }
 	 
    /**
     * Assigns a player of the game of Connect Four a name. 
     * If player is human, then the user chooses the player's name.
     * If player is the computer, then the program assigns the player a name.
     * 
     * @param i the ith player of the game (i >= 0)
     * @param type the type of player
     * @return the name of the player
     */
 	 
    private static String playerName(int i, String type) {
	if (i < 0) {
	    throw new IllegalArgumentException("i cannot be less than zero."+
					       "You entered "+i+".");
	}
	if (type.equalsIgnoreCase("h")) {
	    // player is human ... user chooses player's name
	    System.out.print("Please enter players's name: ");
	    String nameHuman = TextIO.getln(); // name of human player
	    return nameHuman;
	}
	else if (type.equalsIgnoreCase("c")) {
	    // player is the computer ... program assigns player's name
	    String nameComputer = "Computer "+Integer.toString(i); // name of
	                                                      // computer player
	    return nameComputer;
	}
	else {
	    return null;
	}
    }
 	 	 
}
 
 
