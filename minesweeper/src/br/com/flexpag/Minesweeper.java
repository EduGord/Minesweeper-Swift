package br.com.flexpag;

import javax.swing.*;

import br.com.flexpag.model.Board;
import br.com.flexpag.model.Game;
import br.com.flexpag.model.GamePanel;

import java.awt.*;

@SuppressWarnings("serial")
public class Minesweeper extends JFrame {
    public Minesweeper() {
        setTitle("Sweepminer");

        setLayout(new GridLayout());
        setSize(400,400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        GamePanel gamePanel = new GamePanel(new Game(new Board(16,16,32)));
        this.add(gamePanel);
        setVisible(true);
    }
    public static void main(String[] args) {
        new Minesweeper();
    }
}
