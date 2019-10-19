package com.example.s243476.threegames;

import android.util.Log;

public class MS {
    private int[][] grid;
    public int[][] playerView;
    boolean death = false;
    int cnt = 0;
    int numBombs = 0;

    public MS(int l, int w) {
        grid = new int[l][w];
        playerView = new int[l][w];
        death = false;
    }

    public void init(int r, int c) {
        putBombs(r, c);
        fillNums();
    }

    public void putBombs(int r, int c) {
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++) {
                if(Math.random() < .15)
                    if(!((i == r || i == r - 1 || i == r + 1) && (j == c || j == c - 1 || j == c+1))) {
                        grid[i][j] = -1;
                        numBombs++;
                    }
            }
        }
    }

    public void fillNums() {
        for(int i = 0; i< grid.length; i++) {
            for(int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] != -1) {
                    int cnt = 0;

                    //Check straight
                    if(i > 0 && grid[i-1][j] == -1)
                        cnt++;
                    if(i < grid.length - 1 && grid[i+1][j] == -1)
                        cnt++;
                    if(j > 0 && grid[i][j-1] == -1)
                        cnt++;
                    if(j < grid[i].length - 1 && grid[i][j+1] == -1)
                        cnt++;

                    //Check diagonals
                    if(i > 0 && j > 0 && grid[i-1][j-1] == -1)
                        cnt++;
                    if(i < grid.length - 1 && j < grid[i].length - 1 && grid[i+1][j+1] == -1)
                        cnt++;
                    if(i < grid.length - 1 && j > 0 && grid[i+1][j-1] == -1)
                        cnt++;
                    if(i > 0 && j < grid[i].length - 1 && grid[i-1][j+1] == -1)
                        cnt++;

                    grid[i][j] = cnt;
                }
            }
        }
    }

    public void printWholeGrid() {
        for(int[] row : grid) {
            for(float c : row)
                System.out.printf("%5.0f", c);
            System.out.println("\n");
        }
    }

    public void printGrid() {
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[i].length; j++)
                if(playerView[i][j] == 1)
                    System.out.printf("%5.0f", (float)grid[i][j]);
                else if(playerView[i][j] == 2)
                    System.out.print("    x");
                else
                    System.out.print("    -");
            System.out.println("\n");
        }
    }

    public void flag(int r, int c) {
        playerView[r][c] = Math.abs(2 - playerView[r][c]);
    }

    public void open(int r, int c) {
        //Initialize if not yet initialized
        if(cnt == 0)
            init(r, c);
        cnt++;

        if(!death) {
            if (grid[r][c] == -1) {
                Log.d("stuff", "death");
                die();
            } else
                checkAround(r, c);
        }
    }

    public void checkAround(int r, int c) {
        if(playerView[r][c] != 1) {
            playerView[r][c] = 1;
            if(grid[r][c] == 0) {
                if(r > 0) {
                    checkAround(r - 1, c);
                    if(c > 0)
                        checkAround(r - 1, c - 1);
                    if(c < grid[r].length - 1)
                        checkAround(r - 1, c + 1);
                }
                if(r < grid.length - 1) {
                    checkAround(r + 1, c);
                    if (c > 0)
                        checkAround(r + 1, c - 1);
                    if (c < grid[r].length - 1)
                        checkAround(r + 1, c + 1);
                }
                if(c > 0)
                    checkAround(r, c - 1);
                if(c < grid[r].length - 1)
                    checkAround(r, c + 1);
            }
        }
    }

    public void die() {
        death = true;
        System.out.println("You died");
        for(int i = 0; i < grid.length; i++)
            for(int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] == -1)
                    playerView[i][j] = 1;
            }

        printGrid();
    }

    public boolean dead() {
        return death;
    }

    public boolean won(){
        int numLeft = 0;
        for(int[] i : playerView)
            for(int j : i)
                if(j == 0 || j == 2)
                    numLeft++;

        return numLeft == numBombs;
    }

    public int[][] retGrid(){
        return grid;
    }

    public int[][] retPlayerView(){
        return playerView;
    }

    public void reset(int l, int w){
        cnt = 0;
        death = false;
        numBombs = 0;
        for(int i = 0; i < l; i++)
            for(int j = 0; j < w; j++){
                grid[i][j] = 0;
                playerView[i][j] = 0;
            }
    }
}