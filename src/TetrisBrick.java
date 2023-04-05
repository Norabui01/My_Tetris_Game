//Author: Ngoc Bui
//Purpose: Create Tetris
//Date: 03/16/2023.

public abstract class TetrisBrick {
     protected int[][] position;
     protected int numSegments = 4;
     protected int colorNum;
     public TetrisBrick(){
     }

     public int getColorNumber(){
        return colorNum;
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
