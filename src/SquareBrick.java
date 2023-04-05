//Author: Ngoc Bui
//Purpose: Create Tetris
//Date: 03/16/2023.

public class SquareBrick extends TetrisBrick{
    public SquareBrick(int center_column){
        colorNum = 4;
        initPosition(center_column);
    }

    @Override
    public void initPosition(int center_col) {
        position = new int[][]{{0, center_col},
                               {0, center_col + 1},
                               {1, center_col},
                               {1, center_col + 1}};
    }

    @Override
    public void rotate() {
    }

    @Override
    public void unrotate() {
    }
}
