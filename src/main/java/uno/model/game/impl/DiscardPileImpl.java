package uno.model.game.impl;

import uno.model.cards.types.api.Card;
import uno.model.game.api.DiscardPile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Concrete implementation of the Discard Pile using an ArrayList.
 */
public class DiscardPileImpl implements DiscardPile {

    private final List<Card> cards;

    /**
     * Constructor initializing an empty discard pile.
     */
    public DiscardPileImpl() {
        this.cards = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addCard(final Card card) {
        if (Optional.ofNullable(card).isEmpty()) {
            throw new IllegalArgumentException("Cannot add a null card to the discard pile.");
        }
        this.cards.add(card);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Card> getTopCard() {
        if (cards.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(cards.get(cards.size() - 1));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Card> takeAll() {
        final List<Card> allCards = new ArrayList<>(this.cards);
        this.cards.clear();
        return allCards;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Card> takeAllExceptTop() {
        if (cards.size() <= 1) {
            return new ArrayList<>();
        }

        final Card topCard = cards.get(cards.size() - 1);
        final List<Card> cardsToRecycle = new ArrayList<>(cards.subList(0, cards.size() - 1));

        cards.clear();
        cards.add(topCard);

        return cardsToRecycle;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Card> getSnapshot() {
        return new ArrayList<>(this.cards);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return cards.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "DiscardPile{size=" + size() + ", top=" + getTopCard().orElse(null) + "}";
    }
}
