import java.util.*;
public class CheckerBoard {
	public int[][] board;
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
	
	public int[][] getBoard(){
		return board;
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
/*
   public Move aIMove(boolean isRed)
   {
      Move move;
      ArrayList<Coordinates>pieces = new ArrayList<Coordinates>();
      if(isRed == true)
      {
         for(int x = 0; x < 8; x++)
         {
            for(int y = 0; y < 8; y++)
            {
               if(board[x][y] == RED || board[x][y] == RED_KING)
               {
                  pieces.add(new Coordinates(x,y));
               }
            }
         }
      }
      else
      {
         for(int x2 = 0; x2 < 8; x2++)
         {
            for(int y2 = 0; y2 < 8; y2++)
            {
               if(board[x2][y2] == BLACK || board[x2][y2] == BLACK_KING)
               {
                  pieces.add(new Coordinates(x2,y2));
               }
            }
         }
      }
      //int randomPiece = (pieces.size()+1)*Math.random();
      //move = legalMoves(pieces.get(randomPiece).getX(), pieces.get(randomPiece).getY()).get((legalMoves(pieces.get(randomPiece).getX(), pieces.get(randomPiece).getY()).size()+1)*Math.random());
      return move;
   }
*/
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
			
			if(jumpedPieces.size() != 0)//jump move
			{
				for(int i = 0; i < jumpedPieces.size(); i++)
				{
					int removeX = jumpedPieces.get(i).getX();
					int removeY = jumpedPieces.get(i).getY();
	               if(board[removeX][removeY] == RED || board[removeX][removeY] == RED_KING)
	               {
	                  blackNum--;
	               }
	               else if(board[removeX][removeY] == BLACK || board[removeX][removeY] == BLACK_KING)
	               {
	                  redNum--;
	               }
				   board[removeX][removeY] = EMPTY;
				}
			}
			int value = board[initX][initY];
			board[initX][initY] = 0;
			board[targX][targY] = value;
         if(targX == 7 && value == BLACK)//King
         {
            board[targX][targY] = BLACK_KING;
         }
         else if(targX == 0 && value == RED)//King
         {
            board[targX][targY] = RED_KING;
         }
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



  /*public PriorityQueue<Move> legalMoves(int initX, int initY)
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
  */
	public ArrayList<Move> legalMoves(int initX, int initY)
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
         else if(player == RED_KING || player == BLACK_KING)
	      {
		      if (canMove(row,col,row-1,col+1))
		          moves.addElement(new Move(row,col,row-1,col+1));
		      if (canMove(row,col,row-1,col-1))
		          moves.addElement(new Move(row,col,row-1,col-1));
            if (canMove(row,col,row+1,col+1))
	    		  moves.addElement(new Move(row,col,row+1,col+1));
	          if (canMove(row,col,row+1,col-1))
	              moves.addElement(new Move(row,col,row+1,col-1));
	      }
		  ArrayList<Move> list = new ArrayList<Move>(moves);
		  list.addAll(jumpMove(initX, initY));
		  return list;
	  }

  private ArrayList<Move> jumpMove(int x, int y)
  {
	  ArrayList<Move> returnedMoves = new ArrayList<Move>();
	  return jumpMove(x, y, x, y, new ArrayList<Coordinates>(), returnedMoves);
  }

  

  private ArrayList<Move> jumpMove(int row, int col, int initRow, int initCol, ArrayList<Coordinates> jumpedPieces, ArrayList<Move> moves)
  {
/*	  System.out.println("check1 jumpMove:"+initRow+":"+initCol);
	  
	  System.out.println("can move1:"+canMove(initRow, initCol, row + 2, col +2) );
	  System.out.println("can move2:"+canMove(initRow, initCol, row + 2, col -2) );
	  System.out.println("can move3:"+canMove(initRow, initCol, row - 2, col +2) );
	  System.out.println("can move4:"+canMove(initRow, initCol, row - 2, col -2) );
*/	  

	  if(!(canMove(initRow, initCol, row + 2, col +2) 
			  || canMove(initRow, initCol, row + 2, col -2) 
			  || canMove(initRow, initCol, row - 2, col + 2)
			  || canMove(initRow, initCol, row - 2, col -2) ))
		  return moves;	  
	  ArrayList <Coordinates> temp;
     Coordinates tempCor;
     int player = board[initRow][initCol];
     //System.out.println("check jumpmove:"+player);     
     if(player == BLACK)
	  {
        if(canMove(initRow, initCol, row + 2, col +2) && (board[row+1][col+1] == RED ||board[row+1][col+1] == RED_KING))
		  {
        	  jumpedPieces.add(tempCor = new Coordinates(row+1,col+1));
           temp = new ArrayList<Coordinates>();
           temp.addAll(jumpedPieces);
			  moves.add(new Move(initRow, initCol, row+2, col+2, temp));
           moves.addAll(jumpMove(row+2, col+2, initRow, initCol, jumpedPieces, moves));
		     jumpedPieces.remove(tempCor);
        }

        if(canMove(initRow, initCol, row + 2, col -2) && (board[row+1][col-1] == RED ||board[row+1][col-1] == RED_KING))
		  {
        	  jumpedPieces.add(tempCor = new Coordinates(row+1,col-1));
			  temp = new ArrayList<Coordinates>();
           temp.addAll(jumpedPieces);
           moves.add(new Move(initRow, initCol, row+2, col-2, temp));
           moves.addAll(jumpMove(row+2, col-2, initRow, initCol, jumpedPieces, moves));
		     jumpedPieces.remove(tempCor);
        }
	  }
	  else if(player == RED)
	  {
          //System.out.print("debug All");
		  if(canMove(initRow, initCol, row - 2, col - 2) && (board[row-1][col-1] == BLACK ||board[row-1][col-1] == BLACK_KING))
          {
        	  jumpedPieces.add(tempCor = new Coordinates(row-1,col-1));
           temp = new ArrayList<Coordinates>();
           temp.addAll(jumpedPieces);
           moves.add(new Move(initRow, initCol, row-2, col-2, temp));
          moves.addAll(jumpMove(row-2, col-2, initRow, initCol, jumpedPieces, moves));
          jumpedPieces.remove(tempCor);
          }
          //System.out.print("debug Right");
          if(canMove(initRow, initCol, row - 2, col + 2) && (board[row-1][col+1] == BLACK ||board[row-1][col+1] == BLACK_KING))
          {
        	  //System.out.println("debug");
        	  jumpedPieces.add(tempCor = new Coordinates(row-1,col+1));
			  temp = new ArrayList<Coordinates>();
           temp.addAll(jumpedPieces);
           moves.add(new Move(initRow, initCol, row-2, col+2, temp));
           moves.addAll(jumpMove(row-2, col+2, initRow, initCol, jumpedPieces, moves));
          jumpedPieces.remove(tempCor);
          }
	  }
     else if(player == RED_KING)
     {
          if(canMove(initRow, initCol, row - 2, col - 2) && (board[row-1][col-1] == BLACK ||board[row-1][col-1] == BLACK_KING) && !jumpedPieces.contains(tempCor = new Coordinates(row-1,col-1)))
          {
        	  jumpedPieces.add(tempCor);
           temp = new ArrayList<Coordinates>();
           temp.addAll(jumpedPieces);
           moves.add(new Move(initRow, initCol, row-2, col-2, temp));
          moves.addAll(jumpMove(row-2, col-2, initRow, initCol, jumpedPieces, moves));
          jumpedPieces.remove(tempCor);
          }

          if(canMove(initRow, initCol, row - 2, col + 2) && (board[row-1][col+1] == BLACK ||board[row-1][col+1] == BLACK_KING) && !jumpedPieces.contains(tempCor = new Coordinates(row-1,col+1)))
          {
        	  jumpedPieces.add(tempCor);
			  temp = new ArrayList<Coordinates>();
           temp.addAll(jumpedPieces);
           moves.add(new Move(initRow, initCol, row-2, col+2, temp));
          moves.addAll(jumpMove(row-2, col+2, initRow, initCol, jumpedPieces, moves));
          jumpedPieces.remove(tempCor);
          }
          if(canMove(initRow, initCol, row + 2, col +2) && (board[row+1][col+1] == BLACK ||board[row+1][col+1] == BLACK_KING) && !jumpedPieces.contains(tempCor = new Coordinates(row+1,col+1)))
		    {
        	  jumpedPieces.add(tempCor);
			  temp = new ArrayList<Coordinates>();
           temp.addAll(jumpedPieces);
           moves.add(new Move(initRow, initCol, row+2, col+2, temp));
		    moves.addAll(jumpMove(row+2, col+2, initRow, initCol, jumpedPieces, moves));
          jumpedPieces.remove(tempCor);
        }

        if(canMove(initRow, initCol, row + 2, col -2) && (board[row+1][col-1] == BLACK ||board[row+1][col-1] == BLACK_KING) && !jumpedPieces.contains(tempCor = new Coordinates(row+1,col-1)))
		  {
        	  jumpedPieces.add(tempCor);
			  temp = new ArrayList<Coordinates>();
           temp.addAll(jumpedPieces);
           moves.add(new Move(initRow, initCol, row+2, col-2, temp));
		   moves.addAll(jumpMove(row+2, col-2, initRow, initCol, jumpedPieces, moves));
         jumpedPieces.remove(tempCor);
         }
     }
     else if(player == BLACK_KING)
     {
          if(canMove(initRow, initCol, row - 2, col - 2) && (board[row-1][col-1] == RED ||board[row-1][col-1] == RED_KING) && !jumpedPieces.contains(tempCor = new Coordinates(row-1,col-1)))
          {
        	  jumpedPieces.add(tempCor);
           temp = new ArrayList<Coordinates>();
           temp.addAll(jumpedPieces);
           moves.add(new Move(initRow, initCol, row-2, col-2, temp));
          moves.addAll(jumpMove(row-2, col-2, initRow, initCol, jumpedPieces, moves));
          jumpedPieces.remove(tempCor);
          }
          if(canMove(initRow, initCol, row - 2, col + 2) && (board[row-1][col+1] == RED ||board[row-1][col+1] == RED_KING) && !jumpedPieces.contains(tempCor = new Coordinates(row-1,col+1)))
          {
        	  jumpedPieces.add(tempCor);
			  temp = new ArrayList<Coordinates>();
           temp.addAll(jumpedPieces);
           moves.add(new Move(initRow, initCol, row-2, col+2, temp));
          moves.addAll(jumpMove(row-2, col+2, initRow, initCol, jumpedPieces, moves));
          jumpedPieces.remove(tempCor);
          }
          if(canMove(initRow, initCol, row + 2, col +2) && (board[row+1][col+1] == RED ||board[row+1][col+1] == RED_KING) && !jumpedPieces.contains(tempCor = new Coordinates(row+1,col+1)))
		    {
        	  jumpedPieces.add(tempCor);
			  temp = new ArrayList<Coordinates>();
           temp.addAll(jumpedPieces);
           moves.add(new Move(initRow, initCol, row+2, col+2, temp));
		     moves.addAll(jumpMove(row+2, col+2, initRow, initCol, jumpedPieces, moves));
           jumpedPieces.remove(tempCor);
        }

        if(canMove(initRow, initCol, row + 2, col -2) && (board[row+1][col-1] == RED ||board[row+1][col-1] == RED_KING) && jumpedPieces.contains(tempCor = new Coordinates(row+1,col-1)))
		  {
        	  jumpedPieces.add(tempCor);
			  temp = new ArrayList<Coordinates>();
           temp.addAll(jumpedPieces);
           moves.add(new Move(initRow, initCol, row+2, col-2, temp));
           moves.addAll(jumpMove(row+2, col-2, initRow, initCol, jumpedPieces, moves));
           jumpedPieces.remove(tempCor);
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
