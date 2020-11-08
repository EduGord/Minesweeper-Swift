package br.com.flexpag.model;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


public class Game {
    Board gameBoard;
    // Constructors
    public Game(){
        this.gameBoard = new Board(8,8);
    }

    public Game (Board gameBoard){
        this.gameBoard = gameBoard;
    }

    // Observers
    Set<GameObserver> observers = new HashSet<>();

    public void registerObserver(GameObserver observer){
        observers.add(observer);
    }

    public void notifyObservers(GameEvent event) {
        observers.forEach(o -> o.eventOccurred(event));
    }

    public boolean hasWon() {
        List<BoardField> minedFields = this.gameBoard.getFields().stream().filter(f -> f.isMined()).collect(Collectors.toList());
        List<BoardField> nonMinedFields = this.gameBoard.getFields().stream().filter(f -> !f.isMined()).collect(Collectors.toList());

        if (minedFields.stream().allMatch(f-> f.isFlagged()) &&
        nonMinedFields.stream().allMatch(f -> f.isOpen())) {
            notifyObservers(GameEvent.WIN);
            return true;
        }
        else {
            return false;
        }
    }
}



