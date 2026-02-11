package uno.model.cards.types.api;

import uno.model.cards.attributes.CardColor;
import uno.model.cards.attributes.CardValue;
import uno.model.game.api.Game;

/**
 * Interface representing a generic playing card in the UNO game.
 * This interface defines the contract for card behavior, including retrieving
 * dynamic attributes (Color/Value which may change based on the Game state,
 * e.g., in Uno Flip) and executing game effects.
 */
public interface Card {

    /**
     * Gets the current color of the card based on the game state.
     * 
     * @param game The current game context.
     * @return The {@link CardColor} of the card (e.g., RED, BLUE, or WILD).
     */
    CardColor getColor(Game game);

    /**
     * Gets the current value/type of the card based on the game state.
     * 
     * @param game The current game context.
     * @return The {@link CardValue} representing the card's type (e.g., NINE, SKIP,
     *         WILD_DRAW_FOUR).
     */
    CardValue getValue(Game game);

    /**
     * Determines if this card can be legally played on top of the discard pile's
     * current card.
     * This method implements the core matching logic (Match by Color, Match by
     * Value, or Wild card rules).
     * 
     * @param topCard The card currently visible on top of the discard pile.
     * @param game    The current game instance (needed to check the active color if
     *                topCard is Wild).
     * @return {@code true} if the move is valid according to the rules,
     *         {@code false} otherwise.
     */
    boolean canBePlayedOn(Card topCard, Game game);

    /**
     * Executes the gameplay effect associated with this card.
     * This includes actions such as:
     * - Simple turn passing (for Number cards).
     * - Modifying game flow (Skip, Reverse).
     * - Forcing opponents to draw cards (Draw Two, Wild Draw Four).
     * - Changing the active color (Wild).
     * 
     * @param game The current game instance where the effect will be applied.
     */
    void performEffect(Game game);

    /**
     * Gets the point value of the card for scoring purposes at the end of a round.
     * 
     * @param game The current game context.
     * @return The point value.
     */
    int getPointValue(Game game);

    /**
     * Checks if this side represents a Wild card based on its color logic.
     * This avoids code duplication in implementing classes.
     * 
     * @param game The current game context.
     * @return true if the card allows changing color, false otherwise.
     */
    default boolean isWild(final Game game) {
        return getColor(game).name().contains("WILD");
    }
}
