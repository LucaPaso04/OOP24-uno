package uno.model.cards.deck.impl;

import uno.model.cards.attributes.CardValue;
import uno.model.cards.behaviors.api.CardSideBehavior;
import uno.model.cards.behaviors.impl.BackSideBehavior;
import uno.model.cards.behaviors.impl.WildBehavior;
import uno.model.cards.types.api.Card;
import uno.model.cards.types.impl.DoubleSidedCard;
import uno.model.utils.api.GameLogger;

/**
 * Represents the deck for the "Uno All Wild" variant.
 * In this mode, every single card is a Wild card. Colors do not matter for matching,
 * but specific cards have powerful action effects (Skip Two, Targeted Draw, Swap).
 */
public class AllWildDeck extends AbstractDeckImpl<Card> {

    private static final int CARDS_OF_EACH_TYPE = 14;

    /**
     * Constructs an All Wild Deck with 112 Wild cards.
     * 
     * @param logger logger for logging deck operations.
     */
    public AllWildDeck(final GameLogger logger) {
        super(logger);
        initializeDeck();
        shuffle();
    }

    /**
     * Populates the deck with 112 Wild cards.
     * Note: In "All Wild", cards do NOT require a color choice 
     * because they match everything. We set `requiresColorChoice` to false.
     */
    private void initializeDeck() {
        for (int i = 0; i < CARDS_OF_EACH_TYPE; i++) {

            // Classic Wild
            createAndAddCard(new WildBehavior(
                CardValue.WILD_ALLWILD, 0, false, false, 0, false
            ));

            // Wild Draw 4
            createAndAddCard(new WildBehavior(
                CardValue.WILD_DRAW_FOUR_ALLWILD, 4, false, false, 1, false
            ));

            // Wild Draw 2
            createAndAddCard(new WildBehavior(
                CardValue.WILD_DRAW_TWO_ALLWILD, 2, false, false, 1, false
            ));

            // Wild Reverse
            createAndAddCard(new WildBehavior(
                CardValue.WILD_REVERSE, 0, false, false, 0, true
            ));

            // Wild Skip
            createAndAddCard(new WildBehavior(
                CardValue.WILD_SKIP, 0, false, false, 1, false
            ));

            // Wild Skip Two
            createAndAddCard(new WildBehavior(
                CardValue.WILD_SKIP_TWO, 0, false, false, 2, false
            ));

            // Forced Swap
            createAndAddCard(new WildBehavior(
                CardValue.WILD_FORCED_SWAP, 0, false, true, 0, false
            ));

            // Targeted Draw Two
            createAndAddCard(new WildBehavior(
                CardValue.WILD_TARGETED_DRAW_TWO, 0, false, true, 0, false
            ));
        }
    }

    /**
     * Helper to create the DoubleSidedCard.
     * Front is the Wild Action, Back is Standard.
     * 
     * @param frontBehavior The behavior for the front side of the card.
     */
    private void createAndAddCard(final CardSideBehavior frontBehavior) {
        final Card card = new DoubleSidedCard(frontBehavior, BackSideBehavior.getInstance());
        this.addCard(card);
    }
}
