//Author: Ngoc Bui
//Purpose: Create Tetris
//Date: 03/16/2023.

public class ZeeBrick extends TetrisBrick{
    public ZeeBrick(int center_column){
        colorNum = 3;
        initPosition(center_column);
    }

    @Override
    public void initPosition(int center_col) {
        position = new int[][]{{0, center_col},
                               {0, center_col + 1},
                               {1, center_col + 1},
                               {1, center_col + 2}};
    }

    @Override
    public void rotate() {
        if (position[0][0] == position[1][0]){
            position[0][0] -= 1;
            position[0][1] += 1;

            position[2][0] -= 1;
            position[2][1] -= 1;

            position[3][1] -= 2;
        }else{
            position[0][0] += 1;
            position[0][1] -= 1;

            position[2][0] += 1;
            position[2][1] += 1;

            position[3][1] += 2;
        }
    }

    @Override
    public void unrotate() {
        rotate();
    }
}
