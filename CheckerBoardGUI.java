import java.awt.*;
 import java.awt.event.*;
 import java.util.Scanner;
 import java.util.*;
 import javax.swing.*;

public class CheckerBoardGUI extends JPanel{

	private JButton[][] buttonBoard;
	private JButton reset,start/*,rand*/;
	static CheckerBoard board;
	private boolean checker = false;
	private Coordinates temp;
	private ArrayList<Move> legalMoves;

	private static StretchIcon redIcon = new StretchIcon("C:\\Users\\red.png");
	private static StretchIcon blackIcon = new StretchIcon("C:\\Users\\black.jpg");
	private static StretchIcon redKingIcon = new StretchIcon("C:\\Users\\redKing.png");
	private static StretchIcon blackKingIcon = new StretchIcon("C:\\Users\\blackKing.jpg");
	
   public CheckerBoardGUI(){
		
      setLayout(new BorderLayout());

		reset = new JButton("Winner is N/A");
		start = new JButton("Start");     
      //rand = new JButton("Random Move");
		reset.setEnabled(false);
	
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
		for(int r = 0; r < 8; r ++){
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

        board = new CheckerBoard(); 
			for(int r = 0; r < 8; r ++){
				for(int c = 0; c < 8; c ++){
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
	
	private class MainListener implements ActionListener{
		private int r, c;
		
		public MainListener(int r, int c)
		{
			this.r = r;
			this.c = c;
		}
		
		public void actionPerformed(ActionEvent e){

			if(checker == false){
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
			else{
				if(buttonBoard[r][c].getBackground().equals(Color.blue)){
					
					checker = false;
					
					int initX = temp.getX();
					int initY = temp.getY();
				    for(int i = 0; i < board.legalMoves(initX, initY).size(); i++){
				    	if(board.legalMoves(initX, initY).get(i).getTargetX() == r && board.legalMoves(initX, initY).get(i).getTargetY() == c)
				    		board.move(board.legalMoves(initX, initY).get(i));
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
         if(board.checkWin() == true)
         {
            if(board.redNum == 0)
            {
               reset = new JButton("Winner is Black");
               for(int r = 0; r < 8; r ++){
      				for(int c = 0; c < 8; c ++){
      						buttonBoard[r][c].setEnabled(false);
                  }
               }
            }
            else
            {
               reset = new JButton("Winner is Red");
               for(int r = 0; r < 8; r ++){
      				for(int c = 0; c < 8; c ++){
      						buttonBoard[r][c].setEnabled(false);
                  }
               }
            }
         }            
		}
	}//end MainListener
	
	private void refreshBoard(){
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
	
	private void printBoard(){
		int[][] internalBoard = board.getBoard();
		for(int r = 0; r < internalBoard.length; r ++){
			for(int c = 0; c < internalBoard[0].length; c ++){
				System.out.print(internalBoard[r][c]);
			}
			System.out.println();
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
