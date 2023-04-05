//Author: Ngoc Bui
//Purpose: Create Tetris
//Date: 03/16/2023.

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class TetrisDisplay extends JPanel {
    private TetrisGame game;
    private int start_x = 60;
    private int start_y = 30;
    private int cell_size = 10;
    private int speed = 300;

    private boolean pause;
    private Timer timer;
    private Color[] colors;
    public TetrisDisplay(TetrisGame g){
        game = g;
        colors = new Color[]{Color.GREEN, Color.ORANGE, Color.YELLOW, Color.CYAN,
                Color.BLUE, Color.MAGENTA, Color.RED};

        this.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent ke) {
                translateKey(ke);
            }
        });
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);

        timer = new Timer(speed, new ActionListener(){
            public void actionPerformed(ActionEvent ae)
            {
                if(!pause) {
                    cycleMove();
                }
            }
        });
        timer.start();
    }

    public void paintComponent(Graphics g){
        drawWell(g);
        drawBrick(g);
        drawBackground(g);
    }

    private void drawWell(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(start_x, start_y, cell_size, cell_size * game.getRows());

        g.fillRect(start_x, start_y + cell_size * game.getRows(),
                cell_size * game.getCols() + cell_size * 2, cell_size);

        g.fillRect(start_x + cell_size * game.getCols() + cell_size, start_y,
                cell_size, cell_size * game.getRows());

        g.setColor(Color.WHITE);
        g.fillRect(start_x + cell_size, start_y, cell_size * game.getCols(),
                cell_size * game.getRows());
    }

    private void drawBackground(Graphics g) {
        for(int row = 0; row < game.getRows(); row++){
            for(int col = 0; col < game.getCols(); col++){
                if(game.fetchBoardPosition(row, col) != -1) {
                    g.setColor(colors[game.fetchBoardPosition(row, col)]);
                    g.fillRect(start_x + cell_size + cell_size * col,
                            start_y + cell_size * row,
                            cell_size, cell_size);

                    g.setColor(Color.BLACK);
                    g.drawRect(start_x + cell_size + cell_size * col,
                            start_y + cell_size * row,
                            cell_size, cell_size);
                }
            }
        }
    }

    private void drawBrick(Graphics g){
        for(int numSeg = 0; numSeg < game.getNumSegs(); numSeg ++) {
            g.setColor(colors[game.getFallingBrickColor()]);
            g.fillRect(start_x + cell_size + cell_size * game.getSegCol(numSeg),
                    start_y + cell_size * game.getSegRow(numSeg),
                    cell_size, cell_size);

            g.setColor(Color.BLACK);
            g.drawRect(start_x + cell_size + cell_size * game.getSegCol(numSeg),
                    start_y + cell_size * game.getSegRow(numSeg),
                    cell_size, cell_size);
        }
    }

    private void translateKey(KeyEvent ke){
        int code = ke.getKeyCode();
        switch(code){
            case KeyEvent.VK_UP:
                game.makeMove("rotate");
                break;
            case KeyEvent.VK_RIGHT:
                game.makeMove("right");
                break;
            case KeyEvent.VK_DOWN:
                game.makeMove("down");
                break;
            case KeyEvent.VK_LEFT:
                game.makeMove("left");
                break;
            case KeyEvent.VK_SPACE:
                if(!pause) {
                    pause = true;
                }else{
                    pause = false;
                }
                break;
            case KeyEvent.VK_N:
                pause = false;
                game.newGame();
                break;
        }
    }

    private void cycleMove(){
        game.makeMove("down");
        repaint();
    }
}
