/* 
 * Original Creators: James Crowley and Arthur Liao
 * Class: Advanced CS AB Data Structures and Algorithms
 * Period: 6
 * School: Oakton High School
 * Date: 6/7/2017
 */
import java.awt.*;
 import java.awt.event.*;
 import java.util.Scanner;
 import java.util.*;
 import javax.swing.*;

public class CheckerBoardGUI extends JPanel{
	
	private JButton[][] buttonBoard;//main GUI board
	private JButton reset,start/*,rand*/;
	static CheckerBoard board;//int[][] board
	private boolean checker = false;//this instance variable is used to check whether or not a click is meant to move a piece to a location or meant to meant to indicate that this piece was chosen to be moved 
	private Coordinates temp;//this instance variable is used to hold the coordinates of the piece that was selected to be moved
	private ArrayList<Move> legalMoves;//ArrayList of all possible moves for a piece at its x and y value

	private static StretchIcon redIcon = new StretchIcon("C:\\Users\\red.png");//icons for the pieces 
	private static StretchIcon blackIcon = new StretchIcon("C:\\Users\\black.jpg");
	private static StretchIcon redKingIcon = new StretchIcon("C:\\Users\\redKing.png");
	private static StretchIcon blackKingIcon = new StretchIcon("C:\\Users\\blackKing.jpg");
	
   public CheckerBoardGUI(){
		
      setLayout(new BorderLayout());

		reset = new JButton("Winner is N/A");
		start = new JButton("Start");     
      //rand = new JButton("Random Move");
		reset.setEnabled(false);
		reset.addActionListener(new resetListener());
	
      //board = new CheckerBoard();//Create board
		JPanel south = new JPanel();
		south.setLayout(new FlowLayout());
		add(south, BorderLayout.SOUTH);
		south.add(start);
		south.add(reset);
		start.addActionListener(new StartListener());
		JPanel center = new JPanel();
		center.setLayout(new GridLayout(8,8));
		add(center, BorderLayout.CENTER);
		buttonBoard = new JButton[8][8];
		for(int r = 0; r < 8; r ++){//sets up the initial board with the appropriate colors
			for(int c = 0; c < 8; c ++){
				buttonBoard[r][c] = new JButton();
				if(r % 2 == 0){
					if(c % 2 == 1){
						buttonBoard[r][c].setBackground(Color.red);
						buttonBoard[r][c].setEnabled(false);
					}
					else{
						buttonBoard[r][c].setBackground(Color.white);
						buttonBoard[r][c].setEnabled(false);
					}
				}
				else{
					if(c % 2 == 1){
						buttonBoard[r][c].setBackground(Color.white);
						buttonBoard[r][c].setEnabled(false);
					}
					else{
						buttonBoard[r][c].setBackground(Color.red);
						buttonBoard[r][c].setEnabled(false);
					}
				}
				center.add(buttonBoard[r][c]);
			}
		}
	}//end constructor	

	private class StartListener implements ActionListener{

		public void actionPerformed(ActionEvent e){
			
		start.setEnabled(false);	
		reset.setEnabled(true);

        board = new CheckerBoard(); //instantiates the int[][] board
			for(int r = 0; r < 8; r ++){
				for(int c = 0; c < 8; c ++){//populates the GUI board with pieces
					if(buttonBoard[r][c].getBackground() == Color.white){
						buttonBoard[r][c].setEnabled(true);
						buttonBoard[r][c].addActionListener(new MainListener(r, c));	
                  buttonBoard[r][c].setIcon(null);
					}
					if(r == 0 ||  r == 2){
						if(c % 2 == 0){
						    buttonBoard[r][c].setIcon(blackIcon);
						}
					}
					else if(r == 1){
						if(c % 2 == 1){
						    buttonBoard[r][c].setIcon(blackIcon);
						}
					}
					else if(r == 5 || r == 7){
						if(c % 2 == 1){
						    buttonBoard[r][c].setIcon(redIcon);
						}
					}
					else if(r == 6){
						if(c % 2 == 0){
						    buttonBoard[r][c].setIcon(redIcon);
						}
					}
				}
			}
		}
	}//end StartListener
	
	private class resetListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			reset.setText("Winner is N/A");
			reset.setEnabled(true);
			start.setEnabled(true);
			
			for(int r = 0; r < 8; r ++){
				for(int c = 0; c < 8; c ++){
					buttonBoard[r][c].setIcon(null);
					if(r % 2 == 0){
						if(c % 2 == 1){
							buttonBoard[r][c].setBackground(Color.red);
							buttonBoard[r][c].setEnabled(false);
						}
						else{
							buttonBoard[r][c].setBackground(Color.white);
							buttonBoard[r][c].setEnabled(false);
						}
					}
					else{
						if(c % 2 == 1){
							buttonBoard[r][c].setBackground(Color.white);
							buttonBoard[r][c].setEnabled(false);
						}
						else{
							buttonBoard[r][c].setBackground(Color.red);
							buttonBoard[r][c].setEnabled(false);
						}
					}
				}
			}
		}
	}
	private class MainListener implements ActionListener{
		private int r, c;
		
		public MainListener(int r, int c)
		{
			this.r = r;
			this.c = c;
		}
		
		public void actionPerformed(ActionEvent e){

			if(checker == false){//if checker is false, this means that the selected piece is chosen to be moved
				checker = true;					
				legalMoves = board.legalMoves(r, c);
				if(legalMoves.size() != 0){
					temp = new Coordinates(r, c);

					for(int i = 0; i < legalMoves.size();i++){
						int targX = legalMoves.get(i).getTargetX();
						int targY = legalMoves.get(i).getTargetY();
						buttonBoard[targX][targY].setBackground(Color.blue);
					}
				}
				else{
					checker = false;
				}
			}
			else{//if checker is true, this means that the previously selected piece located at temp will be moved to the current selected piece position
				if(buttonBoard[r][c].getBackground().equals(Color.blue)){
					
					checker = false;
					
					int initX = temp.getX();
					int initY = temp.getY();
				    for(int i = 0; i < board.legalMoves(initX, initY).size(); i++){
				    	if(board.legalMoves(initX, initY).get(i).getTargetX() == r && board.legalMoves(initX, initY).get(i).getTargetY() == c)
				    		board.move(board.legalMoves(initX, initY).get(i));//this if statement is to ensure that the correct move is selected from the arraylist of legalmoves
				    }
					StretchIcon tempIcon = (StretchIcon)buttonBoard[initX][initY].getIcon();
					buttonBoard[initX][initY].setIcon(null);
					buttonBoard[r][c].setIcon(tempIcon);
					
					for(int i = 0; i < legalMoves.size(); i++){
						int targX = legalMoves.get(i).getTargetX();
						int targY = legalMoves.get(i).getTargetY();
						buttonBoard[targX][targY].setBackground(Color.white);
					}
				}
			}
			refreshBoard();
         if(board.checkWin() == true)//checks if anyone has won. This is determined by counting the number of red pieces and number of black pieces
         {
            if(board.redNum == 0)
            {
               reset.setText("Winner is Red");
               reset.setEnabled(true);
               for(int r = 0; r < 8; r ++){
      				for(int c = 0; c < 8; c ++){
      						buttonBoard[r][c].setEnabled(false);
      						buttonBoard[r][c].setIcon(null);
                  }
               }
            }
            else
            {
               reset.setText("Winner is Black");
               reset.setEnabled(true);
               for(int r = 0; r < 8; r ++){
      				for(int c = 0; c < 8; c ++){
      						buttonBoard[r][c].setEnabled(false);
      						buttonBoard[r][c].setIcon(null);
                  }
               }
            }
         }            
		}
	}//end MainListener
	
	private void refreshBoard(){//refreshes the GUI button board based off of the contents of the int[][] in the CheckerBoard object
		for(int r = 0; r < board.board.length; r++){
			for(int c = 0; c < board.board[r].length; c++){
				if(board.board[r][c] == 1){//RED
					buttonBoard[r][c].setIcon(redIcon);
				}
				else if(board.board[r][c] == 2){//BLACK
					buttonBoard[r][c].setIcon(blackIcon);
				}
				else if(board.board[r][c] == 0){//EMPTY
					buttonBoard[r][c].setIcon(null);
				}
				else if(board.board[r][c] == 3){//RED KING
					buttonBoard[r][c].setIcon(redKingIcon);
				}
				else if(board.board[r][c] == 4){//BLACK KING
					buttonBoard[r][c].setIcon(blackKingIcon);
				}
			}
		}
	}
		   public static void main(String args[]){
			  JFrame frame = new JFrame("Checkers");
			  frame.setSize(850, 800);
			  frame.setLocation(500, 100);
			  frame.setContentPane(new CheckerBoardGUI());
			  frame.setVisible(true);
			  frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		     }
}
