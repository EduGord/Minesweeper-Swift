package br.com.flexpag.model;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingUtilities;


@SuppressWarnings("serial")
public class BoardFieldButton extends JButton implements BoardFieldObserver, MouseListener {
    private BoardField boardField;

    // Colors
    private final Color BG_DEFAULT = new Color(184, 184, 184);
    private final Color BG_FLAGGED = new Color(8, 179, 247);
    private final Color BG_EXPLODE = new Color(189, 66, 68);
    private final Color TEXT_DEFAULT = new Color(0,0,0);
    private final Color TEXT_FLAGGED = new Color(255,0,0);
    private final Color TEXT_ONE = new Color(0, 0, 255);
    private final Color TEXT_TWO = new Color(0, 123, 0);
    private final Color TEXT_THREE = new Color(255, 0, 0);
    private final Color TEXT_FOUR = new Color(0, 0, 123);
    private final Color TEXT_FIVE = new Color(123, 0, 0);
    private final Color TEXT_SIX = new Color(0, 123, 123);
    private final Color TEXT_SEVEN = new Color(0, 0, 0);
    private final Color TEXT_EIGHT = new Color(123, 123, 123);

    public BoardFieldButton(BoardField boardField) {
        this.boardField = boardField;
        setBackground(BG_DEFAULT);
        setBorder(BorderFactory.createBevelBorder(0));
        addMouseListener(this);
        boardField.registerObserver(this);
    }

    @Override
    public void eventOccurred(BoardFieldEvent event) {
        switch(event) {
            case OPEN:
                applyStyleOpen();
                break;
            case FLAG:
                applyStyleFlagged();
                break;
            case UNFLAG:
                applyStyleClosed();
                break;
            case EXPLODE:
                applyStyleExplode();
                break;
        }
    }

    // Applying Styles
    private void applyStyleOpen() {
        setBackground(BG_DEFAULT);
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        setText(Integer.toString(boardField.countMinedNeighbors()));
        switch(boardField.countMinedNeighbors()){
            case 1:
                setForeground(TEXT_ONE);
                break;
            case 2:
                setForeground(TEXT_TWO);
                break;
            case 3:
                setForeground(TEXT_THREE);
                break;
            case 4:
                setForeground(TEXT_FOUR);
                break;
            case 5:
                setForeground(TEXT_FIVE);
                break;
            case 6:
                setForeground(TEXT_SIX);
                break;
            case 7:
                setForeground(TEXT_SEVEN);
                break;
            case 8:
                setForeground(TEXT_EIGHT);
                break;
            default:
                setText(null);
        }

        SwingUtilities.invokeLater(() -> {
            repaint();
            validate();
        });
    }

    private void applyStyleClosed() {
        setBackground(BG_DEFAULT);
        setBorder(BorderFactory.createBevelBorder(0));
        setForeground(null);
        setText(null);
    }

    private void applyStyleFlagged() {
        setText("\u2691");
        setBackground(BG_FLAGGED);
        setForeground(TEXT_FLAGGED);
    }

    private void applyStyleExplode() {
        setBackground(BG_EXPLODE);
        setForeground(TEXT_DEFAULT);
        setText("\uD83D\uDCA3");
    }
    // Mouse Events interfaces
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == 1)
            boardField.open();
        if (e.getButton() == 3)
            boardField.toggleFlag();
    }
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
