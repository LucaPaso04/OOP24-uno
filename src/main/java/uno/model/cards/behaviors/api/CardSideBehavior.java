package uno.model.cards.behaviors.api;

import uno.model.game.api.Game;
import uno.model.cards.attributes.CardColor;
import uno.model.cards.attributes.CardValue;

/**
 * Interface representing the behavior of a single side of a card in the UNO game.
 * Each card can have one or more sides (e.g., for Flip cards), and this
 * interface defines the contract for how each side behaves in terms of its color,
 * value, and the effect it has when played.
 */
public interface CardSideBehavior {

    /**
     * Retrieves the static color of this specific card side.
     * 
     * @return The {@link CardColor} associated with this side.
     */
    CardColor getColor();

    /**
     * Retrieves the face value or type of this specific card side.
     * 
     * @return The {@link CardValue} (e.g., NUMBER, SKIP, WILD).
     */
    CardValue getValue();

    /**
     * Retrieves the point value of this card side.
     * 
     * @return The point value.
     */
    default int getPointValue() {
        return getValue().getPointValue();
    }

    /**
     * Executes the specific game logic associated with this card side.
     * For number cards, this might simply pass the turn.
     * For action cards, this triggers effects like skipping, reversing, or drawing
     * cards.
     * 
     * @param game The current game instance to apply effects on.
     */
    void executeEffect(Game game);

    /**
     * Returns a string representation of this card side, typically combining color
     * and value.
     * Useful for logging and debugging.
     * 
     * @return A string description (e.g., "RED_FIVE" or "WILD_COLOR").
     */
    @Override
    String toString();

    /**
     * Checks if this side represents a Wild card based on its color logic.
     * This avoids code duplication in implementing classes.
     * 
     * @return true if the card allows changing color, false otherwise.
     */
    default boolean isWild() {
        return getColor().name().contains("WILD");
    }
}
