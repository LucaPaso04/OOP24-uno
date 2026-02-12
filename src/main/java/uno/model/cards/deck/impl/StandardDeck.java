package uno.model.cards.deck.impl;

import java.util.Arrays;
import java.util.List;

import uno.model.cards.attributes.CardColor;
import uno.model.cards.attributes.CardValue;
import uno.model.cards.behaviors.api.CardSideBehavior;
import uno.model.cards.behaviors.impl.ActionBehavior;
import uno.model.cards.behaviors.impl.BackSideBehavior;
import uno.model.cards.behaviors.impl.DrawBehavior;
import uno.model.cards.behaviors.impl.NumericBehavior;
import uno.model.cards.behaviors.impl.WildBehavior;
import uno.model.cards.types.api.Card;
import uno.model.cards.types.impl.DoubleSidedCard;
import uno.model.utils.api.GameLogger;
import uno.model.game.api.Game;

/**
 * Represents the deck for the "Uno Standard".
 * It extends {@link AbstractDeckImpl} and initializes the cards in the constructor
 * by creating {@link DoubleSidedCard} instances where the back side is always
 * the standard {@link BackSideBehavior}.
 */
public class StandardDeck extends AbstractDeckImpl<Card> {

    /**
     * Constructs a new StandardDeck by populating it with the standard UNO cards (108 cards).
     * 
     * @param logger logger for logging deck operations.
     */
    public StandardDeck(final GameLogger logger) {
        super(logger);
        initializeDeck();
        shuffle();
    }

    /**
     * Fills the deck according to standard UNO rules.
     */
    private void initializeDeck() {
        final List<CardColor> colors = Arrays.asList(
            CardColor.RED, CardColor.BLUE, CardColor.GREEN, CardColor.YELLOW
        );

        final List<CardValue> numberValues = Arrays.asList(
            CardValue.ONE, CardValue.TWO, CardValue.THREE, CardValue.FOUR,
            CardValue.FIVE, CardValue.SIX, CardValue.SEVEN, CardValue.EIGHT, CardValue.NINE
        );

        for (final CardColor color : colors) {
            // ZERO (Only 1 per color)
            createAndAddCard(new NumericBehavior(color, CardValue.ZERO));

            // NUMBERS 1-9 (2 per color)
            for (final CardValue value : numberValues) {
                createAndAddCard(new NumericBehavior(color, value));
                createAndAddCard(new NumericBehavior(color, value));
            }

            // ACTIONS (2 per color)
            // Skip
            createAndAddCard(new ActionBehavior(color, CardValue.SKIP, g -> g.skipPlayers(1)));
            createAndAddCard(new ActionBehavior(color, CardValue.SKIP, g -> g.skipPlayers(1)));

            // Reverse
            createAndAddCard(new ActionBehavior(color, CardValue.REVERSE, Game::reversePlayOrder));
            createAndAddCard(new ActionBehavior(color, CardValue.REVERSE, Game::reversePlayOrder));

            // Draw Two
            createAndAddCard(new DrawBehavior(color, CardValue.DRAW_TWO, 2));
            createAndAddCard(new DrawBehavior(color, CardValue.DRAW_TWO, 2));
        }

        // WILD CARDS (4 each)
        for (int i = 0; i < 4; i++) {
            createAndAddCard(new WildBehavior(CardValue.WILD, 0));
            createAndAddCard(new WildBehavior(CardValue.WILD_DRAW_FOUR, 4));
        }
    }

    /**
     * Helper method to wrap a behavior into a standard DoubleSidedCard.
     * Uses the parent's addCard() method to insert it into the protected list.
     * 
     * @param frontBehavior The behavior for the front side of the card.
     */
    private void createAndAddCard(final CardSideBehavior frontBehavior) {
        final Card card = new DoubleSidedCard(frontBehavior, BackSideBehavior.getInstance());
        this.addCard(card);
    }
}
