
public class CheckerBoard {
	private int[][] board;
	public static final int 
			EMPTY = 0,
			RED = 1,
			BLACK = 2,
			RED_KING = 3,
			BLACK_KING = 4;
	public CheckerBoard()
	{
		board = new int[8][8];
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
			
	
}
