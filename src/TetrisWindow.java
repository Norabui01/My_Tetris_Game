//Author: Ngoc Bui
//Purpose: Create Tetris
//Date: 03/16/2023.

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class TetrisWindow extends JFrame implements ActionListener{
    private TetrisGame game;
    private TetrisDisplay display;
    private int win_wid = 700;
    private int win_height = 700;
    private int game_rows = 20;
    private int game_cols = 12;


    public TetrisWindow(){
        this.setTitle("Tetris_Ngoc");
        this.setSize(win_wid, win_height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initMenus();
        game = new TetrisGame(game_rows, game_cols);
        display = new TetrisDisplay(game);

        this.add(display);
        this.setVisible(true);
    }

    public void initMenus(){
        JMenuBar myMenuBar = new JMenuBar();
        this.setJMenuBar(myMenuBar);

        JMenu menu0 = new JMenu("Instruction");
        myMenuBar.add(menu0);
        JMenuItem item0 = new JMenuItem("Show Intro");
        item0.addActionListener(this);

        menu0.add(item0);

        JMenu menu1 = new JMenu("Game");
        myMenuBar.add(menu1);
        JMenuItem item1 = new JMenuItem("New game");
        item1.addActionListener(this);
        JMenuItem item2 = new JMenuItem("Save");
        item2.addActionListener(this);
        JMenuItem item3 = new JMenuItem("Open");
        item3.addActionListener(this);

        menu1.add(item1);
        menu1.add(item2);
        menu1.add(item3);

        JMenu menu2 = new JMenu("Leader Board");
        myMenuBar.add(menu2);
        JMenuItem item5 = new JMenuItem("Reset");
        item5.addActionListener(this);
        JMenuItem item4 = new JMenuItem("Show");
        item4.addActionListener(this);

        menu2.add(item4);
        menu2.add(item5);

        JMenu menu3 = new JMenu("Size");
        myMenuBar.add(menu3);
        JMenuItem item6 = new JMenuItem("20 * 12");
        item6.addActionListener(this);
        JMenuItem item7 = new JMenuItem("30 * 30");
        item7.addActionListener(this);

        menu3.add(item6);
        menu3.add(item7);
    }

    public void actionPerformed(ActionEvent ae){
        Object clickedObject = ae.getSource();

        if(clickedObject instanceof JMenuItem) {
            JMenuItem clickItem = (JMenuItem) clickedObject;
            if (clickItem.getText().equals("New game")) {
                game.newGame();
            }
            else if(clickItem.getText().equals("Save")){
                game.saveToFile();
            }else if(clickItem.getText().equals("Open")){
                openFiles();

            }else if(clickItem.getText().equals("Show")){
                game.showLeaderBoard();
            }else if(clickItem.getText().equals("Reset")){
                game.resetLeaderBoard();
                game.showLeaderBoard();

            }else if(clickItem.getText().equals("20 * 12")){
                changeSize("20 * 12");
            }else if(clickItem.getText().equals("30 * 30")) {
                changeSize("30 * 30");

            }else{
                showInstruction();
            }
        }
    }

    private void openFiles(){
        JFileChooser fileChooser = new JFileChooser();
        int fileClicked = fileChooser.showOpenDialog(null);
        if (fileClicked == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            game.retrieveFromFile(selectedFile);
        }
    }

    private void changeSize(String size){
        if (size.equals("20 * 12")){
            String changeSizeMess = "You are setting back to original well with size 20 * 12.\n"+
                    "Click Ok to have new game with this well. Have fun!";
            JOptionPane.showMessageDialog(null, changeSizeMess, "      ".repeat(7) +
                    "Small Well!", 1);
            game.setRows(20);
            game.setCols(12);

        }else {
            String changeSizeMess = "You are setting a big well with size 30 * 30.\n" +
                    "Click Ok to have new game with this well. Have fun!";
            JOptionPane.showMessageDialog(null, changeSizeMess, "      ".repeat(7) +
                    "Big Well!", 1);
            game.setRows(30);
            game.setCols(30);
        }
        game.newGame();
    }

    private void showInstruction(){
        String introMess = "<html><h2>Welcome to my Tetris game!</h2>"
                + "<h3>This is a famous basic version of Tetris so<br>"
                + "I think you already know how to play:))<br>"
                + "However, in case some menus of the game make you confuse a bit,<br>"
                + "I am here to help.<br>"
                + "The Game menu includes New Game that you can play new game whenever you like to.<br>"
                + "It also has Save option that you can save the game you are playing<br>"
                + "and can continue to play it later by choosing your game file from Open option.<br>"
                + "The Leader Board will show the ones who have high scores, also can delete the board:))<br>"
                + "To be in the Leader Board, you have to have higher score than the last leader!<br>"
                + "And you are able to change the size of game for fun by Size menu.<br>"
                + "The space bar can be used for pausing and N-key is a quick way for new game.<br>"
                + "Have fun and be a leader!</h3>";

        JOptionPane.showMessageDialog(null, introMess, "      ".repeat(14) +
                "Hello!", 1);
    }

    public static void main(String[] args){
        new TetrisWindow();
    }
}
