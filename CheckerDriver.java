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
      
        
        
        
        
      
      
      }
      
      //Find who won
      
      
      
   }
}
