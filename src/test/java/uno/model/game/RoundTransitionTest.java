package uno.model.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uno.model.cards.attributes.CardColor;
import uno.model.cards.attributes.CardValue;
import uno.model.cards.deck.api.Deck;
import uno.model.cards.deck.impl.AbstractDeckImpl;
import uno.model.cards.types.api.Card;
import uno.model.cards.types.impl.DoubleSidedCard;
import uno.model.cards.behaviors.api.CardSideBehavior;
import uno.model.cards.behaviors.impl.NumericBehavior;
import uno.model.game.api.DiscardPile;
import uno.model.game.api.GameRules;
import uno.model.game.api.GameState;
import uno.model.game.api.TurnManager;
import uno.model.game.impl.DiscardPileImpl;
import uno.model.game.impl.GameImpl;
import uno.model.game.impl.GameRulesImpl;
import uno.model.game.impl.TurnManagerImpl;
import uno.model.players.api.AbstractPlayer;
import uno.model.players.impl.HumanPlayer;
import uno.model.utils.api.GameLogger;
import uno.model.utils.impl.TestLogger;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RoundTransitionTest {

    private static final int HAND_SIZE = 7;
    private static final int INITIAL_SCORE = 499;
    private static final int WINNING_SCORE_EXPE = 501;
    private static final int CARDS_NUMB = 20;

    private GameImpl game;
    private AbstractPlayer p1;
    private AbstractPlayer p2;
    private Deck<Card> deck;
    private DiscardPile discardPile;
    private TurnManager turnManager;

    @BeforeEach
    void setUp() {
        p1 = new HumanPlayer("P1");
        p2 = new HumanPlayer("P2");
        final List<AbstractPlayer> players = Arrays.asList(p1, p2);

        deck = new TestDeck();
        // Add some dummy cards to deck
        for (int i = 0; i < CARDS_NUMB; i++) {
            deck.addCard(createSimpleCard(CardColor.RED, CardValue.FIVE));
        }

        discardPile = new DiscardPileImpl();
        discardPile.addCard(createSimpleCard(CardColor.RED, CardValue.FOUR)); // Add a starting card
        final GameRules rules = new GameRulesImpl(false, false, false, true); // Scoring enabled
        turnManager = new TurnManagerImpl(players, rules);
        final GameLogger logger = new TestLogger();

        game = new GameImpl(deck, players, turnManager, discardPile, "Classic", logger, rules);
    }

    @Test
    void testRoundOverTransition() {
        // Setup: P1 has 1 card, P2 has cards. P1 plays last card and wins round.
        // Scores are 0.

        final Card winningCard = createSimpleCard(CardColor.RED, CardValue.FIVE);
        p1.addCardToHand(winningCard);

        // P2 has some cards (points)
        p2.addCardToHand(createSimpleCard(CardColor.BLUE, CardValue.TWO)); // 2 points

        game.setCurrentColorOptional(Optional.of(CardColor.RED));
        // Force p1 turn
        while (!game.getCurrentPlayer().equals(p1)) {
            turnManager.advanceTurn(game);
        }

        // Action: P1 plays card
        game.playCard(Optional.of(winningCard));

        // Assert: P1 should have won the round
        assertTrue(p1.hasWon());
        assertEquals(2, p1.getScore(), "P1 should get 2 points from P2's hand");

        // Assert: State should be ROUND_OVER (because score < 500)
        assertEquals(GameState.ROUND_OVER, game.getGameState(), "State should be ROUND_OVER");
    }

    @Test
    void testMatchOverTransition() {
        // Setup: P1 score is 499. P1 wins meaningful points.
        p1.setScore(INITIAL_SCORE);

        final Card winningCard = createSimpleCard(CardColor.RED, CardValue.FIVE);
        p1.addCardToHand(winningCard);
        p2.addCardToHand(createSimpleCard(CardColor.BLUE, CardValue.TWO)); // 2 points

        game.setCurrentColorOptional(Optional.of(CardColor.RED));
        while (!game.getCurrentPlayer().equals(p1)) {
            turnManager.advanceTurn(game);
        }

        game.playCard(Optional.of(winningCard));

        // Assert: P1 score > 500
        assertEquals(WINNING_SCORE_EXPE, p1.getScore());

        // Assert: State should be GAME_OVER
        assertEquals(GameState.GAME_OVER, game.getGameState(), "State should be GAME_OVER");
    }

    @Test
    void testStartNewRound() {
        // Setup: Game in ROUND_OVER state
        final Card winningCard = createSimpleCard(CardColor.RED, CardValue.FIVE);
        p1.addCardToHand(winningCard);
        p2.addCardToHand(createSimpleCard(CardColor.BLUE, CardValue.TWO));

        game.setCurrentColorOptional(Optional.of(CardColor.RED));
        while (!game.getCurrentPlayer().equals(p1)) {
            turnManager.advanceTurn(game);
        }
        game.playCard(Optional.of(winningCard));

        assertEquals(GameState.ROUND_OVER, game.getGameState());

        // Action: Start new round
        game.startNewRound();

        // Assert:
        // 1. State is RUNNING
        assertEquals(GameState.RUNNING, game.getGameState());

        // 2. Players have 7 cards
        assertEquals(HAND_SIZE, p1.getHandSize());
        assertEquals(HAND_SIZE, p2.getHandSize());

        // 3. Deck is refilled (approx check)
        assertFalse(deck.isEmpty());

        // 4. Discard pile has 1 card (the start card)
        assertFalse(discardPile.isEmpty());

        // 5. Turn manager reset (checking if currentPlayer is valid)
        assertNotNull(game.getCurrentPlayer());
    }

    private Card createSimpleCard(final CardColor color, final CardValue value) {
        final CardSideBehavior behavior = new NumericBehavior(color, value);
        return new DoubleSidedCard(behavior, behavior);
    }

    // Helper class to create a clean deck for testing
    static class TestDeck extends AbstractDeckImpl<Card> {
        TestDeck() {
            super(new TestLogger());
        }
    }
}
