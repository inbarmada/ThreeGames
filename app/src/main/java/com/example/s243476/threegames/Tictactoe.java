package com.example.s243476.threegames;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Scanner;

class Tictactoe{
    char[][] board;
    boolean playing;
    char curPlayer;
    Context context;

    public Tictactoe(Context cxt){
        context = cxt;
        Log.d("in tictactoe", "so far so good");
        board = new char[3][3];
        playing = true;
        curPlayer = 'X';
        for(int r = 0; r < board.length; r++)
            for(int c = 0; c < board[r].length; c++)
                board[r][c] = ' ';

        print();
    }

    public char[][] getBoard(){
        return board;
    }

    public char getTurn(){
        return curPlayer;
    }
    public void print(){
        for(char[] row : board)
            System.out.println(Arrays.toString(row));
    }

    public void makeMove(int index){
        if(!playing)
            return;
        Log.d("makeMove", "" + index);

        int i = index/3;
        int j = index%3;
        Log.d("makeMove", "i: " + i + "   j: " + j);

        System.out.println(index);
        if(board[i][j] == ' '){
            board[i][j] = curPlayer;
            switchPlayer();
            checkEnd(board);
        }
    }

    public void switchPlayer(){
        if(curPlayer == 'X')
            curPlayer = 'O';
        else
            curPlayer = 'X';
    }

//    public void move(char[][] b){
//        place('X', b);
//        for(char[] row : board)
//            System.out.println(Arrays.toString(row));
//
//        if(checkEnd(b) != ' '){
//            if(checkEnd(b) == 'X')
//                System.out.println("Game over! PLayer X won!");
//            else
//                System.out.println("Game over! Tie!");
//            playing = false;
//            return;
//        }
//        place('O', b);
//        if(checkEnd(b) != ' '){
//            System.out.println("Game over! PLayer Y won!");
//            playing = false;
//        }
//    }

//    public char[][] place(char turn, char[][] b){
//        System.out.println("Turn: " + turn);
//        boolean goodMove = false;
//
//        while(!goodMove){
//            System.out.println("Choose row (0,1,2): ");
//            int r = read.nextInt();
//            System.out.println("Choose column (0,1,2): ");
//            int c = read.nextInt();
//
//            if(b[r][c] == ' '){
//                goodMove = true;
//                b[r][c] = turn;
//            }else{
//                System.out.println("Invalid. Try again: ");
//            }
//        }
//
//        return b;
//    }

    public char checkEnd(char[][] b){
        for(int r = 0; r < b.length; r++){
            //Check row wins
            if(b[r][0] != ' ' && b[r][0] == b[r][1] && b[r][1] == b[r][2]){
                playing = false;
                Toast.makeText(context, "Game over! Player " + b[r][0] + " won!", Toast.LENGTH_LONG).show();

                return b[r][0];
            }
            //Check column wins
            else if(b[0][r] != ' ' && b[0][r] == b[1][r] && b[1][r] == b[2][r]){
                playing = false;
                Toast.makeText(context, "Game over! Player " + b[0][r] + " won!", Toast.LENGTH_LONG).show();
                return b[0][r];
            }
        }

        //Check diagonal wins
        if(b[1][1] != ' ' && ((b[0][0] == b[1][1] && b[1][1] == b[2][2]) || (b[0][2] == b[1][1] && b[1][1] == b[2][0]))){
            playing = false;
            Toast.makeText(context, "Game over! Player " + b[1][1] + " won!", Toast.LENGTH_LONG).show();
            return b[1][1];
        }

        //Check if board is filled
        for(char[] r : b)
            for(char c : r)
                if(c == ' ')
                    return ' ';

        Toast.makeText(context, "Tie!", Toast.LENGTH_LONG).show();
        return '=';
    }

    public void restart(){
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                board[i][j] = ' ';

        playing = true;

    }
}
