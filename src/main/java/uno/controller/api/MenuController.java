package uno.controller.api;

/**
 * Interface for the Main Menu Controller.
 * It handles the user interactions within the main menu, such as selecting game modes,
 * opening rules, or quitting the application.
 */
public interface MenuController extends MenuObserver {

    /**
     * {@inheritDoc}
     */
    @Override
    void onStartClassicGame();

    /**
     * {@inheritDoc}
     */
    @Override
    void onStartFlipGame();

    /**
     * {@inheritDoc}
     */
    @Override
    void onStartAllWildGame();

    /**
     * {@inheritDoc}
     */
    @Override
    void onOpenRules();

    /**
     * {@inheritDoc}
     */
    @Override
    void onQuit();
}
