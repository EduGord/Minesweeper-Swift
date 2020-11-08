package br.com.flexpag.model;

import java.awt.GridLayout;

import javax.swing.*;

@SuppressWarnings("serial")
public class BoardPanel extends JPanel {
    Board board;
    public BoardPanel(Board board) {
        this.board = board;
        setLayout(new GridLayout(board.getLines(), board.getColumns()));

        for (BoardField boardField : board.getFields()) {
            add(new BoardFieldButton(boardField));
        }
    }
}
