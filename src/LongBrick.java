//Author: Ngoc Bui
//Purpose: Create Tetris
//Date: 03/16/2023.

public class LongBrick extends TetrisBrick{
    public LongBrick(int center_column){
        colorNum = 6;
        initPosition(center_column);
    }

    @Override
    public void initPosition(int center_col) {
        position = new int[][]{{0, center_col - 1},
                               {0, center_col},
                               {0, center_col + 1},
                               {0, center_col + 2}};
    }

    @Override
    public void rotate() {
        if (position[0][0] == position[1][0]){
            position[0][0] -= 2;
            position[0][1] += 2;

            position[1][0] -= 1;
            position[1][1] += 1;

            position[numSegments - 1][0] += 1;
            position[numSegments - 1][1] -= 1;
        }else{
            position[0][0] += 2;
            position[0][1] -= 2;

            position[1][0] += 1;
            position[1][1] -= 1;

            position[numSegments - 1][0] -= 1;
            position[numSegments - 1][1] += 1;
        }
    }

    @Override
    public void unrotate() {
        rotate();
    }
}
