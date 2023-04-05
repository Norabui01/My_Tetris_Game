//Author: Ngoc Bui
//Purpose: Create Tetris
//Date: 03/16/2023.

import javax.swing.*;
public class TetrisWindow extends JFrame{
    private TetrisGame game;
    private TetrisDisplay display;
    private int win_wid = 270;
    private int win_height = 300;
    private int game_rows = 20;
    private int game_cols = 12;

    public TetrisWindow(){
        this.setTitle("Tetris_Ngoc");
        this.setSize(win_wid, win_height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        game = new TetrisGame(game_rows, game_cols);
        display = new TetrisDisplay(game);

        this.add(display);
        this.setVisible(true);
    }

    public static void main(String[] args){
        new TetrisWindow();
    }
}
