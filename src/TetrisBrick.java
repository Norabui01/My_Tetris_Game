//Author: Ngoc Bui
//Purpose: Create Tetris
//Date: 03/16/2023.

import java.util.Arrays;

public abstract class TetrisBrick {
     protected int[][] position;
     protected int numSegments = 4;
     protected int colorNum;
     public TetrisBrick(){
     }

    @Override
    public String toString() {
        String positsString = "";
        for (int numSeg = 0; numSeg < numSegments; numSeg++) {
            positsString += position[numSeg][0] + ",";
            positsString += position[numSeg][1] + ",";
        }
        return  positsString +
                "\n" + colorNum;
    }

    public int getColorNumber(){
        return colorNum;
    }

    public int getMaximumRow(){
        int maxRow = position[0][0];
        for(int numSeg = 1; numSeg < numSegments; numSeg++){
            if (maxRow < position[numSeg][0]){
                maxRow = position[numSeg][0];
            }
        }
        return maxRow;
    }
    public int getMinimumRow(){
         int minRow = position[0][0];
         for(int numSeg = 1; numSeg < numSegments; numSeg++){
             if (minRow > position[numSeg][0]){
                 minRow = position[numSeg][0];
             }
         }
         return minRow;
    }

    public void setSegPosition(int numSeg, int num, int val){
        position[numSeg][num] = val;
    }


     public abstract void initPosition(int center_column);

     public abstract void rotate();

     public abstract void unrotate();

     public void moveLeft(){
         for(int numSeg = 0; numSeg < numSegments; numSeg++) {
             position[numSeg][1] = position[numSeg][1] - 1;
         }
     }

     public void moveRight(){
         for(int numSeg = 0; numSeg < numSegments; numSeg++) {
             position[numSeg][1] = position[numSeg][1] + 1;
         }
     }

     public void moveDown(){
         for(int numSeg = 0; numSeg < numSegments; numSeg++) {
             position[numSeg][0] = position[numSeg][0] + 1;
         }
     }

     public void moveUp(){
         for(int numSeg = 0; numSeg < numSegments; numSeg++) {
             position[numSeg][0] = position[numSeg][0] - 1;
         }
     }
}
