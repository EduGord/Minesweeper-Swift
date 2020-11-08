package br.com.flexpag.model;

import java.util.*;

public class BoardField {
    // Basic Info
    private int line;
    private int column;
    // Status
    private boolean open = false;
    private boolean closed = true;
    private boolean flagged = false;
    private boolean mined = false;
    // Miscellaneous
    private List<BoardField> neighbors = new ArrayList<>();
    // Observers
    private Set<BoardFieldObserver> observers = new HashSet<>();


    public void registerObserver(BoardFieldObserver boardFieldObserver) {
        observers.add(boardFieldObserver);
    }


    public void notifyObservers(BoardFieldEvent event) {
        observers.stream().forEach(o -> o.eventOccurred(event));
    }

    // Constructors
    public BoardField(){}
    public BoardField(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public void open() {
        if (this.closed) {
            this.setOpen();
            if (!this.mined) {
                for (BoardField boardField : neighbors) {
                    if (!boardField.isMined()){
                        if (boardField.neighbors.stream().allMatch(f -> !f.isMined()))
                            boardField.open();
                        else
                            boardField.setOpen();
                    }
                }
            }
            else
                notifyObservers(BoardFieldEvent.EXPLODE);
        }
    }

    public int countMinedNeighbors(){
        return (int) this.neighbors.stream().filter(aField -> aField.isMined()).count();
    }

    public boolean isNeighbor(BoardField boardField){
        if (Math.abs(this.line - boardField.line) <= 1 &&
           (Math.abs(this.column - boardField.column) <= 1) &&
           (!boardField.equals(this))){
               return true;
           } else {
               return false;
           }
    }

    // Hashcode and Equals
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof BoardField)) {
            return false;
        }
        BoardField boardField = (BoardField) o;
        return line == boardField.line && column == boardField.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(line, column);
    }

    // Getters & Setters
    public int getLine() {
        return this.line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return this.column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public boolean isOpen() {
        return this.open;
    }

    public void setOpen() {
        if (this.closed) {
            this.closed = false;
            this.open = true;
            notifyObservers(BoardFieldEvent.OPEN);
        }
    }

    public boolean isClosed() {
        return this.closed;
    }

    public boolean isFlagged() {
        return this.flagged;
    }

    public void toggleFlag() {
        if (this.closed) {
            if (!this.flagged)
                notifyObservers(BoardFieldEvent.FLAG);
            else
                notifyObservers(BoardFieldEvent.UNFLAG);
            this.flagged = !this.flagged;
        }
    }

    public boolean isMined() {
        return this.mined;
    }

    public boolean setMined() {
        if (!this.mined) {
            this.mined = true;
            return true;
        }
        else
            return false;
    }

    public List<BoardField> getNeighbors() {
        return this.neighbors;
    }

    public void setNeighbors(List<BoardField> neighbors) {
        this.neighbors = neighbors;
    }


}
