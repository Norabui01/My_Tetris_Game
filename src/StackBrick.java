//Author: Ngoc Bui
//Purpose: Create Tetris
//Date: 03/16/2023.

public class StackBrick extends TetrisBrick{
    public StackBrick (int center_column){
        colorNum = 5;
        initPosition(center_column);
    }

    @Override
    public void initPosition(int center_col){
        position = new int[][]{{0, center_col + 1},
                               {1, center_col},
                               {1, center_col + 1},
                               {1, center_col + 2}};
    }

    @Override
    public void rotate() {
        if (position[0][0] < position[2][0]){
            position[0][0] += 1;
            position[0][1] += 1;

            position[1][0] -= 1;
            position[1][1] += 1;

            position[numSegments - 1][0] += 1;
            position[numSegments - 1][1] -= 1;
        } else if (position[0][1] > position[2][1]) {
            position[0][0] += 1;
            position[0][1] -= 1;

            position[1][0] += 1;
            position[1][1] += 1;

            position[numSegments - 1][0] -= 1;
            position[numSegments - 1][1] -= 1;
        } else if (position[0][0] > position[2][0]) {
            position[0][0] -= 1;
            position[0][1] -= 1;

            position[1][0] += 1;
            position[1][1] -= 1;

            position[numSegments - 1][0] -= 1;
            position[numSegments - 1][1] += 1;
        }else{
            position[0][0] -= 1;
            position[0][1] += 1;

            position[1][0] -= 1;
            position[1][1] -= 1;

            position[numSegments - 1][0] += 1;
            position[numSegments - 1][1] += 1;
        }
    }

    @Override
    public void unrotate() {
        rotate();
        rotate();
        rotate();
    }
}
