package uno.model.api;

/**
 * Interface for observers of the Game Model. Observers will be notified of changes in the game state
 * and should update their views accordingly.
 */
@FunctionalInterface
public interface GameModelObserver {

    /**
     * Called by the Game Model to notify the observer of a change in the game state.
     */
    void onGameUpdate();
}
