package uno.controller.api;

import uno.model.game.api.GameRules;

/**
 * Interface defining the operations for controlling the main menu of the Uno! game.
 * It serves as a contract for handling user interactions in the menu, such as
 * starting different game modes, opening the rules, and quitting the application.
 */
public interface MenuObserver {

    /**
     * Called when the user clicks "Start Classic Mode".
     */
    void onStartClassicGame();

    /**
     * Called when the user clicks "Start Flip Mode".
     */
    void onStartFlipGame();

    /**
     * Called when the user clicks "Start All Wild Mode".
     */
    void onStartAllWildGame();

    /**
     * Called when the user clicks "Rules".
     */
    void onOpenRules();

    /**
     * Called when the user saves custom rules.
     * 
     * @param rules The new rules set by the user.
     */
    void onSaveRules(GameRules rules);

    /**
     * Called when the user wants to return to the main menu from the rules screen.
     */
    void onBackToMenu();

    /**
     * Called when the user clicks "Quit".
     */
    void onQuit();
}
