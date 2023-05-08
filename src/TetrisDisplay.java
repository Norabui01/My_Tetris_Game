//Author: Ngoc Bui
//Purpose: Create Tetris
//Date: 03/16/2023.

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class TetrisDisplay extends JPanel{
    private TetrisGame game;
    private int start_x = 110;
    private int start_y = 90;
    private int cell_size = 15;
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
        super.paintComponent(g);
        drawWell(g);
        drawBrick(g);
        drawBackground(g);
        drawGameOverSignal(g);
        drawScore(g);
        drawTetrisSign(g);
    }

    private void drawScore(Graphics g){
        int font_size = 20;
        Font medFont = new Font("Arial", 3, font_size);
        String score = "Score: " + game.getScore();

        g.setColor(Color.GREEN);
        g.setFont(medFont);
        g.drawString(score, 10, 20);

    }

    private void drawGameOverSignal(Graphics g){
        if(game.getState() == 1) {
            g.setColor(Color.RED);
            g.fillRect(start_x - 22, start_y + 40, cell_size * (game.getCols() + 5), cell_size * 5);

            int big_font_size = 40;
            Font bigFont = new Font("Arial", 1, big_font_size);

            g.setColor(Color.BLUE);
            g.setFont(bigFont);
            g.drawString("Game Over!", 8 * game.getCols() + 3 , start_y + 90);

        }
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

    private void drawTetrisSign(Graphics g){
        int big_font_size = 20;
        Font bigFont = new Font("Arial", 1, big_font_size);

        g.setColor(Color.BLUE);
        g.setFont(bigFont);
        g.drawString("Tetris - Ngoc Bui", 500 , 20);
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
        game.fullRowDetect();
        game.saveHighScore();
        repaint();
    }
}
