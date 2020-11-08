package br.com.flexpag.model;

@FunctionalInterface
public interface BoardFieldObserver {
    public void eventOccurred(BoardFieldEvent event);
}
