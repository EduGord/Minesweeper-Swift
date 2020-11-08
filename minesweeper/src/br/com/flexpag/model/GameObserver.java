package br.com.flexpag.model;

@FunctionalInterface
public interface GameObserver {
    public void eventOccurred(GameEvent event);
}
