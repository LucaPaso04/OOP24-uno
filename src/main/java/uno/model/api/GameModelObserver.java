package uno.model.api;

/**
 * Interface for observers of the Game Model.
 * It defines a generic update method that the View will implement to be notified
 * of changes in the Game state. Upon receiving an update, the View should
 * query the Model for the latest data and refresh its display accordingly.
 */
@FunctionalInterface
public interface GameModelObserver {

    /**
     * Called by the Game Model to notify the observer of a change in the game state.
     * The observer should then query the model for the latest state and update the view.
     */
    void onGameUpdate();
}
