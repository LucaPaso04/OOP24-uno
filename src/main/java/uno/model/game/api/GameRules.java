package uno.model.game.api;

/**
 * Interface representing the customizable rules for a Game session.
 */
public interface GameRules {

    /**
     * Checks if the UNO penalty rule is enabled.
     * 
     * @return true if the UNO penalty rule is active.
     */
    boolean isUnoPenaltyEnabled();

    /**
     * Checks if playing immediately after drawing a card is forbidden.
     * 
     * @return true if playing immediately after drawing is forbidden.
     */
    boolean isSkipAfterDrawEnabled();

    /**
     * Checks if the mandatory pass rule is enabled, which prevents
     * reshuffling the discard pile when the deck is empty.
     * 
     * @return true if the discard pile should NOT be reshuffled when deck is empty.
     */
    boolean isMandatoryPassEnabled();

    /**
     * Checks if the scoring mode is enabled, which determines how the game ends.
     * 
     * @return true if the game ends when a player reaches a score limit (e.g. 500),
     *         false if it ends after a single round.
     */
    boolean isScoringModeEnabled();
}
