   import java.awt.*;
   import java.awt.event.*;
import java.util.Scanner;

import javax.swing.*;
public class CheckerBoardGUI extends JPanel{
	private JButton[][] buttonBoard;
	private JButton reset,start;
	   static boolean blackIsPlayer;
	   static boolean redIsPlayer;
	   static boolean isRedsMove;
	   static CheckerBoard board;
	public CheckerBoardGUI(){
		setLayout(new BorderLayout());
		reset = new JButton("Reset");
		start = new JButton("Start");
		reset.setEnabled(false);
		
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
					if(c % 2 == 0){
						buttonBoard[r][c].setBackground(Color.red);
						buttonBoard[r][c].setEnabled(false);
					}
					else{
						buttonBoard[r][c].setBackground(Color.white);
						buttonBoard[r][c].setEnabled(false);
					}
				}
				else{
					if(c % 2 == 0){
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
			for(int r = 0; r < 8; r ++){
				for(int c = 0; c < 8; c ++){
					if(buttonBoard[r][c].getBackground() == Color.white){
						buttonBoard[r][c].setEnabled(true);
						buttonBoard[r][c].addActionListener(new MainListener(r, c));	
					}
					
					if(r == 0 ||  r == 2){
						if(c % 2 == 1){
							StretchIcon icon = new StretchIcon("C:\\Users\\Liao_Family\\Arthur\\Eclipse\\workspace\\Final Project\\src\\red.png");
						    buttonBoard[r][c].setIcon(icon);
						}
					}
					else if(r == 1){
						if(c % 2 == 0){
							StretchIcon icon = new StretchIcon("C:\\Users\\Liao_Family\\Arthur\\Eclipse\\workspace\\Final Project\\src\\red.png");
						    buttonBoard[r][c].setIcon(icon);
						}
					}
					else if(r == 5 || r == 7){
						if(c % 2 == 0){
							StretchIcon icon = new StretchIcon("C:\\Users\\black.jpg");
						    buttonBoard[r][c].setIcon(icon);
						}
							
					}
					else if(r == 6){
						if(c % 2 == 1){
							StretchIcon icon = new StretchIcon("C:\\Users\\red.png");
						    buttonBoard[r][c].setIcon(icon);
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
			//if()
		}
	}//end MainListener
		   public static void main(String args[]){
		      
			      //Find who won
			      JFrame frame = new JFrame("Checkers");
			      frame.setSize(450, 400);
			      frame.setLocation(200, 100);
			      frame.setContentPane(new CheckerBoardGUI());
			      frame.setVisible(true);
			      frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
			   
			  Scanner console = new Scanner(System.in);
		      isRedsMove = true;
		      System.out.println("Is black a real player? type y/n");//Determine who is real and who will be computer controlled
		      if(console.next().equals("y")){
		         blackIsPlayer = true;
		      }
		      else{
		         blackIsPlayer = false;
		      }
		      System.out.println("Is red a real player? type y/n");
		      if(console.next().equals("y")){
		         redIsPlayer = true;
		      }
		      else{
		         redIsPlayer = false;
		      }
		     
		      board = new CheckerBoard();//Create board
		     
		     
		      while(board.checkWin() == false){//Play Game
		         int tempX = 0;
		         int tempY = 1;
		         if(isRedsMove == true){
		            while(board.legalMoves(tempX, tempY).size() == 0)
		            {
		            	System.out.println("Red Player, type x coordinate of the piece you want to move");
			            tempX = console.nextInt();
			            System.out.println("Red Player, type y coordinate of the piece you want to move");
			            tempY = console.nextInt();
			            System.out.println("Moves available to you are as follows");
			            System.out.println(board.legalMoves(tempX, tempY));////////////////////////////////////////////////////////////////Insert array of moves here
		            }
		            System.out.println("Red Player, type number of move you want to make");
		            board.move(board.legalMoves(tempX, tempY).get(console.nextInt()));//poll());
		         }
		         else{
		            while(board.legalMoves(tempX, tempY).size() == 0)
		            {
		            	System.out.println("Black Player, type x coordinate of the piece you want to move");
			            tempX = console.nextInt();
			            System.out.println("Black Player, type y coordinate of the piece you want to move");
			            tempY = console.nextInt();
			            System.out.println("Moves available to you are as follows");
			            System.out.println(board.legalMoves(tempX, tempY));////////////////////////////////////////////////////////////////Insert array of moves here
		            }
		            System.out.println("Black Player, type number of the move you want to make");
		            board.move(board.legalMoves(tempX, tempY).get(console.nextInt()));//poll());
		         }
		         System.out.println(board);
		         if(isRedsMove == true)
		            isRedsMove = false;
		         else
		            isRedsMove = true;
		      }
		      

		      
		      
		      
	}
}

