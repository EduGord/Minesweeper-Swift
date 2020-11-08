package br.com.flexpag.model;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements  GameObserver, BoardFieldObserver {
    Game game;
    BoardPanel boardPanel;

    public GamePanel(Game game) {
        this.game = game;
        this.boardPanel = new BoardPanel(game.gameBoard);
        init();

    }
    public void init() {
        setLayout(new GridLayout());
        add(boardPanel);
        game.registerObserver(this);
        game.gameBoard.getFields().forEach(f -> f.registerObserver(this));
    }
    public void reboot() {
        game.gameBoard.reboot();
        boardPanel = new BoardPanel(game.gameBoard);
        remove(0);
        init();
        SwingUtilities.invokeLater(() -> {
            repaint();
            validate();
        });
    }

    @Override
    public void eventOccurred(GameEvent event) {
        switch(event){
            case WIN:
                JOptionPane.showMessageDialog(this, "You win", "Congratuliations", 1);
                game.notifyObservers(GameEvent.REBOOT);
                break;
            case LOSE:
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this, "You lose", "Boom!", 2);
                    game.notifyObservers(GameEvent.REBOOT);
                });
                break;
            case REBOOT:
                reboot();
                break;
        }
    }

    @Override
    public void eventOccurred(BoardFieldEvent event) {
        switch(event){
            case EXPLODE:
                game.notifyObservers(GameEvent.LOSE);
                break;
            default:
                SwingUtilities.invokeLater((game::hasWon));
                break;
        }
    }

}
