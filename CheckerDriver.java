public class CheckerDriver {

   boolean blackIsPlayer;
   boolean redIsPlayer;
   boolean isRedsMove;
   CheckerBoard board;
   
   
   public static void main(String args[]){
      Scanner console = new Scanner (System.in);
      isRedsMove = true;
      System.out.println("Is black a real player? type y/n");//Determine who is real and who will be computer controlled
      if(console.next() == "y"){
         blackIsPlayer = true;
      }
      else{
         blackIsPlayer = false;
      }
      System.out.println("Is red a real player? type y/n");
      if(console.next() == "y"){
         redIsPlayer = true;
      }
      else{
         redIsPlayer = false;
      }
     
      board = new CheckerBoard();//Create board
     
     
      while(board.checkWin() == false){//Play Game
         int tempX;
         int tempY;
         if(isRedMove == true){
            System.out.println("Red Player, type x coordinate of the piece you want to move");
            tempX = console.nextInt();
            System.out.println("Red Player, type y coordinate of the piece you want to move");
            tempY = console.nextInt();
            System.out.println("Moves available to you are as follows");
            System.out.println();////////////////////////////////////////////////////////////////Insert array of moves here
            System.out.println("Red Player, type number of move you want to make");
            board.move();
         }
         else{
            System.out.println("Black Player, type x coordinate of the piece you want to move");
            tempX = console.nextInt();
            System.out.println("Black Player, type y coordinate of the piece you want to move");
            tempY = console.nextInt();
            System.out.println("Moves available to you are as follows");
            System.out.println();////////////////////////////////////////////////////////////////Insert array of moves here
            System.out.println("Black Player, type number of the move you want to make");
            board.move();
         }
         System.out.println(board);
      }
      
      //Find who won
      
      
      
   }
}
