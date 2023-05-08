//Author: Ngoc Bui
//Purpose: Create Tetris
//Date: 03/16/2023.

import javax.swing.*;
import java.io.*;
import java.util.*;

public class TetrisGame {
    private TetrisBrick fallingBrick;
    private int[][] background;
    private int rows;
    private int minRowDetect;
    private int maxRowDetect;
    private int cols;
    private int numBrickTypes = 7;
    private int score = 0;
    private int state = 0;
    private Random randomGen;
    private int minScore;
    public TetrisGame(int row, int col){
        rows = row;
        cols = col;
        initBoard();

        randomGen = new Random();
        spawnBrick();
    }
    @Override
    public String toString() {
        String backgroundStr = "";
        for(int row = 0; row < rows; row++){
            for(int col = 0; col < cols; col++){
                backgroundStr += background[row][col] + ",";
            }
            backgroundStr += "\n";
        }
        return  fallingBrick.toString() +
                "\n" + rows + "," + cols + "," + score + "," + state +
                "\n" + backgroundStr;
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
        score = 0;
        state = 0;
        minRowDetect = 0;
        maxRowDetect = 0;
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

    public void makeMove(String move){
        switch (move) {
            case "down":
                fallingBrick.moveDown();
                if(!validateMove()){
                    fallingBrick.moveUp();
                    transferColor();
                    minRowDetect = fallingBrick.getMinimumRow();
                    maxRowDetect = fallingBrick.getMaximumRow();

                    if(emptyTop()) {
                        spawnBrick();
                    }else{
                        state = 1;
                    }
                }
                break;
            case "left":
                fallingBrick.moveLeft();
                if(!validateMove()){
                    fallingBrick.moveRight();
                }
                break;
            case "right":
                fallingBrick.moveRight();
                if(!validateMove()){
                    fallingBrick.moveLeft();
                }
                break;
            case "rotate":
                fallingBrick.rotate();
                if(!validateMove()){
                    fallingBrick.unrotate();
                }
                break;
        }
    }

    private boolean emptyTop(){
        boolean empty = true;
        for(int col = 0; col < cols; col++){
            if(background[1][col] != -1){
                empty = false;
                break;
            }
        }
        return empty;
    }

    private boolean validateMove() {
        boolean valid = true;
        for (int numSeg = 0; numSeg < getNumSegs(); numSeg++) {
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
    public void setRows(int numRows){
        rows = numRows;
    }
    public void setCols(int numCols){
        cols = numCols;
    }

    private boolean rowIsFull(int rowNumber){
        boolean full = true;

        for (int col = 0; col < cols; col++){
            if (background[rowNumber][col] == -1){
                full = false;
                break;
            }
        }
        return full;
    }

    private void copyRow(int rowNumber){
        for (int col = 0; col < cols; col++) {
            background[rowNumber + 1][col] = background[rowNumber][col];
        }
    }
    private void copyAllRows(int rowNumber){
        for(int row = rowNumber; row >= 0; row--) {
            copyRow(row);
        }
    }
    public void fullRowDetect() {
        int count = 0;
        for (int currentRow = minRowDetect; currentRow <= maxRowDetect; currentRow++) {
            if (rowIsFull(currentRow)) {
                copyAllRows(currentRow - 1);
                count += 1;
            }
        }
        switch(count){
            case 1:
                score += 100;
                break;
            case 2:
                score += 300;
                break;
            case 3:
                score += 600;
                break;
            case 4:
                score += 1200;
                break;
        }
    }

    public int getScore(){
        return score;
    }
    public int getState(){
        return state;
    }

    private String sortScores(){
        File scoreFile = new File("highScore.csv");
        ArrayList<Integer> scoreList = new ArrayList<>();
        ArrayList<String> leaderNames = new ArrayList<>();

        try{
            Scanner inScan = new Scanner(scoreFile);

            while (inScan.hasNextLine()) {
                String nextLine = inScan.nextLine();
                Scanner lineScan = new Scanner(nextLine).useDelimiter(",");
                while (lineScan.hasNextInt()) {
                    int scored = lineScan.nextInt();
                    String name = lineScan.next();

                    leaderNames.add(name);
                    scoreList.add(scored);
                }
                lineScan.close();
            }
            inScan.close();

        }catch(IOException ioe){
            String errorMessage = "!!!!!!!!!!!!!!!!!!!!!!!!!!\n" +
                    "Error made by you. Please check the files." +
                    "!!!!!!!!!!!!!!!!!!!!!!!!!!";
            JOptionPane.showMessageDialog(null, errorMessage, "      ".repeat(7) +
                    "File Error", 2);
            System.exit(0);
        }
        ArrayList<String> sortedNames = new ArrayList<>();
        ArrayList<Integer> sortedList = new ArrayList<>();
        String leaderBoard = "";

        int size = scoreList.size();
        if(size < 2){
            if(size == 0) {
                leaderBoard += "No leaders yet. Go play and be a leader!";
            }else{
                leaderBoard += "1.     " + leaderNames.get(0) + "    ".repeat(4) +
                        scoreList.get(0) + "\n";
            }
        }else {
            for (int cycle = 1; cycle < size; cycle++) {
                int maxElement = scoreList.get(0);
                int maxIndex = 0;
                int counter = 0;

                while (counter < scoreList.size()) {
                    if (maxElement < scoreList.get(counter)) {
                        maxElement = scoreList.get(counter);
                        maxIndex = counter;
                    }
                    counter++;
                }
                sortedList.add(scoreList.get(maxIndex));
                sortedNames.add(leaderNames.get(maxIndex));
                scoreList.remove(maxIndex);
                leaderNames.remove(maxIndex);
            }
            sortedList.add(scoreList.get(0));
            sortedNames.add(leaderNames.get(0));
            scoreList.remove(0);
            leaderNames.remove(0);

            int topLeaders;
            if (sortedList.size() <= 10) {
                topLeaders = sortedList.size();
                minScore = 0;
            } else {
                topLeaders = 10;
                minScore = sortedList.get(9);
            }
            for (int dex = 0; dex < topLeaders; dex++) {
                leaderBoard += (dex + 1) + ".   " + sortedNames.get(dex) +
                        ":   " + sortedList.get(dex) + " points.\n";
            }
        }
        return leaderBoard;
    }

    public void showLeaderBoard(){
        JOptionPane.showMessageDialog(null, sortScores(),
                "Leaders", 1);
    }

    public void saveHighScore(){
        sortScores();
        File scoreFile = new File("highScore.csv");

        if (score > minScore && state == 1){
            String leaderInfo = JOptionPane.showInputDialog(null,
                    "Congratulations for archive high score! Please enter your name: ");
            if(leaderInfo != null) {
                if (leaderInfo.equals("")) {
                    leaderInfo = "Anonymous";
                }

                try {
                    FileWriter writeToFile = new FileWriter(scoreFile, true);
                    BufferedWriter writing = new BufferedWriter(writeToFile);
                    writing.write(score + "," + leaderInfo + "\n");
                    writing.close();
                } catch (IOException ioe) {
                    String errorMessage = "!!!!!!!!!!!!!!!!!!!!!!!!!!\n" +
                            "Trouble writing file to highScore! Delete things in the csv file and run again" +
                            "!!!!!!!!!!!!!!!!!!!!!!!!!!";
                    JOptionPane.showMessageDialog(null, errorMessage,
                            "You crashed my code:))", 2);
                    System.exit(0);
                }
                showLeaderBoard();
            }
            score = 0;
        }
    }
    public void resetLeaderBoard(){
        File scoreFile = new File("highScore.csv");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(scoreFile));
            writer.write("");
            writer.close();
        }catch(IOException ioe){
            String errorMessage = "!!!!!!!!!!!!!!!!!!!!!!!!!!\n" +
                    "Error made by you. Please just re-run." +
                    "!!!!!!!!!!!!!!!!!!!!!!!!!!";
            JOptionPane.showMessageDialog(null, errorMessage, "      ".repeat(7) +
                    "Crash", 2);
            System.exit(0);
        }
    }

    public void saveToFile(){
        JFileChooser chooser = new JFileChooser();
        int retrieve = chooser.showSaveDialog(null);

        if (retrieve == JFileChooser.APPROVE_OPTION) {
            try {
                if(chooser.getSelectedFile().createNewFile()) {
                    FileWriter writeToFile = new FileWriter(chooser.getSelectedFile());
                    writeToFile.write(toString());
                    writeToFile.close();

                }else{
                    String errorMessage = "Your file name is already existed! Please save it with another name!";
                    JOptionPane.showMessageDialog(null, errorMessage, "      ".repeat(9) +
                            "Repeated Name", 0);
                }

            } catch (IOException ioe) {
                String errorMessage = "!!!!!!!!!!!!!!!!!!!!!!!!!!\n" +
                        "Trouble writing file" +
                        "!!!!!!!!!!!!!!!!!!!!!!!!!!";
                JOptionPane.showMessageDialog(null, errorMessage, "      ".repeat(7) +
                        "File missing", 2);
                System.exit(0);
            }
        }
    }
    public void retrieveFromFile(File myFile){
        try{
            Scanner inScan = new Scanner(myFile);
            String firstLine = inScan.nextLine();
            Scanner lineScan = new Scanner(firstLine).useDelimiter("[\n,]");

            int numSeg = 0;
            while (lineScan.hasNextInt()) {
                fallingBrick.setSegPosition(numSeg, 0, lineScan.nextInt());
                fallingBrick.setSegPosition(numSeg, 1, lineScan.nextInt());
                numSeg += 1;
            }
            lineScan.close();

            String secondLine = inScan.nextLine();
            lineScan = new Scanner(secondLine).useDelimiter("[\n,]");
            fallingBrick.colorNum = lineScan.nextInt();
            lineScan.close();

            String thirdLine = inScan.nextLine();
            lineScan = new Scanner(thirdLine).useDelimiter("[\n,]");
            rows = lineScan.nextInt();
            cols = lineScan.nextInt();
            score = lineScan.nextInt();
            state = lineScan.nextInt();
            lineScan.close();

            int row = 0;
            while (inScan.hasNextLine()) {
                String nextLine = inScan.nextLine();
                lineScan = new Scanner(nextLine).useDelimiter("[\n,]");

                int col = 0;
                while (lineScan.hasNextInt()) {
                    background[row][col] = lineScan.nextInt();
                    col += 1;
                }
                lineScan.close();
                row += 1;
            }
            inScan.close();
        } catch (IOException ioe) {
            String errorMessage = "!!!!!!!!!!!!!!!!!!!!!!!!!!\n" +
                    "Trouble opening file " +
                    "!!!!!!!!!!!!!!!!!!!!!!!!!!";

            JOptionPane.showMessageDialog(null, errorMessage, "      ".repeat(7) +
                    "File missing", 2);
            System.exit(0);
        }
    }
}
