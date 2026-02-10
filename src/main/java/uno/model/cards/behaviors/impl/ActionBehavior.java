package uno.model.cards.behaviors.impl;

import uno.model.game.impl.GameImpl;
import uno.model.game.api.Game;
import uno.model.cards.attributes.CardColor;
import uno.model.cards.attributes.CardValue;
import uno.model.cards.behaviors.api.CardSideBehavior;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Concrete implementation of {@link CardSideBehavior} for action cards (e.g., SKIP, REVERSE).
 * It encapsulates the logic for executing the card's effect on the game state.
 * The behavior is defined by a Consumer<Game> that is executed when the card is played.
 */
public class ActionBehavior implements CardSideBehavior {

    private final CardColor color;
    private final CardValue value;
    private final Optional<Consumer<Game>> action;

    /**
     * Constructs a behavior with a specific color, value, and game action.
     *
     * @param color  The color of this card side.
     * @param value  The value/type (e.g., SKIP, REVERSE).
     * @param action A functional consumer that defines what happens to the {@link GameImpl} when this card is played.
     */
    public ActionBehavior(final CardColor color, final CardValue value, final Consumer<Game> action) {
        this.color = color;
        this.value = value;
        this.action = Optional.ofNullable(action);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void executeEffect(final Game game) {
        action.ifPresent(gameConsumer -> gameConsumer.accept(game));
    }

    /**
     * {@inheritDoc}
     */
    @Override 
    public CardColor getColor() { 
        return color; 
    }

    /**
     * {@inheritDoc}
     */
    @Override 
    public CardValue getValue() { 
        return value; 
    }

    /**
     * {@inheritDoc}
     */
    @Override 
    public String toString() { 
        return String.format("%s %s", color, value); 
    }
}
