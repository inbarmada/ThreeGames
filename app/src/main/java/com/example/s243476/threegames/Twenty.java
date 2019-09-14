package com.example.s243476.threegames;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;
import java.util.ArrayList;
enum Direction {
    LEFT, RIGHT, UP, DOWN
}
class Twenty{
    int[][] nums;
    boolean playing;
    boolean won;
    boolean stopped;
    int score;
    Direction curDir;
    Context context;

    public Twenty(Context cxt){
        nums = new int[4][4];
        playing = true;
        won = false;
        stopped = false;
        score = 0;
        curDir = Direction.LEFT;
        context = cxt;
        for(int[] row : nums)
            Log.d("TWENTY", Arrays.toString(row));

        Log.d("TWENTY", "array printed");
        //FOR TESTING PURPOSES
        int[][] numbers = {{2, 4, 0, 32},
                {64, 256, 128, 1024},
                {8, 0, 2, 4},
                {256, 64, 2048, 8}};

        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 4; j++){
                nums[i][j] = numbers[i][j];
            }
        //OVER
        //Log.d("TWENTY", "array changed");

        for(int[] row : nums)
            System.out.println(Arrays.toString(row));
        //Log.d("TWENTY", "second array printed");

        System.out.println("hola");

    }

    /*Get and set mat*/
    public int[][] getMat(){
        return nums;
    }

    public void restartMat(int[][] mat){
        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 4; j++)
                nums[i][j] = mat[i][j];

        nums = addRand(nums);
        score = 0;
    }
    public void begin(){
        Log.d("TWENTY", "begin began");

        while(playing == true){
            move();
            System.out.println("Score: " + score);
            for(int[] row : nums)
                System.out.println(Arrays.toString(row));
        }
        if(!stopped)
            System.out.println("Game Over!");

        System.out.println("Your score was " + score);
    }

    /*Everything that goes into one move*/
    public void move(){
        Log.d("TWENTY", "In move");

        //Get direction of move
        Direction dir = getDir();

        //Align tiles to the move, and store in a separate matrix
        //If time, check why this is necessary--
        int[][] curRay = getRays(dir, nums);

        //Add numbers on the board in chosen direction
        curRay = addUp(dir, curRay, true);

        //Check if anything changed
        boolean diff = change(curRay, nums);

        //If anything changed, realign board and add a random number
        if(diff){
            //Align
            curRay = getRays(dir, curRay);
            //Add random number
            curRay = addRand(curRay);
        }

        //Put changes back into original matrix
        for(int r = 0; r < nums.length; r++){
            for(int c = 0; c < nums.length; c++)
                nums[r][c] = curRay[r][c];
        }

        //Check if game has already been won (if user is playing to win or playing for fun)
        if(!won)
            //If not, check if user has won and act
            //--Put winning action to diff method if time--
            checkWin(curRay);

        //Check if game over
        if(checkEnd(curRay, emptyLoc(curRay))){
            //If so, finish game
            playing = false;
        }
    }

    public void printMat(int[][] rays){
        for(int[] row : rays)
            System.out.println("...." + Arrays.toString(row));
    }

    /*Getting and setting directions*/
    public Direction getDir(){
        return curDir;
    }

    public void setDir(Direction dir){
        curDir = dir;
    }

    /*Get score*/
    public int getScore(){
        return score;
    }

    /*Aligning board to chosen direction*/
    public int[][] getRays(Direction dir, int[][] curRay){
        //--Change var name from curRay to rays, and other var from rays to something else, to standardize--
        //Set a new matrix
        int[][] rays = new int[4][4];

        //Copy items from curRay to rays in direction chosen
        //Iterate over all items in curRay in the correct direction, and copy to rays without spaces
        for(int row = 0; row < curRay.length; row++){
            int cnt = 0;
            for(int col = 0; col < curRay[row].length; col++){
                //--"Normalize" this to where there is less in each if statement--

                //LEFT
                if(dir.equals(Direction.LEFT)){
                    int num = curRay[row][col];
                    if(num != 0){
                        rays[row][cnt] = num;
                        cnt++;
                    }
                }

                //RIGHT
                else if(dir.equals(Direction.RIGHT)){
                    int num = curRay[3 - row][3 - col];
                    if(num != 0){
                        rays[3 - row][3 - cnt] = num;
                        cnt++;
                    }
                }

                //UP
                else if(dir.equals(Direction.UP)){
                    int num = curRay[col][row];
                    if(num != 0){
                        rays[cnt][row] = num;
                        cnt++;
                    }
                }

                //DOWN
                else if(dir.equals(Direction.DOWN)){
                    int num = curRay[3 - col][3 - row];
                    if(num != 0){
                        rays[3 - cnt][3 - row] = num;
                        cnt++;
                    }
                }
            }
        }

        return rays;
    }

    /*Add numbers together in chosen direction*/
    public int[][] addUp(Direction dir, int[][] rays, boolean real){
        //Iterate over items in rays
        //Maybe copy this to separate array instead
        for(int a = 0; a < rays.length; a++)
            for(int b = 0; b < rays.length - 1; b++){
                int r1, r2, c1, c2;
                //Set numbers to check based on direction
                //--Use same if statement for left + right and for up + down--
                if(dir.equals(Direction.LEFT)){
                    r1 = r2 = a;
                    c1 = b;
                    c2 = b + 1;
                }else if(dir.equals(Direction.RIGHT)){
                    r1 = r2 = a;
                    c1 = 3 - b;
                    c2 = 2 - b;
                }else if(dir.equals(Direction.UP)){
                    r1 = b;
                    r2 = b + 1;
                    c1 = c2 = a;
                }else{
                    r1 = 3 - b;
                    r2 = 2 - b;
                    c1 = c2 = a;
                }

                //If they're equal, add them
                if(rays[r1][c1] == rays[r2][c2]){
                    rays[r1][c1] *= 2;
                    rays[r2][c2] = 0;
                    System.out.println("r1: " + r1 + "r2: " + r2);
                    System.out.println("c1: " + c1 + "c2: " + c2);
                    System.out.println("[r1][c1]" + rays[r1][c1]);
                    System.out.println("[r2][c2]" + rays[r2][c2]);
                    if(real)
                        score += rays[r1][c1];
                }
            }

        return rays;
    }

    /*Check if two matrices are the same or not*/
    public boolean change(int[][] ray1, int[][] ray2){
        //Assume the two matrices are the same
        boolean different = false;

        //Iterate over them
        for(int i = 0; i < 4; i++){
            //If there's a difference, return true
            if(!Arrays.equals(ray1[i], ray2[i]))
                different = true;
        }

        //If no difference found return false
        return different;
    }

    /*Return a list with locations of all empty spots on board*/
    public ArrayList<int[]> emptyLoc(int[][] rays){
        //Initialize list
        ArrayList<int[]> locs = new ArrayList<int[]>();

        //Iterate through matrix to find empty spaces
        for(int row = 0; row < rays.length; row++)
            for(int col = 0; col < rays[row].length; col++){
                //If space is empty, add to list
                if(rays[row][col] == 0){
                    int[] curLoc = {row, col};
                    locs.add(curLoc);
                }
            }

        return locs;
    }

    /*Check if game is over; can any moves change the board?*/
    public boolean checkEnd(int[][] rays, ArrayList<int[]> locs){
        //If there are empty spaces, game is not over
        if(!locs.isEmpty()){
            return false;
        }

        //If your next move is "UP", will anything change?
        else if(!(emptyLoc(addUp(Direction.UP, rays, false)).isEmpty())){
            return false;
        }

        //If your next move is "DOWN", will anything change?
        else if(!(emptyLoc(addUp(Direction.DOWN, rays, false)).isEmpty())){
            return false;
        }

        //If your next move is "LEFT", will anything change?
        else if(!(emptyLoc(addUp(Direction.LEFT, rays, false)).isEmpty())){
            return false;
        }

        //If your next move is "RIGHT", will anything change?
        else if(!(emptyLoc(addUp(Direction.RIGHT, rays, false)).isEmpty())){
            return false;
        }

        //If nothing helped - yes, the game is over

        Toast.makeText(context, "Oh no! You lost!", Toast.LENGTH_LONG).show();
        return true;
    }

    /*Add a number to the board*/
    public int[][] addRand(int[][] rays){
        //Get list of empty spaces
        ArrayList<int[]> locs = emptyLoc(rays);

        //Choose random empty location from list
        int rand = (int)(Math.random() * locs.size());
        int[] chosenLoc = locs.get(rand);

        //Choose whether to add 2 or 4
        int number = (int)(Math.round(Math.random())*2) + 2;

        //Put chosen number into chosen location
        rays[chosenLoc[0]][chosenLoc[1]] = number;

        return rays;
    }

    /*Check if you have reached 2048 (AKA if you have won the game)*/
    public void checkWin(int[][] rays){
        //Check if player won
        for(int[] r : rays)
            for(int n : r)
                if(n == 2048)
                    won = true;

        //If won, initiate won game sequence
        if(won){
            winSequence();
        }
    }

    /*Sequence that happens if you won*/
    public void winSequence(){
        Toast.makeText(context, "Great job! You Won!", Toast.LENGTH_LONG).show();
        System.out.println("Great job! You won!");
        System.out.println("Would you like to continue? (Y/N)");
    }

}
