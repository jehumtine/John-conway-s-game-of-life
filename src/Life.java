import java.io.IOException;

/**
 * Write a description of class Life here.
 * 
 * @author (Jehu Mtine) 
 * @version (1.0)
 */

public class Life
{
    public static final int ROWS = 20;
    public static final int COLS = 60;
    public static final int TIME_DELAY=500;

    /**
     * The intializeBoard static method sets up the initial board with a 
     * random set of cells.
     * @param board a Board, typically empty
     */
    public static void initilizeBoard(Board board){
      for(int r=0; r<ROWS;r++){
        for(int c=0; c<COLS;c++){
            int randVal = (int) (Math.random()*8); // random number 0,1 or 2
            if(randVal ==0){ // 1/3 chance of having a live cell
                board.set(r, c, 1);
            }else{}
        }

      }

    }
    
    

    /**
     * The static displayBoard method displays the board on screen. A Board
     * is a 2-dimensional int[][] array, so for the display to include other
     * characters--"." and "0", for example--characters will need to be printed
     * on the screen after checking the int value of that location.
     * @param board the board to be displayed
     */
    public static void displayBoard(Board board){
        for(int r=0; r<ROWS; r++){
            for(int c=0; c<COLS;c++){
                if(board.get(r, c) ==0){
                    System.out.print(".");
                }
                else if(board.get(r,c)==1){
                    System.out.print("0");
                }
            }
            System.out.println();
        }
    }
    

    /**
     * The static calculateNextGeneration method takes the current board and 
     * a new (empty) board and calculates the next generation for that second
     * board based on the standard rules of Conway's Life:
     * 1. existing cell dies if fewer than 2 neighbors (underpopulation)
     * 2. existing cell lives if 2-3 neighbors ("these neighbors are JUST RIGHT!")
     * 3. existing cell dies if greater than 3 neighbors (overpopulation)
     * 4. empty cell becomes alive if exactly 3 neighbors (because...?)
     * 
     * @param b the current board
     * @param nextB a board with the new generation on it
     */
    public static void calculateNextGeneration(Board board,Board nextBoard){
    
        for(int r =0; r<ROWS; r++){
            for (int c =0;c<COLS; c++){
                int neighborCount = countNeighbors(r,c,board);
                if(board.get(r,c)==1 && neighborCount<2){
                    board.set(r, c, 0);
                }else if(board.get(r, c)==1 && neighborCount<4){
                    nextBoard.set(r, c, 1);
                }else if(board.get(r, c)==1 && neighborCount>3){
                    nextBoard.set(r, c, 0);
                }else if(board.get(r, c)==0 && neighborCount==3){
                    nextBoard.set(r, c, 1);
                }
                else{
                    nextBoard.set(r, c, 0);
                }
            }
        }
    }
    
    

    /**
     * The static method countNeighbors counts the eight cells around a given 
     * cell, making sure not to count outside of the bounds of the array and 
     * not to count the current cell itself!
     * @param row the row of the current cell
     * @param col the col of the current cell
     * @param b the board we're investigating
     * @return the number of non-zero neighbors (minimum 0, maximum 8)
     */
    public static int countNeighbors(int row, int col, Board board){
        int count =0;
        for(int r= row-1; r<= row+1;r++){
            for(int c = col -1; c<= col+1;c++){
                if (r>=0 && r<ROWS && 
                    c>=0 && c<COLS &&
                    !(r==row && c ==col) &&
                    board.get(r, c)==1) {
                        count++;
                }
            }
        }
        return count;
    }
    
    
    /**
     * The static method transferNextToCurrent takes the board with the 
     * next generation and copies it to the board for this generation so 
     * that we can continue displaying and analyzing generations.
     * @param board the current board that we will copy to
     * @param nextBoard the next board containing a calculated new generation
     */
    public static void transferNextToCurrent(Board board, Board nexBoard){
        for(int r =0; r<ROWS; r++){
            for(int c =0; c<COLS; c++){
                board.set(r, c, nexBoard.get(r, c));
            }
        }
    }
    
    

    /**
     * The clearConsole method attempts to clear the Terminal so that
     * successive generations of the board can be displayed
     */
    private static void clearConsole()
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void slow(int TIME_DELAY)
    {
        // Sleep for some amount of time to slow display down
        try
        {
            Thread.sleep(TIME_DELAY);
            // TIME_DELAY is an integer in milliseconds
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws IOException
    {
        Board board = new Board(ROWS, COLS);
        Board nextBoard = new Board(ROWS,COLS);
        initilizeBoard(board);
        displayBoard(board);
        for(int i=1;i<100;i++){
            board.toFile(i);
           // displayBoard(board);
            slow(TIME_DELAY);
            calculateNextGeneration(board, nextBoard);
            transferNextToCurrent(board, nextBoard);
            clearConsole();
            
       }
       
    }
}
