package br.com.flexpag.model;

import java.util.*;
import java.util.stream.Collectors;

public class Board {
    private final int lines;
    private final int columns;
    private List<BoardField> fields = new ArrayList<>();
    private final int NMINES;
    // Constructor
    public Board(){
        this.lines = 4;
        this.columns = 4;
        this.NMINES = 4;
        this.init();
    }
    public Board(int lines, int columns) {
        this.lines = lines;
        this.columns = columns;
        this.NMINES = (int) (lines + columns)/2 ;
        this.init();
    }

    public Board(int lines, int columns, int NMINES) {
        this.lines = lines;
        this.columns = columns;
        this.NMINES = NMINES;
        this.init();
    }

    // Methods
    public void reboot() {
        fields.clear();
        init();
    }

    public BoardField getFieldByCoordinates(int x, int y) {
        if (x < lines && y < columns)
            return fields.stream().filter(f -> f.getLine() == x).filter(f -> f.getColumn() == y).findFirst().get();
        else
            return null;
    }

    private void generateFields(){
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                fields.add(new BoardField(i, j));
            }
        }
    }

    private void generateMines(){
        Random randomGenerator = new Random();
        for (int i = 0; i < NMINES; i++) {
            while (!fields.get(randomGenerator.nextInt(fields.size())).setMined());
        }
    }

    private void populateNeighbors() {
        for (BoardField boardField : fields) {
            boardField.setNeighbors(fields.stream().filter(f -> Math.abs(f.getLine() - boardField.getLine()) <= 1)
                                                        .filter(f -> Math.abs(f.getColumn() - boardField.getColumn()) <= 1)
                                                        .filter(f -> !f.equals(boardField)).collect(Collectors.toList()));
        }
    }

    public void init() {
        generateFields();
        generateMines();
        populateNeighbors();
    }

    // Getters
    public List<BoardField> getMinedFields() {
        return fields.stream().filter(f -> f.isMined()).collect(Collectors.toList());
    }

    public List<BoardField> getNonMinedFields() {
        return fields.stream().filter(f -> !f.isMined()).collect(Collectors.toList());
    }

    public int getLines() {
        return lines;
    }

    public int getColumns() {
        return columns;
    }

    public List<BoardField> getFields() {
        return fields;
    }

    public int getNMines() {
        return NMINES;
    }


}
