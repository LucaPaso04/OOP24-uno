package uno.view.impl;

import uno.model.cards.attributes.CardColor;
import uno.model.cards.attributes.CardValue;
import uno.model.cards.types.api.Card;
import uno.view.api.CardViewData;

import java.util.Optional;

/**
 * DTO representing a Card for the View.
 * Contains resolved color and value (no dependency on Game state for
 * retrieval).
 */
public class CardViewDataImpl implements CardViewData {
    private final CardColor color;
    private final CardValue value;
    private final String imageKey;
    private final Optional<Card> modelCard;

    /**
     * Constructor for CardViewDataImpl.
     * 
     * @param color the color of the card.
     * @param value the value of the card.
     * @param imageKey the image key for this card, used to retrieve the correct image from the
     *                 View's image repository.
     * @param modelCard an optional reference to the underlying model Card, if available. This can be
     *                  used as an opaque token for actions like 'play' without exposing the full model
     *                  to the View.
     */
    public CardViewDataImpl(final CardColor color, final CardValue value, 
                            final String imageKey, final Optional<Card> modelCard) {
        this.color = color;
        this.value = value;
        this.imageKey = imageKey;
        this.modelCard = modelCard;
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
    public String getImageKey() {
        return imageKey;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Card> getModelCard() {
        return modelCard;
    }
}
