import java.util.*;
public class CheckerBoard {
	private int[][] board;
	private int redNum, blackNum;
	
	public static final int 
			EMPTY = 0,
			RED = 1,
			BLACK = 2,
			RED_KING = 3,
			BLACK_KING = 4;
	public CheckerBoard()
	{
		board = new int[8][8];
		redNum = 12;
		blackNum = 12;
		setUpBoard();
	}
	
	private void setUpBoard()
	{
	      for (int row = 0; row < 8; row++) {
	          for (int col = 0; col < 8; col++) {
	             if ( row % 2 == col % 2 ) {
	                if (row < 3)
	                   board[row][col] = BLACK;
	                else if (row > 4)
	                   board[row][col] = RED;
	                else
	                   board[row][col] = EMPTY;
	             }
	             else {
	                board[row][col] = EMPTY;
	             }
	          }
	       }
	}
	
	public boolean move(Move validMove)
	{
		//ArrayList legalMoves = legalMoves(validMove.getInitX(), validMove.getInitY());
		//if(legalMoves.contains(validMove))
		//{
			ArrayList<Coordinates> jumpedPieces = validMove.getJumped();
		    
			int initX = validMove.getInitX();
			int initY = validMove.getInitY();
			int targX = validMove.getTargetX();
			int targY = validMove.getTargetY();

			
			if(jumpedPieces.size() != 0)//regular move
			{
				for(int i = 0; i < jumpedPieces.size(); i++)
				{
					int removeX = jumpedPieces.get(i).getX();
					int removeY = jumpedPieces.get(i).getY();
					board[removeX][removeY] = EMPTY;
				}
			}
			
			int value = board[initX][initY];
			board[initX][initY] = 0;
			board[targX][targY] = value;
				
			
			
		return checkWin();

	}
    
	public boolean checkWin()
	{
		if(redNum == 0)
			return true;
		else if(blackNum == 0)
			return true;
		else	
			return false;
	}
	
	private boolean canMove(int initX, int initY, int targetX, int targetY) {
        // This is called by the getLegalMoves() method to determine whether
        // the player can legally move from (initX,initY) to (targetX,targetY).  It is
        // assumed that (initX,targetX) contains one of the player's pieces and
        // that (targetX,targetY) is a neighboring square.
	 int player = board[initX][initY];
 
     if (targetX < 0 || targetX >= 8 || targetY < 0 || targetY >= 8)
        return false;  // (targetX,targetY) is off the board.
        
     if (board[targetX][targetY] != EMPTY)
        return false;  // (targetX,targetY) already contains a piece.

     if (player == RED) {
        if (board[initX][initY] == RED && targetX > initX)
            return false;  // Regualr red piece can only move down.
         return true;  // The move is legal.
     }
     else {
        if (board[initX][initY] == BLACK && targetX < initX)
            return false;  // Regular black piece can only move up.
         return true;  // The move is legal.
     }
     
  }  // end canMove()

  public PriorityQueue<Move> legalMoves(int initX, int initY)
  {
	  int player = board[initX][initY];
	  Vector<Move> moves = new Vector();
	  

      int row = initX;
      int col = initY;
      if(player == BLACK)
      {
    	  if (canMove(row,col,row+1,col+1))
    		  moves.addElement(new Move(row,col,row+1,col+1));
          if (canMove(row,col,row+1,col-1))
              moves.addElement(new Move(row,col,row+1,col-1));
      }
      else if(player == RED)
      {
	      if (canMove(row,col,row-1,col+1))
	          moves.addElement(new Move(row,col,row-1,col+1));
	      if (canMove(row,col,row-1,col-1))
	          moves.addElement(new Move(row,col,row-1,col-1));
      }

	  
	  ArrayList<Move> list = new ArrayList<Move>(moves);
	  
	  PriorityQueue<Move> pq = new PriorityQueue<Move>();
	  pq.addAll(list);
	  pq.addAll(jumpMove(initX, initY));
	  
	  return pq;

  }
  
  private ArrayList<Move> jumpMove(int x, int y)
  {
	  ArrayList<Move> returnedMoves = new ArrayList<Move>();
	  return jumpMove(x, y, new ArrayList<Coordinates>(), returnedMoves);
  }
  
  private ArrayList<Move> jumpMove(int row, int col, ArrayList<Coordinates> jumpedPieces, ArrayList<Move> moves)
  {
	  if(!(canMove(row, col, row + 2, col +2) && canMove(row, col, row + 2, col -2) && canMove(row, col, row + 2, col - 2) && canMove(row, col, row - 2, col -2)))
		  return moves;
	  int player = board[row][col];
	  if(player == BLACK)
	  {
		  if(canMove(row, col, row + 2, col +2) && board[row+1][col+1] == RED)
		  {
        	  jumpedPieces.add(new Coordinates(row+1,col+1));
			  moves.add(new Move(row, col, row+2, col+2, jumpedPieces));
			  return jumpMove(row+2, col+2, jumpedPieces, moves);
			  //jumpMove(row+2, col-2, jumpedPieces, moves);
		  }
          if(canMove(row, col, row + 2, col -2) && board[row+1][col-1] == RED)
		  {
        	  jumpedPieces.add(new Coordinates(row+1,col-1));
			  moves.add(new Move(row, col, row+2, col-2, jumpedPieces));
			  //jumpMove(row+2, col+2, jumpedPieces, moves);
			  return jumpMove(row+2, col-2, jumpedPieces, moves);
		  }
	  }
	  else if(player == RED)
	  {
          if(canMove(row, col, row - 2, col - 2) && board[row-1][col-1] == BLACK)
          {
        	  jumpedPieces.add(new Coordinates(row-1,col-1));
			  moves.add(new Move(row, col, row-2, col-2, jumpedPieces));
			  return jumpMove(row-2, col-2, jumpedPieces, moves);
			  //jumpMove(row-2, col+2, jumpedPieces, moves);
          }
          if(canMove(row, col, row - 2, col + 2) && board[row-1][col+1] == BLACK)
          {
        	  jumpedPieces.add(new Coordinates(row-1,col+1));
			  moves.add(new Move(row, col, row-2, col+2, jumpedPieces));
			  return jumpMove(row-2, col+2, jumpedPieces, moves);
			  //jumpMove(row-2, col-2, jumpedPieces, moves);
          }
	  }
	  return moves;
  }

  public String toString()
  {
     String printout = "";
     for(int y = 0; y < 8; y++)
     {
        for(int x = 0; x < 8; x++)
        {
           printout += "[" + board[y][x] + "]";
        }
        printout += "\n";
     }
     return printout;
  }
}

class Move implements Comparable<Move>
{
	private int initialX, initialY, targetX, targetY;
    ArrayList<Coordinates> jumped;
	public Move(int initX, int initY, int targX, int targY)
	{
		initialX = initX;
		initialY = initY;
		targetX = targX;
		targetY = targY;
        jumped = new ArrayList<Coordinates>();
	}	
   public Move(int initX, int initY, int targX, int targY, ArrayList<Coordinates> jumps)
   {
        initialX = initX;
		initialY = initY;
		targetX = targX;
		targetY = targY;
        jumped = jumps;
   }
   
	public int getInitX()
	{
		return initialX;
	}
	
	public int getInitY()
	{
		return initialY;
	}
	
	public int getTargetX()
	{
		return targetX;
	}
	
	public int getTargetY()
	{
		return targetY;
	}
	
	public int compareTo(Move other)
	{
		if(jumped.size()  == other.jumped.size())
			return 0;
		else if(jumped.size() < other.jumped.size())
			return -1;
		else
			return 1;
	}
   
   public ArrayList<Coordinates> getJumped()
   {
      return jumped;
   }
   
   public String toString()
   {
      return "(" + initialX + ", " + initialY + ") -> (" + targetX + ", " + targetY + ")" + "J(" + jumped + ")";
   }
}         

class Coordinates
{
	private int x, y;
	public Coordinates(int initX, int initY)
	{
		x = initX;
		y = initY;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
   
   public String toString()
   {
      return "(" + x + ", " + y + ")";
   }
}

