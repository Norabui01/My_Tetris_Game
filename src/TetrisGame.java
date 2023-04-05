//Author: Ngoc Bui
//Purpose: Create Tetris
//Date: 03/16/2023.

import java.util.*;

public class TetrisGame {
    private TetrisBrick fallingBrick;
    private int[][] background;
    private int rows;
    private int cols;
    private int numBrickTypes = 7;
    private int score;
    private int state;
    private Random randomGen;
    public TetrisGame(int row, int col){
        rows = row;
        cols = col;

        initBoard();

        randomGen = new Random();
        spawnBrick();
    }

    public void initBoard() {
        background = new int[rows][cols];
        for(int row = 0; row < rows; row++){
            for(int col = 0; col < cols; col++){
                background[row][col] = -1;
            }
        }
    }

    public void newGame(){
        initBoard();

        spawnBrick();
        fallingBrick.moveUp();
    }

    public int fetchBoardPosition(int row, int col){
        return background[row][col];
    }

    private void spawnBrick(){
        int randNum = randomGen.nextInt(numBrickTypes);

        int center_column = cols/2 - 1;

            switch (randNum) {
                case 0:
                    fallingBrick = new JayBrick(center_column);
                    break;
                case 1:
                    fallingBrick = new ElBrick(center_column);
                    break;
                case 2:
                    fallingBrick = new ZeeBrick(center_column);
                    break;
                case 3:
                    fallingBrick = new SquareBrick(center_column);
                    break;
                case 4:
                    fallingBrick = new EssBrick(center_column);
                    break;
                case 5:
                    fallingBrick = new StackBrick(center_column);
                    break;
                case 6:
                    fallingBrick = new LongBrick(center_column);
                    break;
            }

    }
    private boolean emptyTop(int row){
        boolean emptyRow = true;

        for(int col = 0; col < cols; col++){
            for(int num = 0; num < row; num++) {
                if (background[row][col] != -1) {
                    emptyRow = false;
                    break;
                }
            }
        }
        return emptyRow;
    }
    public void makeMove(String move){
        switch (move) {
            case "down":
                fallingBrick.moveDown();
                if (!validateMove()){
                    fallingBrick.moveUp();
                    transferColor();

                    if(emptyTop(1)) {
                        spawnBrick();
                    }
                }
                break;
            case "left":
                fallingBrick.moveLeft();
                if (!validateMove()){
                    fallingBrick.moveRight();
                }
                break;
            case "right":
                fallingBrick.moveRight();
                if (!validateMove()){
                    fallingBrick.moveLeft();
                }
                break;
            case "rotate":
                fallingBrick.rotate();
                if (!validateMove()){
                    fallingBrick.unrotate();
                }
                break;
        }
    }
    private boolean validateMove(){
        boolean valid = true;

        for(int numSeg = 0; numSeg < getNumSegs(); numSeg++){
            if (getSegRow(numSeg) == rows ||
                    getSegRow(numSeg) < 0 ||
                    getSegCol(numSeg) < 0 ||
                    getSegCol(numSeg) == cols ||
                    background[getSegRow(numSeg)][getSegCol(numSeg)] != -1) {

                valid = false;
                break;
            }

        }
        return valid;
    }

    public int getNumSegs(){
        return fallingBrick.numSegments;
    }

    public int getFallingBrickColor(){
        return fallingBrick.getColorNumber();
    }

    public int getSegRow(int numSeg){
        return fallingBrick.position[numSeg][0];
    }

    public int getSegCol(int numSeg){
        return fallingBrick.position[numSeg][1];
    }

    private void transferColor(){
        for(int numSeg = 0; numSeg < getNumSegs(); numSeg++){
            background[getSegRow(numSeg)][getSegCol(numSeg)] = getFallingBrickColor();
        }
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
}
