package uno.model.cards.deck.impl;

import uno.model.cards.deck.api.Deck;
import uno.model.cards.types.api.Card;
import uno.model.utils.api.GameLogger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Abstract implementation of the {@link Deck} interface.
 * This class handles the underlying storage (ArrayList) and standard operations
 * like shuffling and drawing, while delegating the specific deck composition
 * to the concrete subclasses (e.g., UnoDeck).
 * 
 * @param <T> The type of Card this deck contains.
 */
public abstract class AbstractDeckImpl<T extends Card> implements Deck<T> {

    private final GameLogger logger;
    private final List<T> cards;

    /**
     * Default constructor. Initializes an empty deck.
     * Concrete subclasses must call {@link #refill(List)} or {@link #addCard(Card)}
     * to populate it.
     * 
     * @param logger logger
     */
    public AbstractDeckImpl(final GameLogger logger) {
        this.logger = logger;
        this.cards = new ArrayList<>();
    }

    /**
     * Constructor that accepts an initial set of cards.
     * Useful for testing or creating a deck from a known state.
     * 
     * @param initialCards The cards to start with.
     * @param logger logger
     */
    public AbstractDeckImpl(final List<T> initialCards, final GameLogger logger) {
        this.logger = logger;
        this.cards = new ArrayList<>(initialCards);
        shuffle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void shuffle() {
        if (!cards.isEmpty()) {
            Collections.shuffle(cards);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Optional<T> draw() {
        if (cards.isEmpty()) {
            return Optional.empty();
        }
        // Remove from the end (top) of the list for O(1) performance
        return Optional.of(cards.remove(cards.size() - 1));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Optional<T> peek() {
        if (cards.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(cards.get(cards.size() - 1));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void addCard(final T card) {
        if (card != null) {
            cards.add(card);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void refill(final List<T> newCards) {
        if (newCards != null && !newCards.isEmpty()) {
            this.cards.addAll(newCards);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isEmpty() {
        return cards.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int size() {
        return cards.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final GameLogger getLogger() {
        return this.logger;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "DeckImpl{size=" + size() + "}";
    }
}
